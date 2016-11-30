package com.awg.gpe.data.filters;

import java.time.LocalDate;

import com.awg.gpe.data.enums.EnumMethodology;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Clase con los filtros disponibles para la paginación de la clase {@link TGpeMProject}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface TGpeMProjectFilter {

    // Filters ------------------------------------------------------------
    String FILTER_CODE = "codeFilter";
    String FILTER_NAME = "nameFilter";
    /**
     * El objeto {@link EnumMethodology} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_METHODOLOGY = "methodologyFilter";
    /**
     * El objeto {@link TGpeMUser} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_PROJECT_LEADER = "projectLeaderFilter";
    /**
     * El objeto {@link TGpeMUser} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_PROJECT_MANAGER = "projectManagerFilter";
    /**
     * El objeto {@link TGpeMUser} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_PRODUCT_OWNER = "productOwnerFilter";
    /**
     * El objeto {@link TGpeMUser} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_SCRUM_MASTER = "scrumMasterFilter";
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
    
    String FILTER_ARCHIVED = "archivedFilter";

}
