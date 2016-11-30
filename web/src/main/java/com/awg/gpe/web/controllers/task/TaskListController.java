package com.awg.gpe.web.controllers.task;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.enums.EnumPriority;
import com.awg.gpe.data.enums.EnumTaskStatus;
import com.awg.gpe.data.enums.EnumTaskType;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMTasksFilters;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMRequirement;
import com.awg.gpe.data.model.TGpeMTask;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.services.LoginService;
import com.awg.gpe.data.services.ServiceMProject;
import com.awg.gpe.data.services.ServiceMRequirement;
import com.awg.gpe.data.services.ServiceMTask;
import com.awg.gpe.data.services.ServiceMUser;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.primefaces.GPELazyDataModel;
import com.awg.gpe.web.utils.GPELazyDataModelFilterBuilder;

/**
 * Controlador que maneja el listado de tareas de la aplicación
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "taskListController")
@ViewScoped
public class TaskListController extends BaseController {
    
    private static final Logger log = Logger.getLogger(TaskListController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_TASK_LIST = "/views/tasks/taskList.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMTask taskService;
    @Autowired
    private ServiceMProject projectService;
    @Autowired
    private ServiceMRequirement requirementService;
    @Autowired
    private ServiceMUser userService;

    // Attributes ----------------------------------------------------------
    /**
     * Listado paginado con las tareas
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMTask> tasksList;
    /**
     * Listaado con los proyectos asignados al usuario
     * @version 1.0
     * @since 1.0
     */
    private List<TGpeMProject> userProjects;
    /**
     * Listado con los requerimientos asociados al proyecto seleccionado
     * @version 1.0
     * @since 1.0
     */
    private List<TGpeMRequirement> projectRequirements;
    /**
     * Listado de los usuarios
     * @version 1.0
     * @since 1.0
     */
    private List<TGpeMUser> users;
    /**
     * Listado con los tipos de tareas existentes en el sistema
     * @version 1.0
     * @since 1.0
     */
    private List<EnumTaskType> taskTypes;
    /**
     * Listado con los estados de las tareas existentes en el sistema
     * @version 1.0
     * @since 1.0
     */
    private List<EnumTaskStatus> taskStatus;
    /**
     * Listado con las prioridades de las tareas existentes en el sistema
     * @version 1.0
     * @since 1.0
     */
    private List<EnumPriority> priorities;
    
    // Filters-------------------------------------------------------------
    
    /**
     * Filtro del código de la tarea
     * @version 1.0
     * @since 1.0
     */
    private String codeFilter;
    /**
     * Filtro del nombre de la tarea
     * @version 1.0
     * @since 1.0
     */
    private String nameFilter;
    /**
     * Filtro del proyecto
     * @version 1.0
     * @since 1.0
     */
    private TGpeMProject projectFilter;
    /**
     * Filtro del requerimiento
     * @version 1.0
     * @since 1.0
     */
    private TGpeMRequirement requirementFilter;
    /**
     * Filtro del tipo de tarea
     * @version 1.0
     * @since 1.0
     */
    private EnumTaskType typeFilter;
    /**
     * Filtro del estado de la tarea
     * @version 1.0
     * @since 1.0
     */
    private EnumTaskStatus statusFilter;
    /**
     * Filtro de la prioridad de la tarea
     * @version 1.0
     * @since 1.0
     */
    private EnumPriority priorityFilter;
    /**
     * Filtro del desarrollador
     * @version 1.0
     * @since 1.0
     */
    private TGpeMUser developerFilter;
    /**
     * Filtro de la fecha de inicio
     * @version 1.0
     * @since 1.0
     */
    private LocalDate startDateFilter;
    /**
     * Filtro de la fecha de fin
     * @version 1.0
     * @since 1.0
     */
    private LocalDate endDateFilter;

    // Init ---------------------------------------------------------------
    /**
     * @see com.awg.gpe.web.controllers.BaseController#init()
     * @version 1.0
     * @since 1.0
     */
    @Override
    @PostConstruct
    public void init() {
        initialize();
    }

    /**
     * @see com.awg.gpe.web.controllers.BaseController#reloadPage()
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected void reloadPage() {
        goToTaskList();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de listado de tareas
     * @version 1.0
     * @since 1.0
     */
    public void goToTaskList() {
        goToView(TaskListController.VIEW_TASK_LIST);
        clear();
    }

    // Methods --------------------------------------------------------------
    
    /**
     * Método que inserta los filtros y realiza la búsqueda
     * @version 1.0
     * @since 1.0
     */
    public void search() {
        GPELazyDataModelFilterBuilder builder = new GPELazyDataModelFilterBuilder();
        Map<String, Object> filters = builder
            .add(TGpeMTasksFilters.FILTER_CODE, this.codeFilter)
            .add(TGpeMTasksFilters.FILTER_NAME, this.nameFilter)
            .add(TGpeMTasksFilters.FILTER_PROJECT, this.projectFilter)
            .add(TGpeMTasksFilters.FILTER_REQUIREMENT, this.requirementFilter)
            .add(TGpeMTasksFilters.FILTER_TASK_TYPE, this.typeFilter)
            .add(TGpeMTasksFilters.FILTER_TASK_STATUS, this.statusFilter)
            .add(TGpeMTasksFilters.FILTER_TASK_PRIORITY, this.priorityFilter)
            .add(TGpeMTasksFilters.FILTER_DEVELOPER, this.developerFilter)
            .add(TGpeMTasksFilters.FILTER_START_DATE, this.startDateFilter)
            .add(TGpeMTasksFilters.FILTER_END_DATE, this.endDateFilter)
            .add(TGpeMTasksFilters.FILTER_USER, this.loggedUser)
            .build();
        
        resetDatatable();

        this.tasksList = new GPELazyDataModel<>(this.taskService, filters);
    }
    
    /**
     * Método que obtiene los requerimientos relacionados con el proyecto que el usuario ha seleccionado
     * @version 1.0
     * @since 1.0
     */
    public void requirementsByProject() {
        try {
            if (this.projectFilter != null) {
                projectRequirements = this.requirementService.findByProject(this.projectFilter);
            } else {
                projectRequirements = this.requirementService.findByUser(LoginService.getPrincipal());
            }
        } catch (ServiceException e) {
            TaskListController.log.error("Se ha producido un error al obtener los requerimientos del proyecto: " + e.getMessage());
        }
    }
    
    private void clear() {
        try {
            this.users = this.userService.findApplicationUsers();
            this.userProjects = this.projectService.findByUser(LoginService.getPrincipal());
            this.projectRequirements = this.requirementService.findByUser(LoginService.getPrincipal());
        } catch (ServiceException e) {
            TaskListController.log.error("Se ha producido un error al intentar traer de la base de datos los proyectos y/o requerimientos: " + e.getMessage());
        }
        if (this.taskTypes == null) {
            this.taskTypes = Arrays.asList(EnumTaskType.values());
        }
        if (this.taskStatus == null) {
            this.taskStatus = Arrays.asList(EnumTaskStatus.values());
        }
        if (this.priorities == null) {
            this.priorities = Arrays.asList(EnumPriority.values());
        }
        this.codeFilter = null;
        this.nameFilter = null;
        this.projectFilter = null;
        this.requirementFilter = null;
        this.typeFilter = null;
        this.statusFilter = null;
        this.priorityFilter = null;
        this.developerFilter = null;
        this.startDateFilter = null;
        this.endDateFilter = null;
        
        resetDatatable();
        GPELazyDataModelFilterBuilder builder = new GPELazyDataModelFilterBuilder();
        Map<String, Object> filters = builder
            .add(TGpeMTasksFilters.FILTER_USER, this.loggedUser)
            .build();
        this.tasksList = new GPELazyDataModel<>(this.taskService, filters);
    }

    // Getters and setters --------------------------------------------------

    public GPELazyDataModel<TGpeMTask> getTasksList() {
        return this.tasksList;
    }

    public void setTasksList(GPELazyDataModel<TGpeMTask> tasksList) {
        this.tasksList = tasksList;
    }

    public List<TGpeMProject> getUserProjects() {
        return this.userProjects;
    }

    public void setUserProjects(List<TGpeMProject> userProjects) {
        this.userProjects = userProjects;
    }

    public List<TGpeMRequirement> getProjectRequirements() {
        return this.projectRequirements;
    }

    public void setProjectRequirements(List<TGpeMRequirement> projectRequirements) {
        this.projectRequirements = projectRequirements;
    }

    public List<EnumTaskType> getTaskTypes() {
        return this.taskTypes;
    }

    public void setTaskTypes(List<EnumTaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }

    public List<EnumTaskStatus> getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(List<EnumTaskStatus> taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<EnumPriority> getPriorities() {
        return this.priorities;
    }

    public void setPriorities(List<EnumPriority> priorities) {
        this.priorities = priorities;
    }

    public String getCodeFilter() {
        return this.codeFilter;
    }

    public void setCodeFilter(String codeFilter) {
        this.codeFilter = codeFilter;
    }

    public String getNameFilter() {
        return this.nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public List<TGpeMUser> getUsers() {
        return this.users;
    }

    public void setUsers(List<TGpeMUser> users) {
        this.users = users;
    }

    public TGpeMProject getProjectFilter() {
        return this.projectFilter;
    }

    public void setProjectFilter(TGpeMProject projectFilter) {
        this.projectFilter = projectFilter;
    }

    public TGpeMRequirement getRequirementFilter() {
        return this.requirementFilter;
    }

    public void setRequirementFilter(TGpeMRequirement requirementFilter) {
        this.requirementFilter = requirementFilter;
    }

    public EnumTaskType getTypeFilter() {
        return this.typeFilter;
    }

    public void setTypeFilter(EnumTaskType typeFilter) {
        this.typeFilter = typeFilter;
    }

    public EnumTaskStatus getStatusFilter() {
        return this.statusFilter;
    }

    public void setStatusFilter(EnumTaskStatus statusFilter) {
        this.statusFilter = statusFilter;
    }

    public EnumPriority getPriorityFilter() {
        return this.priorityFilter;
    }

    public void setPriorityFilter(EnumPriority priorityFilter) {
        this.priorityFilter = priorityFilter;
    }

    public TGpeMUser getDeveloperFilter() {
        return this.developerFilter;
    }

    public void setDeveloperFilter(TGpeMUser developerFilter) {
        this.developerFilter = developerFilter;
    }

    public LocalDate getStartDateFilter() {
        return this.startDateFilter;
    }

    public void setStartDateFilter(LocalDate startDateFilter) {
        this.startDateFilter = startDateFilter;
    }

    public LocalDate getEndDateFilter() {
        return this.endDateFilter;
    }

    public void setEndDateFilter(LocalDate endDateFilter) {
        this.endDateFilter = endDateFilter;
    }
}
