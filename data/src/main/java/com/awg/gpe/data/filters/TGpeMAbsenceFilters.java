package com.awg.gpe.data.filters;

import com.awg.gpe.data.model.TGpeMAbsence;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Clase con los filtros disponibles para la paginación de la clase {@link TGpeMAbsence}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface TGpeMAbsenceFilters {
    
    /**
     * Objeto {@link TGpeMUser}, referente al usuario logado en la aplicación
     * @since 1.0
     */
    String USER_FILTER = "userFilter";
    
    /**
     * {@link Integer} que simboliza el año de la ausencia
     */
    String YEAR_FILTER = "yearFilter";
    
    /**
     * {@link Integer} que simboliza el último año de las ausencias históricas
     */
    String HIST_YEAR_FILTER = "histYearFilter";

}
