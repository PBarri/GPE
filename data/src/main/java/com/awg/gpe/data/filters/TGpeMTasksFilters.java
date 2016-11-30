package com.awg.gpe.data.filters;

import java.time.LocalDateTime;

import com.awg.gpe.data.enums.EnumPriority;
import com.awg.gpe.data.enums.EnumTaskStatus;
import com.awg.gpe.data.enums.EnumTaskType;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMRequirement;
import com.awg.gpe.data.model.TGpeMTask;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Clase con los filtros disponibles para la paginación de la clase {@link TGpeMTask}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface TGpeMTasksFilters {

    // Filters ------------------------------------------------------------
    String FILTER_NAME = "nameFilter";
    String FILTER_CODE = "codeFilter";
    
    /**
     * El objeto {@link TGpeMUser} correspondiente al usuario logado en la aplicación.
     * @since 1.0
     */
    String FILTER_USER = "currentUser";
    /**
     * El objeto {@link TGpeMProject} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_PROJECT = "projectFilter";
    /**
     * El objeto {@link TGpeMRequirement} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_REQUIREMENT = "requirementFilter";
    /**
     * El objeto {@link EnumTaskStatus} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_TASK_STATUS = "taskStatusFilter";
    /**
     * El objeto {@link EnumPriority} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_TASK_PRIORITY = "taskPriorityFilter";
    /**
     * El objeto {@link EnumTaskType} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_TASK_TYPE = "taskTypeFilter";
    /**
     * El objeto {@link TGpeMUser} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_DEVELOPER = "developerFilter";
    /**
     * El objeto {@link LocalDateTime} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_START_DATE = "startDateFilter";
    /**
     * El objeto {@link LocalDateTime} con el que se quiere filtrar
     * 
     * @since 1.0
     */
    String FILTER_END_DATE = "endDateFilter";
    
    /**
     * Filtro para obtener las tareas con un determinado padre
     */
    String FILTER_PARENT_TASK = "parentTaskFilter";

}
