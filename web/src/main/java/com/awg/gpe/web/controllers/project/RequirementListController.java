package com.awg.gpe.web.controllers.project;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMRequirementFilters;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMRequirement;
import com.awg.gpe.data.services.LoginService;
import com.awg.gpe.data.services.ServiceMProject;
import com.awg.gpe.data.services.ServiceMRequirement;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.primefaces.GPELazyDataModel;
import com.awg.gpe.web.utils.GPELazyDataModelFilterBuilder;

/**
 * Controlador del listado de requerimientos
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "requirementListController")
@ViewScoped
public class RequirementListController extends BaseController {
    
    private static final Logger log = Logger.getLogger(RequirementListController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_REQUIREMENT_LIST = "/views/projects/requirementList.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMRequirement requirementService;
    @Autowired
    private ServiceMProject projectService;

    // Attributes ----------------------------------------------------------
    /**
     * Listado paginado de los requerimientos existentes en el sistema
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMRequirement> requirementList;
    /**
     * Listado de los proyectos asociados al usuario
     * @version 1.0
     * @since 1.0
     */
    private List<TGpeMProject> userProjects;
    
    // Filters -------------------------------------------------------------
    /**
     * Filtro del nombre de requerimiento 
     * @version 1.0
     * @since 1.0
     */
    private String nameFilter;
    /**
     * Filtro del código del requerimiento 
     * @version 1.0
     * @since 1.0
     */
    private String codeFilter;
    /**
     * Filtro del proyecto
     * @version 1.0
     * @since 1.0
     */
    private TGpeMProject projectFilter;
    /**
     * Filtro del archivado del requerimiento 
     * @version 1.0
     * @since 1.0
     */
    private Boolean archivedFilter;
    /**
     * Filtro de la fecha de inicio 
     * @version 1.0
     * @since 1.0
     */
    private LocalDate startDateFilter;
    /**
     * Filtro de la fecha de finalización 
     * @version 1.0
     * @since 1.0
     */
    private LocalDate endDateFilter;
    
    // Init ----------------------------------------------------------------
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
        goToRequirementList();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método de navegación a la pantalla de lista de requerimientos
     * @version 1.0
     * @since 1.0
     */
    public void goToRequirementList() {
        goToView(RequirementListController.VIEW_REQUIREMENT_LIST);
        clear();
        
    }

    // Methods --------------------------------------------------------------

    /**
     * Método que ejecuta la búsqueda con los filtros informados por el usuario
     * @version 1.0
     * @since 1.0
     */
    public void search() {
        GPELazyDataModelFilterBuilder builder = new GPELazyDataModelFilterBuilder();
        Map<String, Object> filters = builder
            .add(TGpeMRequirementFilters.FILTER_CODE, this.codeFilter)
            .add(TGpeMRequirementFilters.FILTER_NAME, this.nameFilter)
            .add(TGpeMRequirementFilters.FILTER_PROJECT, this.projectFilter)
            .add(TGpeMRequirementFilters.FILTER_ARCHIVED, this.archivedFilter)
            .add(TGpeMRequirementFilters.FILTER_START_DATE, this.startDateFilter)
            .add(TGpeMRequirementFilters.FILTER_END_DATE, this.endDateFilter)
            .build();

        this.requirementList = new GPELazyDataModel<>(this.requirementService, filters);
    }
    
    private void clear() {
        this.requirementList = new GPELazyDataModel<>(this.requirementService);
        try {
            this.userProjects = this.projectService.findByUser(LoginService.getPrincipal());
        } catch (ServiceException e) {
            RequirementListController.log.error("Se ha producido un error al acceder a los proyectos del usuario");
        }
        this.userProjects = null;
        this.nameFilter = null;
        this.codeFilter =  null;
        this.projectFilter = null;
        this.startDateFilter = null;
        this.endDateFilter = null;
    }

    // Getters and setters --------------------------------------------------

    public GPELazyDataModel<TGpeMRequirement> getRequirementList() {
        return this.requirementList;
    }

    public void setRequirementList(GPELazyDataModel<TGpeMRequirement> requirementList) {
        this.requirementList = requirementList;
    }

    public List<TGpeMProject> getUserProjects() {
        return this.userProjects;
    }

    public void setUserProjects(List<TGpeMProject> userProjects) {
        this.userProjects = userProjects;
    }

    public String getNameFilter() {
        return this.nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public String getCodeFilter() {
        return this.codeFilter;
    }

    public void setCodeFilter(String codeFilter) {
        this.codeFilter = codeFilter;
    }

    public TGpeMProject getProjectFilter() {
        return this.projectFilter;
    }

    public void setProjectFilter(TGpeMProject projectFilter) {
        this.projectFilter = projectFilter;
    }

    public Boolean getArchivedFilter() {
        return this.archivedFilter;
    }

    public void setArchivedFilter(Boolean archivedFilter) {
        this.archivedFilter = archivedFilter;
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
