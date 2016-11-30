package com.awg.gpe.web.controllers.project;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.services.ServiceMProject;
import com.awg.gpe.web.controllers.BaseController;

/**
 * Controlador que maneja las acciones en el detalle de la tarea
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "projectDetailsController")
@ViewScoped
public class ProjectDetailsController extends BaseController {
    
    private static final Logger log = Logger.getLogger(ProjectDetailsController.class);
    
    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_PROJECT_DETAILS = "/views/projects/projectDetail.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMProject projectService;

    // Attributes ----------------------------------------------------------
    /**
     * Proyecto seleccionado
     * @version 1.0
     * @since 1.0
     */
    private TGpeMProject project;

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
        goToProjectDetails();
    }
    
    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de detalle de proyecto
     * @version 1.0
     * @since 1.0
     */
    public void goToProjectDetails() {
        goToView(ProjectDetailsController.VIEW_PROJECT_DETAILS);
    }
    
    /**
     * Método que ejecuta la navegación hacia la pantalla de detalle de proyecto
     * 
     * @param project El proyecto del que se quieren ver los detalles
     * @version 1.0
     * @since 1.0
     */
    public void goToProjectDetails(TGpeMProject project) {
        try {
            this.project = this.projectService.findCompleteProject(project);
            goToView(ProjectDetailsController.VIEW_PROJECT_DETAILS);
        } catch (ServiceException e) {
            ProjectDetailsController.log.error("Se ha producido un error al buscar el proyecto: " + e.getMessage());
            swal("Se ha producido un error, inténtelo de nuevo dentro de unos minutos", null, "error");
        }
    }

    
    // Getters and setters --------------------------------------------------
    
    public TGpeMProject getProject() {
        return this.project;
    }

    public void setProject(TGpeMProject project) {
        this.project = project;
    }

}
