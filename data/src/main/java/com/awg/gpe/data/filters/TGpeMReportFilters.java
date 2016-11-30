package com.awg.gpe.data.filters;

import java.time.LocalDate;

import com.awg.gpe.data.enums.EnumReportStatus;
import com.awg.gpe.data.enums.EnumReportType;
import com.awg.gpe.data.model.TGpeMReport;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Clase con los filtros disponibles para la paginación de la clase {@link TGpeMReport}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface TGpeMReportFilters {

    /**
     * Objeto {@link TGpeMUser} referente al usuario logado en la aplicación
     * @since 1.0
     */
    String FILTER_USER = "userFilter";
    
    /**
     * Referencia al objeto {@link EnumReportType} del filtrado
     * @since 1.0
     */
    String FILTER_TYPE = "typeFilter";
    
    /**
     * Referencia al objeto {@link EnumReportStatus} del filtrado
     * @since 1.0
     */
    String FILTER_STATUS = "statusFilter";

    /**
     * Referencia al objeto {@link LocalDate} del filtrado
     * @since 1.0
     */
    String FILTER_DATE = "dateFilter";
    
    /**
     * Objeto {@link TGpeMUser} referente al usuario que creó el informe
     * @since 1.0
     */
    String FILTER_CREATED_BY = "createdByFilter";
}
