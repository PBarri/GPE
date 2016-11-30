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
@ManagedBean(name = "incurTaskController")
@ViewScoped
@Deprecated
public class IncurTaskController extends BaseController {
    
    private static final Logger log = Logger.getLogger(IncurTaskController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_INCUR_TASK = "/views/tasks/incurTask.xhtml";

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
        goToIncurTask();
    }

    // Navigation -----------------------------------------------------------
    public void goToIncurTask() {
        goToView(IncurTaskController.VIEW_INCUR_TASK);
    }
}
