package com.awg.gpe.web.controllers.task;

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
@ManagedBean(name = "asignTaskController")
@ViewScoped
@Deprecated
public class AsignTaskController extends BaseController {
    
    private static final Logger log = Logger.getLogger(AsignTaskController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_ASIGN_TASK = "/views/tasks/assignTask.xhtml";

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
        goToAssignTask();
    }

    // Navigation -----------------------------------------------------------
    public void goToAssignTask() {
        goToView(AsignTaskController.VIEW_ASIGN_TASK);
    }

}
