package com.awg.gpe.web.controllers.project;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.awg.gpe.web.controllers.BaseController;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "newRequirementController")
@ViewScoped
@Deprecated
public class NewRequirementController extends BaseController {
    
    private static final Logger log = Logger.getLogger(NewRequirementController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_NEW_REQUIREMENT = "/views/projects/newRequirement.xhtml";

    // Services -----------------------------------------------------------

    // Attributes ----------------------------------------------------------

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
        goToNewRequirement();
    }

    // Navigation -----------------------------------------------------------
    public void goToNewRequirement() {
        goToView(NewRequirementController.VIEW_NEW_REQUIREMENT);
    }
}
