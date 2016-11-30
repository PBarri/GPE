package com.awg.gpe.data.filters;

import com.awg.gpe.data.model.TGpeMTimesheet;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Clase con los filtros disponibles para la paginación de la clase {@link TGpeMTimesheet}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface TGpeMTimesheetFilters {
    
    /**
     * Objeto {@link TGpeMUser}, referente al usuario logado en la aplicación
     * @since 1.0
     */
    String USER_FILTER = "userFilter";

}
