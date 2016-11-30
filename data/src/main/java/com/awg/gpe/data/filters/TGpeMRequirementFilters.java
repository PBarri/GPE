package com.awg.gpe.data.filters;

import java.time.LocalDate;

import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMRequirement;

/**
 * Clase con los filtros disponibles para la paginación de la clase {@link TGpeMRequirement}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface TGpeMRequirementFilters {

    // Filters ------------------------------------------------------------
    String FILTER_NAME = "nameFilter";
    String FILTER_CODE = "codeFilter";

    /**
     * El objeto {@link TGpeMProject} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_PROJECT = "projectFilter";

    
    /**
     * {@link Boolean} indicando si el usuario quiere que se muestren solo proyectos archivados o sin archivar
     * @since 1.0
     */
    String FILTER_ARCHIVED = "archivedFilter";
    /**
     * El objeto {@link LocalDate} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_START_DATE = "startDateFilter";

    /**
     * El objeto {@link LocalDate} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_END_DATE = "endDateFilter";

}
