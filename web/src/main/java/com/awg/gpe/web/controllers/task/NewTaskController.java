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
@ManagedBean(name = "newTaskController")
@ViewScoped
@Deprecated
public class NewTaskController extends BaseController {
    
    private static final Logger log = Logger.getLogger(NewTaskController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_NEW_TASK = "/views/tasks/newTask.xhtml";

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
        goToNewTask();
    }

    // Navigation -----------------------------------------------------------
    public void goToNewTask() {
        goToView(NewTaskController.VIEW_NEW_TASK);
    }

}
