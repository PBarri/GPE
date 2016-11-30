package com.awg.gpe.web.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.services.LoginService;

/**
 * Controlador principal de la aplicación.
 * <p>Este controlador se guarda en sesión y maneja los datos del usuario logado y de la navegación.</p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean
@SessionScoped
public class HomeController extends BaseController {
    
    private static final Logger log = Logger.getLogger(HomeController.class);

    private static final long serialVersionUID = 1L;

    // Attributes -----------------------------------------------------------
    /**
     * Atributo que almacena el usuario logado en la sesión del navegador
     * 
     * @since 1.0
     */
    private TGpeMUser user;

    /**
     * Atributo que indica la página en la que el usuario se encuentra actualmente
     * 
     * @since 1.0
     */
    private String actualPage;

    /**
     * Atributo que contiene la página de inicio
     * <p>Este atributo sirve tanto para cuando el usuario se logue en la aplicación como para cuando el usuario presione F5</p>
     * <p>En el caso de que el usuario sea administrador, jefe de proyecto o responsable, esta se inicializará al Dashboard</p>
     * <p>En el caso de que el usuario sea un usuario normal, esta se inicializará a su propio dashboard</p>
     * 
     * @since 1.0
     */
    @Value("${gpe.welcomePage}")
    private String initPage;

    // Initialize -----------------------------------------------------------

    /**
     * Método que inicializa el controlador la primera vez que se ejecuta
     * 
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
     * Al recargar la página, se setea de nuevo la página de inicio y se recarga el usuario logado
     * 
     * @see com.awg.gpe.web.controllers.BaseController#reloadPage()
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected void reloadPage() {
        if (this.user == null) {
            this.user = LoginService.getPrincipal();
        }
        actualPage = initPage;
    }
    
    // Getters and setters --------------------------------------------------

	/**
	 * @return the user
	 */
	public TGpeMUser getUser() {
		return this.user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(TGpeMUser user) {
		this.user = user;
	}

	/**
	 * @return the actualPage
	 */
	public String getActualPage() {
		return this.actualPage;
	}

	/**
	 * @param actualPage the actualPage to set
	 */
	public void setActualPage(String actualPage) {
		this.actualPage = actualPage;
	}

	/**
	 * @return the initPage
	 */
	public String getInitPage() {
		return this.initPage;
	}

	/**
	 * @param initPage the initPage to set
	 */
	public void setInitPage(String initPage) {
		this.initPage = initPage;
	}

}