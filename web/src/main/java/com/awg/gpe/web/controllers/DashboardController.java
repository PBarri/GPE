package com.awg.gpe.web.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.dto.AdminDashboard;
import com.awg.gpe.data.dto.DeveloperDashboard;
import com.awg.gpe.data.dto.LeaderDashboard;
import com.awg.gpe.data.dto.ManagerDashboard;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMReport;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpeMVacation;
import com.awg.gpe.data.services.DashboardService;
import com.awg.gpe.data.services.ServiceMUser;

/**
 * Controlador encargado de manejar las acciones realizadas por el usuario en la pantalla de dashboard.
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "dashboardController")
@ViewScoped
public class DashboardController extends BaseController {
    
    private static final Logger log = Logger.getLogger(DashboardController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_DASHBOARD = "/views/dashboard.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private ServiceMUser userService;

    // Attributes ----------------------------------------------------------
    /**
     * Atributo conteniendo los datos del dashboard del administrador
     * @version 1.0
     * @since 1.0
     */
    private AdminDashboard admin;
    
    /**
     * Atributo conteniendo los datos del dashboard del jefe de proyecto
     * @version 1.0
     * @since 1.0
     */
    private LeaderDashboard leader;
    
    /**
     * Atributo conteniendo los datos del dashboard del responsable de proyecto
     * @version 1.0
     * @since 1.0
     */
    private ManagerDashboard manager;
    
    /**
     * Atributo conteniendo los datos del dashboard del desarrollador
     * @version 1.0
     * @since 1.0
     */
    private DeveloperDashboard developer;

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
        goToDashboard();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que se ocupa de la navegación hacia el dashboard. Este método se ocupa de cargar los datos
     * dependiendo del rol del usuario que invoque al método.
     * @version 1.0
     * @since 1.0
     */
    public void goToDashboard() {
        goToView(DashboardController.VIEW_DASHBOARD);
        loadDashboard();
    }
    
    private void loadDashboard() {
    	if (this.loggedUser != null) {
    		if (this.loggedUser.isAdministrator()) {
                this.admin = this.dashboardService.adminDashboard();
    		} else if (this.loggedUser.isProjectLeader()) {
    			TGpeMUser user;
				try {
					user = this.userService.findLeaderDashboardUser(this.loggedUser);
                    this.leader = this.dashboardService.leaderDashboard(user);
				} catch (ServiceException e) {
					swal("Se ha producido un error desconocido", null, BaseController.ALERT_ERROR);
				}			
    		} else if (this.loggedUser.isProjectManager()) {
    			TGpeMUser user;
				try {
					user = this.userService.findManagerDashboardUser(this.loggedUser);
                    this.manager = this.dashboardService.managerDashboard(user);
				} catch (ServiceException e) {
					swal("Se ha producido un error desconocido", null, BaseController.ALERT_ERROR);
				}    			
    		} else if (this.loggedUser.isDeveloper()) {
                this.developer = this.dashboardService.developerDashboard(this.loggedUser);
    		} else {
    			swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		}
    	} else {
    		swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		
    	}
    }
    
    /**
     * Método que se encarga de aprobar un periodo de vacaciones.
     * <p>
     * Este método no puede ser ejecutado por un desarrollador.
     * </p>
     * @param vacation Periodo de vacaciones a aprobar
     * @version 1.0
     * @since 1.0
     */
    public void approveVacation(TGpeMVacation vacation) {
    	try {
            this.dashboardService.manageVacation(vacation, true);
    		reloadVacations(vacation);
    		swal("Se han aprobado las vacaciones", null, "success");
    	} catch (ServiceException e) {
    		swal("Se ha producido un error", null, "error");
    	}
    }
    
    /**
     * Método que se encarga de rechazar un periodo de vacaciones.
     * <p>
     * Este método no puede ser ejecutado por un desarrollador.
     * </p>
     * @param vacation Periodo de vacaciones a aprobar
     * @version 1.0
     * @since 1.0
     */
    public void rejectVacation(TGpeMVacation vacation) {
    	try {
            this.dashboardService.manageVacation(vacation, false);
    		reloadVacations(vacation);
    		swal("Se han aprobado las vacaciones", null, "success");
    	} catch (ServiceException e) {
    		swal("Se ha producido un error", null, "error");
    	}
    }
    
    private void reloadVacations(TGpeMVacation vacation) {
    	if (this.leader != null && this.leader.getVacationsToApprove() != null && !this.leader.getVacationsToApprove().isEmpty()) {
            this.leader.getVacationsToApprove().remove(vacation);
    	}
    	if (this.manager != null && this.manager.getVacationsToApprove() != null && !this.manager.getVacationsToApprove().isEmpty()) {
            this.manager.getVacationsToApprove().remove(vacation);
    	}
    }
    
    /**
     * Método que se encarga de descargar un informe
     * 
     * @param report Informe a descargar
     * @version 1.0
     * @since 1.0
     */
    public void downloadReport(TGpeMReport report) {
    	
    }

    // Getters and setters --------------------------------------------------
    
	/**
	 * @return the userService
	 */
	public ServiceMUser getUserService() {
		return this.userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(ServiceMUser userService) {
		this.userService = userService;
	}

	/**
	 * @return the admin
	 */
	public AdminDashboard getAdmin() {
		return this.admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(AdminDashboard admin) {
		this.admin = admin;
	}

	/**
	 * @return the leader
	 */
	public LeaderDashboard getLeader() {
		return this.leader;
	}

	/**
	 * @param leader the leader to set
	 */
	public void setLeader(LeaderDashboard leader) {
		this.leader = leader;
	}

	/**
	 * @return the manager
	 */
	public ManagerDashboard getManager() {
		return this.manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(ManagerDashboard manager) {
		this.manager = manager;
	}

	/**
	 * @return the developer
	 */
	public DeveloperDashboard getDeveloper() {
		return this.developer;
	}

	/**
	 * @param developer the developer to set
	 */
	public void setDeveloper(DeveloperDashboard developer) {
		this.developer = developer;
	}
}
