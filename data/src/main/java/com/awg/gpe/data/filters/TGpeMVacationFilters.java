package com.awg.gpe.data.filters;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpeMVacation;

/**
 * Clase con los filtros disponibles para la paginación de la clase {@link TGpeMVacation}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface TGpeMVacationFilters {
    
    /**
     * Objeto {@link TGpeMUser}, referente al usuario logado en la aplicación
     * @since 1.0
     */
    String USER_FILTER = "userFilter";
    
    String YEAR_FILTER = "yearFilter";
    
    String HIST_YEAR_FILTER = "histYearFilter";

}
