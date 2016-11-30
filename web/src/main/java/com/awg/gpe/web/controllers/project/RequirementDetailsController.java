package com.awg.gpe.web.controllers.project;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMRequirement;
import com.awg.gpe.data.model.TGpeMTask;
import com.awg.gpe.data.services.ServiceMRequirement;
import com.awg.gpe.web.controllers.BaseController;

/**
 * Controlador que maneja el detalle del requerimiento
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "requirementDetailsController")
@ViewScoped
public class RequirementDetailsController extends BaseController {
    
    private static final Logger log = Logger.getLogger(RequirementDetailsController.class);
    
    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_REQUIREMENT_DETAIL = "/views/projects/requirementDetail.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMRequirement requirementService;

    // Attributes ----------------------------------------------------------
    /**
     * Requerimiento seleccionado
     * @version 1.0
     * @since 1.0
     */
    private TGpeMRequirement requirement;
    
    /**
     * Listado con las tareas principales del requerimiento
     * @version 1.0
     * @since 1.0
     */
    private List<TGpeMTask> requirementParentTasks;

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
        goToRequirementDetails();

    }
    
    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de detalle del requerimiento
     * @version 1.0
     * @since 1.0
     */
    public void goToRequirementDetails() {
        goToView(RequirementDetailsController.VIEW_REQUIREMENT_DETAIL);
    }
    
    /**
     *  Método que ejecuta la navegación hacia la pantalla de detalle del requerimiento
     * 
     * @param requirement Requerimiento del que se quieren ver los detalles
     * @version 1.0
     * @since 1.0
     */
    public void goToRequirementDetails(TGpeMRequirement requirement) {
        try {
            this.requirement = this.requirementService.findCompleteRequirement(requirement);
            requirementParentTasks = this.requirement.getTasks().stream()
            		.filter(t -> t.getParent() == null)
            		.sorted((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate()))
            		.collect(Collectors.toList());
            goToView(RequirementDetailsController.VIEW_REQUIREMENT_DETAIL);
        } catch (ServiceException e) {
            RequirementDetailsController.log.error("Se ha producido un error al acceder al requerimiento deseado: " + e.getMessage());
            swal("Se ha producido un error, inténtelo de nuevo dentro de unos minutos", null, "error");
        }
    }

    
    // Getters and setters --------------------------------------------------
    
    public TGpeMRequirement getRequirement() {
        return this.requirement;
    }

    public void setRequirement(TGpeMRequirement requirement) {
        this.requirement = requirement;
    }

	public List<TGpeMTask> getRequirementParentTasks() {
		return this.requirementParentTasks;
	}

	public void setRequirementParentTasks(List<TGpeMTask> requirementParentTasks) {
		this.requirementParentTasks = requirementParentTasks;
	}

}
