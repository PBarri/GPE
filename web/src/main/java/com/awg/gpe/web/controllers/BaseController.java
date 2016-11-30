package com.awg.gpe.web.controllers;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.services.LoginService;

/**
 * Clase base para todos los controladores.
 * <p>Esta clase provee soporte para las siguientes funcionalidades comunes:
 * <ul>
 * <li>Inyección de beans de Spring, en el método</li>
 * <li>Navegación mediante Ajax</li>
 * <li>Creación de alertas javascript desde los controladores</li>
 * </ul>
 * 
 * <p>Todos los controladores de la aplicación deberán extender de esta clase
 *  
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseController implements Serializable {

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@link ResourceBundle} que contiene los mensajes de la aplicación
     * 
     * @since 1.0
     */
    @ManagedProperty("#{msg}")
    protected ResourceBundle messages;
    
    /**
     * Atributo al que se enlazará el datatable solo en el caso de que sea necesario
     * @since 1.0
     */
    protected DataTable datatable;
    
    /**
     * Atributo que muestra al usuario logado
     * @since 1.0
     */
    protected TGpeMUser loggedUser;
    
    /**
     * Atributo estático que recoge el tipo de alerta de error
     */
    public static final String ALERT_ERROR = "error";

    /**
     * Atributo estático que recoge el tipo de alerta de éxito
     */
    public static final String ALERT_SUCCESS = "success";

    /**
     * Atributo estático que recoge el tipo de alerta de información
     */
    public static final String ALERT_INFO = "info";

    /**
     * Atributo estático que recoge el tipo de alerta de alerta
     */
    public static final String ALERT_WARNING = "warning";

    /**
     * Método que se usa para inicializar el controlador.
     * <p>Este método debe ser implementado en todos los controladores con la siguiente configuración mínima:
     * <ul>
     * <li>Debe estar anotado con la etiqueta <code>@PostConstruct</code></li>
     * <li>Debe invocar al método <code>initialize()</code> de la clase <code>BaseController</code>
     * </ul></p>
     * 
     * @version 1.0
     * @since 1.0
     */
    public abstract void init();

    /**
     * Método que se tiene que implementar en todos los controladores para controlar el caso de que el usuario recargue la página mediante la tecla F5
     * 
     * @version 1.0
     * @since 1.0
     */
    protected abstract void reloadPage();

    /**
     * Método que provee soporte para la inyección de beans de Spring. Este método debe ser invocado desde el método init.
     * <p>Este método también invoca una recarga de página</p>
     * 
     * @version 1.0
     * @since 1.0
     */
    @PostConstruct
    public void initialize() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getAutowireCapableBeanFactory().autowireBean(this);
        // Actualizamos el usuario logado
        this.loggedUser = LoginService.getPrincipal();
        // Lanzamos una recarga de página, ya que es
        reloadPage();
    }

    /**
     * Método que devuelve el controlador almacenado en sesión que contiene información acerca del usuario logado y sus preferencias, así como los datos de navegación
     * 
     * @return {@link HomeController}
     * @version 1.0
     * @since 1.0
     */
    protected HomeController getHomeController() {
        return (HomeController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("homeController");
    }

    /**
     * Método que crea un mensaje de alerta en la aplicación con el formato de la librería sweet alert.
     * 
     * @param message
     *            - Mensaje de la alerta
     * @param animation
     *            - Animación de la alerta
     * @param type
     *            - Tipo de la alerta
     * @version 1.0
     * @since 1.0
     */
    protected void swal(String message, String animation, String type) {
        execute(createAlert(message, animation, type));
    }

    /**
     * Método que permite ejecutar un script de javascript desde el servidor
     * 
     * @param script un string conteniendo un script de javascript
     * @version 1.0
     * @since 1.0
     */
    protected void execute(String script) {
        RequestContext.getCurrentInstance().execute(script);
    }
    
    /**
     * Método que permite volver a cargar un componente
     * 
     * @param componentId Id del componente en la página
     * @version 1.0
     * @since 1.0
     */
    protected void update(String componentId) {
        RequestContext.getCurrentInstance().update(componentId);
    }
    
    /**
     * Devuelve la tabla a la primera página, solo en el caso de que se haya enlazado una
     * @version 1.0
     * @since 1.0
     */
    protected void resetDatatable() {
        if (this.datatable != null) {
            this.datatable.setFirst(0);
        }
    }

    /**
     * Método interno que se usa para crear el script de javascript que se va a ejecutar
     * 
     * @param message
     *            - Mensaje de la alerta
     * @param animation
     *            - Animación de la alerta
     * @param type
     *            - Tipo de la alerta
     * @return el script de la alerta
     * @version 1.0
     * @since 1.0
     */
    private String createAlert(String message, String animation, String type) {
        String script = "swal({";
        if (message != null) {
            script += "text: \"" + message + "\"";
        }
        script += ",type: \"" + ((type != null) ? type : "info") + "\"";
        script += "});";

        return script;
    }

    /**
     * Método que ejecuta la navegación hasta la vista pasada por parámetro
     * @param view ruta de la vista
     * @version 1.0
     * @since 1.0
     */
    protected void goToView(String view) {
        getHomeController().setActualPage(view);
    }
    
    /**
	 * @return the messages
	 */
	public ResourceBundle getMessages() {
		return this.messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	/**
	 * @return the datatable
	 */
	public DataTable getDatatable() {
		return this.datatable;
	}

	/**
	 * @param datatable the datatable to set
	 */
	public void setDatatable(DataTable datatable) {
		this.datatable = datatable;
	}

	/**
     * Método auxiliar para obtener en las vistas el valor de la fecha actual. Para ello será necesario llamar al atributo
     * <code>currentDate</code>, aunque no exista como tal en el controlador
     * @return {@link LocalDate} con la fecha actual
     * @version 1.0
     * @since 1.0
     */
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * Método auxiliar para obtener en las vistas la fecha y hora actuales. Para ello será necesario llamar al atributo
     * <code>currentTimestamp</code>, aunque no exista como tal en el controlador
     * @return {@link LocalDateTime} con la fecha y hora actuales
     * @version 1.0
     * @since 1.0
     */
    public LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }

	/**
	 * @return the loggedUser
	 */
	public TGpeMUser getLoggedUser() {
		return this.loggedUser;
	}

	/**
	 * @param loggedUser the loggedUser to set
	 */
	public void setLoggedUser(TGpeMUser loggedUser) {
		this.loggedUser = loggedUser;
	}
}
