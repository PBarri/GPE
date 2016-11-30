package com.awg.gpe.web.controllers.profile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.services.LoginService;
import com.awg.gpe.data.services.ServiceMUser;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.utils.validations.ValidatorBuilder;

/**
 * Controlador encargado de gestionar el perfil de un usuario
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "profileController")
@ViewScoped
public class ProfileController extends BaseController {
    
    private static final Logger log = Logger.getLogger(ProfileController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_PROFILE = "/views/profile/profile.xhtml";

    // Services ------------------------------------------------------------
    @Autowired
    private ServiceMUser userService;

    // Attributes ----------------------------------------------------------
    /**
     * Usuario mostrado en la pantalla de perfil
     * @since 1.0
     */
    private TGpeMUser user;
    
    /**
     * Atributo que indica si el usuario que accede al perfil tiene permiso para editar la información
     * @since 1.0
     */
    private Boolean allowedToEdit;
    /**
     * Atributo que sirve para controlar si el usuario está editando sus datos
     * @since 1.0
     */
    private Boolean isEditing;
    
    /**
     * Atributo para recoger la nueva contraseña
     * @version 1.0
     * @since 1.0
     */
    private String newPassword;
    
    
    // Init ----------------------------------------------------------------
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
        goToProfile();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que navega hacia la página del perfil.
     * <p> 
     * Se comprobará si el usuario que ha accedido al perfil es el mismo que el usuario logado, y se
     * buscarán sus tareas asignadas y el histórico de proyectos en los que ha estado
     * </p>
     * @version 1.0
     * @since 1.0
     */
    public void goToProfile() {
        goToView(ProfileController.VIEW_PROFILE);
        clear();
        user = getHomeController().getUser();
        allowedToEdit = true;
    }
    
    /**
     * Método que permite a los administradores
     * 
     * @param user El usuario del que se quiere ver el perfil
     * @version 1.0
     * @since 1.0
     */
    public void goToProfile(TGpeMUser user) {
        TGpeMUser logedUser = LoginService.getPrincipal();
        if (user != null) {
            if (logedUser.equals(user)) {
                // Si el usuario seleccionado somos nosotros vamos a nuestro perfil
                goToProfile();
            } else {
                // Cogemos la lista de tareas asignadas y el histórico de proyectos
                try {
                    this.user = this.userService.findProfileByIdentifier(user.getIdentifier());
                    goToView(ProfileController.VIEW_PROFILE);
                } catch (ServiceException e) {
                    ProfileController.log.error("Se ha producido un error al cargar los datos del usuario: " + e.getMessage());
                    swal("Se ha producido un error al cargar el perfil", null, "error");
                }
                allowedToEdit = (logedUser.isAdministrator());
            }            
        }
    }
    
    private void clear() {
        isEditing = false;
    }
    
    // Internal Methods -----------------------------------------------------
    
    /**
     * Método que marca al usuario para edición
     * @version 1.0
     * @since 1.0
     */
    public void edit() {
        isEditing = true;
    }
    
    /**
     * Método que cambiar la fotografía de perfil del usuario
     * @version 1.0
     * @since 1.0
     */
    public void changeAvatar() {
        
    }
    
    /**
     * Método que permite cambiar la contraseña del usuario
     * @version 1.0
     * @since 1.0
     */
    public void changePassword() {
        if (validatePassword()) {
            try {
                this.userService.changePassword(this.user);
                clear();
                swal("Se ha cambiado la contraseña correctamente", null, "success");
            } catch (ServiceException e) {
                ProfileController.log.error("Se ha producido un error al cambiar la contraseña del usuario: " + e.getMessage());
                swal("Se ha producido un error al cambiar la contraseña", null, "error");
            }
        } else {
            swal("La contraseña introducida no es correcta", null, "error");
        }
    }
    
    /**
     * Método que valida y guarda los cambios que el usuario haya realizado sobre su perfil
     * @version 1.0
     * @since 1.0
     */
    public void save() {
        if (validateUser()) {
            try {
                this.userService.save(this.user);
                clear();
                swal("Se han guardado los cambios correctamente", null, "success");
            } catch (ServiceException e) {
                ProfileController.log.error("Se ha producido un error al guardar los datos del usuario en base de datos: " + e.getMessage());
                swal("Se ha producido un error al guardar los cambios", null, "error");
            }
        } else {
            swal("Existen errores en el formulario", null, "error");
        }
    }
    
    private Boolean validateUser() {
        return ValidatorBuilder.newInstance()
            .field(this.user.getEmail())
                .required()
                .email()
                .and()
            .field(this.user.getRole())
                .required()
                .and()
            .field(this.user.getName())
                .required()
                .maxLength(20)
                .and()
            .field(this.user.getSurname())
                .required()
                .maxLength(50)
                .and()
            .field(this.user.getIsEnabled())
                .required()
                .and()
            .field(this.user.getUserPosition())
                .required()
                .and()
        .validate();
    }
    
    private Boolean validatePassword() {
        return ValidatorBuilder.newInstance()
            .field(this.user.getPassword())
                .required()
                .rangeLength(8, 20)
                .sameText(this.newPassword)
                .and()
        .validate();
    }

    // Getters and setters ---------------------------------------------------
    
    public Boolean getIsEditing() {
        return this.isEditing;
    }

    public void setIsEditing(Boolean isEditing) {
        this.isEditing = isEditing;
    }

    public TGpeMUser getUser() {
        return this.user;
    }

    public void setUser(TGpeMUser user) {
        this.user = user;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Boolean getAllowedToEdit() {
        return this.allowedToEdit;
    }

    public void setAllowedToEdit(Boolean allowedToEdit) {
        this.allowedToEdit = allowedToEdit;
    }

}
