package com.awg.gpe.web.controllers.project;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.enums.EnumMethodology;
import com.awg.gpe.data.filters.TGpeMProjectFilter;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.services.ServiceMProject;
import com.awg.gpe.data.services.ServiceMUser;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.primefaces.GPELazyDataModel;
import com.awg.gpe.web.utils.GPELazyDataModelFilterBuilder;

/**
 * Controlador que maneja el listado de proyectos
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "projectListController")
@ViewScoped
public class ProjectListController extends BaseController {
    
    private static final Logger log = Logger.getLogger(ProjectListController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_PROJECT_LIST = "/views/projects/projectList.xhtml";
    
    // Filters ------------------------------------------------------------
    private static final String FILTER_ACTIVE = "active";
    private static final String FILTER_CURRENT_USER = "user";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMProject projectService;
    @Autowired
    private ServiceMUser userService;

    // Attributes ----------------------------------------------------------
    /**
     * Listaado paginado de los proyectos del sistema
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMProject> projectList;
    /**
     * Colección con las metodologías del sistema
     * @version 1.0
     * @since 1.0
     */
    private List<EnumMethodology> methodologies;
    /**
     * Lista que contendrá a los usuarios capaces de ser jefes de proyecto o product owners
     * @since 1.0
     */
    private List<TGpeMUser> projectLeaders;
    /**
     * Lista que contendrá a todos los usuarios capaces de ser responsables de proyecto o scrum masters
     * @since 1.0
     */
    private List<TGpeMUser> projectManagers;

    // Filters -------------------------------------------------------------
    /**
     * Filtro del nombre de proyecto 
     * @version 1.0
     * @since 1.0
     */
    private String nameFilter;
    /**
     * Filtro del código de proyecto 
     * @version 1.0
     * @since 1.0
     */
    private String codeFilter;
    /**
     * Filtro de la metodología
     * @version 1.0
     * @since 1.0
     */
    private EnumMethodology methodologyFilter;
    /**
     * Filtro de proyecto archivado 
     * @version 1.0
     * @since 1.0
     */
    private Boolean archivedFilter;
    /**
     * Filtro del jefe de proyecto
     * @version 1.0
     * @since 1.0
     */
    private TGpeMUser projectLeaderFilter;
    /**
     * Filtro del responsable de proyecto
     * @version 1.0
     * @since 1.0
     */
    private TGpeMUser projectManagerFilter;
    private TGpeMUser productOwnerFilter;
    private TGpeMUser scrumMasterFilter;
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
        goToProjectList();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de listado de proyectos
     * @version 1.0
     * @since 1.0
     */
    public void goToProjectList() {
        goToView(ProjectListController.VIEW_PROJECT_LIST);
        projectLeaders = this.userService.findProjectLeaders();
        projectManagers = this.userService.findProjectManagers();
        clear();
    }
    
    /**
     * Método que ejecuta la navegación hacia la pantalla de listado de proyectos con filtros precargados
     * @param filter
     */
    public void goToProjectList(String filter) {
    	clear();
    	goToView(ProjectListController.VIEW_PROJECT_LIST);
        projectLeaders = this.userService.findProjectLeaders();
        projectManagers = this.userService.findProjectManagers();
        
        switch(filter) {
        case ProjectListController.FILTER_ACTIVE:
        	GPELazyDataModelFilterBuilder builder = new GPELazyDataModelFilterBuilder();
            Map<String, Object> filters = builder
            	.add(TGpeMProjectFilter.FILTER_END_DATE, null)
            	.add(TGpeMProjectFilter.FILTER_ARCHIVED, false)
            .build();
            projectList = new GPELazyDataModel<>(this.projectService, filters);
        	break;
        case ProjectListController.FILTER_CURRENT_USER:
        	builder = new GPELazyDataModelFilterBuilder();
        	if (this.loggedUser.isProjectLeader()) {
        		filters = builder
                    	.add(TGpeMProjectFilter.FILTER_PROJECT_LEADER, this.loggedUser)
                    .build();
        	} else if (this.loggedUser.isProjectManager()) {
        		filters = builder
                    	.add(TGpeMProjectFilter.FILTER_PROJECT_MANAGER, this.loggedUser)
                    .build();
        	} else {
        		filters = builder.build();
        	}
            projectList = new GPELazyDataModel<>(this.projectService, filters);
        	break;
    	default:
    		break;
        }
    }

    // Methods --------------------------------------------------------------
    private void clear() {
        this.projectList = new GPELazyDataModel<>(this.projectService);
        nameFilter = null;
        codeFilter = null;
        methodologyFilter = null;
        projectLeaderFilter = null;
        projectManagerFilter = null;
        productOwnerFilter = null;
        scrumMasterFilter = null;
        methodologies = Arrays.asList(EnumMethodology.values());
        
    }

    /**
     * Método que ejecuta la búsqueda por los filtros introducidos por el usuario
     * 
     * @version 1.0
     * @since 1.0
     */
    public void search() {
        GPELazyDataModelFilterBuilder builder = new GPELazyDataModelFilterBuilder();
        Map<String, Object> filters = builder
            .add(TGpeMProjectFilter.FILTER_NAME, this.nameFilter)
            .add(TGpeMProjectFilter.FILTER_CODE, this.codeFilter)
            .add(TGpeMProjectFilter.FILTER_METHODOLOGY, this.methodologyFilter)
            .add(TGpeMProjectFilter.FILTER_ARCHIVED, this.archivedFilter)
            .add(TGpeMProjectFilter.FILTER_PROJECT_LEADER, this.projectLeaderFilter)
            .add(TGpeMProjectFilter.FILTER_PROJECT_MANAGER, this.projectManagerFilter)
            .add(TGpeMProjectFilter.FILTER_PRODUCT_OWNER, this.productOwnerFilter)
            .add(TGpeMProjectFilter.FILTER_SCRUM_MASTER, this.scrumMasterFilter)
            .add(TGpeMProjectFilter.FILTER_START_DATE, this.startDateFilter)
            .add(TGpeMProjectFilter.FILTER_END_DATE, this.endDateFilter)
            .build();

        this.projectList = new GPELazyDataModel<>(this.projectService, filters);
    }

    // Getters and setters --------------------------------------------------

    public GPELazyDataModel<TGpeMProject> getProjectList() {
        return this.projectList;
    }

    public void setProjectList(GPELazyDataModel<TGpeMProject> projectList) {
        this.projectList = projectList;
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

    public EnumMethodology getMethodologyFilter() {
        return this.methodologyFilter;
    }

    public void setMethodologyFilter(EnumMethodology methodologyFilter) {
        this.methodologyFilter = methodologyFilter;
    }

    public Boolean getArchivedFilter() {
        return this.archivedFilter;
    }

    public void setArchivedFilter(Boolean archivedFilter) {
        this.archivedFilter = archivedFilter;
    }

    public TGpeMUser getProjectLeaderFilter() {
        return this.projectLeaderFilter;
    }

    public void setProjectLeaderFilter(TGpeMUser projectLeaderFilter) {
        this.projectLeaderFilter = projectLeaderFilter;
    }

    public TGpeMUser getProjectManagerFilter() {
        return this.projectManagerFilter;
    }

    public void setProjectManagerFilter(TGpeMUser projectManagerFilter) {
        this.projectManagerFilter = projectManagerFilter;
    }

    public TGpeMUser getProductOwnerFilter() {
        return this.productOwnerFilter;
    }

    public void setProductOwnerFilter(TGpeMUser productOwnerFilter) {
        this.productOwnerFilter = productOwnerFilter;
    }

    public TGpeMUser getScrumMasterFilter() {
        return this.scrumMasterFilter;
    }

    public void setScrumMasterFilter(TGpeMUser scrumMasterFilter) {
        this.scrumMasterFilter = scrumMasterFilter;
    }

    public List<EnumMethodology> getMethodologies() {
        return this.methodologies;
    }

    public void setMethodologies(List<EnumMethodology> methodologies) {
        this.methodologies = methodologies;
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

    public List<TGpeMUser> getProjectLeaders() {
        return this.projectLeaders;
    }

    public void setProjectLeaders(List<TGpeMUser> projectLeaders) {
        this.projectLeaders = projectLeaders;
    }

    public List<TGpeMUser> getProjectManagers() {
        return this.projectManagers;
    }

    public void setProjectManagers(List<TGpeMUser> projectManagers) {
        this.projectManagers = projectManagers;
    }

}
