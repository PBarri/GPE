package com.awg.gpe.web.primefaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.BaseEntity;
import com.awg.gpe.data.services.BaseService;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class GPELazyDataModel<T extends BaseEntity> extends LazyDataModel<T> {

    /**
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    private final BaseService<T, Long> service;
    
    private String sortField;
    
    private SortOrder sortOrder;

    private Map<String, Object> filters;

    public GPELazyDataModel(BaseService<T, Long> service) {
        this.service = service;
    }

    public GPELazyDataModel(BaseService<T, Long> service, Map<String, Object> filters) {
        this.service = service;
        this.filters = filters;
    }
    
    /**
     * Constructor vacío de la clase GPELazyDataModel.java
     * @since 1.0
     */
    public GPELazyDataModel(BaseService<T, Long> service, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.service = service;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
        this.filters = filters;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        // Si hemos inicializado los datos con filtros, sobreescribimos los que le puedan venir por los que han sido inicializados
        if (this.filters != null && !this.filters.isEmpty()) {
            filters = this.filters;
        }
        if (StringUtils.hasText(this.sortField)) {
            sortField = this.sortField;
        }
        if (this.sortOrder != null) {
            sortOrder = this.sortOrder;
        }
        
        List<T> res = null;
        Pageable pageable;
        if (StringUtils.hasText(sortField) && sortOrder != null) {
            Sort.Direction direction = (sortOrder.equals(SortOrder.ASCENDING)) ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = new PageRequest(first / pageSize, pageSize, direction, sortField);
        } else {
            pageable = new PageRequest(first / pageSize, pageSize);
        }
        try {
            setRowCount(this.service.countPaginatedData(filters));
            res = this.service.loadPaginatedData(pageable, filters);
            setWrappedData(res);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Object getRowKey(T object) {
        return object.getId();
    }

    @Override
    public T getRowData(String rowKey) {
        Optional<T> rowData = getDataList().stream().filter(obj -> obj.getId().equals(Long.valueOf(rowKey))).findAny();
        return rowData.orElseGet(() -> {
            try {
                return this.service.findOne(Long.valueOf(rowKey));
            } catch (Exception e) {
                return null;
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    public List<T> getDataList() {
        return (List<T>) getWrappedData();
    }
}