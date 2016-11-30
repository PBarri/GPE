package com.awg.gpe.data.services;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMTimesheetFilters;
import com.awg.gpe.data.model.QTGpeMTimesheet;
import com.awg.gpe.data.model.TGpeMTimesheet;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMTimesheet;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * Servicio de la entidad {@link TGpeMTimesheet}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMTimesheet extends BaseService<TGpeMTimesheet, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMTimesheet repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMTimesheet, Long> getRepository() {
        return this.repository;
    }

    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
        QTGpeMTimesheet timesheet = QTGpeMTimesheet.tGpeMTimesheet;
        BooleanBuilder predicate = new BooleanBuilder();
        
        filters.forEach((key, value) -> {            
            switch (key) {
                case TGpeMTimesheetFilters.USER_FILTER:
                    predicate.and(timesheet.user.eq((TGpeMUser) value));
                    break;
                default:
                    break;
            }
        });
        
        return predicate;
    }

    // Custom methods ------------------------------------------------
    /**
     * Método que busca el horario actualmente activo por el usuario
     * 
     * @param user Usuario del que se quiere conocer el horario
     * @return El horario actual
     * @throws ServiceException En el caso de que ocurra un error
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTimesheet findActual(TGpeMUser user) throws ServiceException {
        return this.repository.findByDate(LocalDate.now(), user);
    }
}
