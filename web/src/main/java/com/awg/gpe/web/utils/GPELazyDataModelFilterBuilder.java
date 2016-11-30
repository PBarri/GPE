package com.awg.gpe.web.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * Clase encargada de manejar las búsquedas paginadas que van a ser mostradas en algún listado
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class GPELazyDataModelFilterBuilder {
    
    /**
     * Filtros para la búsqueda
     * @version 1.0
     * @since 1.0
     */
    private final Map<String, Object> filters;
    
    /**
     * Constructor vacío de la clase GPELazyDataModelFilterBuilder.java
     * @since 1.0
     */
    public GPELazyDataModelFilterBuilder() {
        this.filters = new HashMap<>();
    }
    
    public static GPELazyDataModelFilterBuilder newInstance() {
        return new GPELazyDataModelFilterBuilder();
    }
    
    /**
     * Añade un nuevo filtro dependiendo de una condición
     * @param expression condición para que se añada el filtro
     * @param filter nombre del filtro
     * @param value objeto para filtrar
     * @return la instancia de {@link GPELazyDataModelFilterBuilder}
     * @version 1.0
     * @since 1.0
     */
    public GPELazyDataModelFilterBuilder add(Boolean expression, String filter, Object value) {
        if (expression == true) {
            this.filters.put(filter, value);
        }
        return this;
    }
    
    /**
     * Añade un nuevo filtro
     * <p>
     * Este método realiza una comprobación de que el objeto sea distinto de nulo
     * </p>
     * @param filter nombre del filtro
     * @param value objeto para filtrar
     * @return la instancia de {@link GPELazyDataModelFilterBuilder}
     * @version 1.0
     * @since 1.0
     */
    public GPELazyDataModelFilterBuilder add(String filter, Object value) {
        if (value != null) {
            this.filters.put(filter, value);
        }        
        return this;
    }
    
    /**
     * Añade un nuevo filtro
     * <p>
     * Este método realiza una comprobación de que el String sea distinto de nulo y no esté vacío
     * </p>
     * @param filter nombre del filtro
     * @param value objeto para filtrar
     * @return la instancia de {@link GPELazyDataModelFilterBuilder}
     * @version 1.0
     * @since 1.0
     */
    public GPELazyDataModelFilterBuilder add(String filter, String value) {
        if (StringUtils.hasText(value)) {
            this.filters.put(filter, value);
        }        
        return this;
    }
    
    /**
     * Elimina un filtro anteriormente usado
     * @param filter
     * @return
     */
    public GPELazyDataModelFilterBuilder remove(String filter) {
        if (StringUtils.hasText(filter)) {
            this.filters.remove(filter);
        }    	     
        return this;
    }
    
    /**
     * @return el objeto de tipo {@link Map} con los filtros creados
     * @version 1.0
     * @since 1.0
     */
    public Map<String, Object> build() {
        return new HashMap<>(this.filters);
    }

}
