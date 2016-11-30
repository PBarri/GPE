package com.awg.gpe.web.controllers.user;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.enums.EnumRoles;
import com.awg.gpe.data.enums.EnumUserPosition;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMRole;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpePUserCategory;
import com.awg.gpe.data.services.ServiceMUser;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.utils.validations.ValidatorBuilder;

/**
 * Controlador que maneja la pantalla de alta de usuario
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "newUserController")
@ViewScoped
public class NewUserController extends BaseController {
    
    private static final Logger log = Logger.getLogger(NewUserController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_NEW_USER = "/views/users/newUser.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMUser userService;

    // Attributes ----------------------------------------------------------
    /**
     * Atributo que contiene la lista de usuarios
     * 
     * @since 1.0
     */
    private TGpeMUser newUser;

    /**
     * Rol seleccionado para el nuevo usuario
     * @version 1.0
     * @since 1.0
     */
    private EnumRoles userRole;

    /**
     * Categoría profesional para el nuevo usuario
     * @version 1.0
     * @since 1.0
     */
    private EnumUserPosition userCategory;

    /**
     * Atributo que sirve para controlar que las dos contraseñas insertadas sean las mismas
     * 
     * @since 1.0
     */
    private String passwordCheck;

    /**
     * Lista de los roles existentes en el sistema
     * @version 1.0
     * @since 1.0
     */
    private List<EnumRoles> roles;

    /**
     * Lista de las categorías profesionales existentes en el sistema
     * @version 1.0
     * @since 1.0
     */
    private List<EnumUserPosition> userCategories;

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
        this.roles = Arrays.asList(EnumRoles.values());
        this.userCategories = Arrays.asList(EnumUserPosition.values());
    }

    /**
     * @see com.awg.gpe.web.controllers.BaseController#reloadPage()
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected void reloadPage() {
        goToNewUser();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que inserta la vista de alta de usuarios en la página
     * 
     * @version 1.0
     * @since 1.0
     */
    public void goToNewUser() {
        goToView(NewUserController.VIEW_NEW_USER);
        clear();
    }

    /**
     * Método encargado de limpiar el formulario
     * 
     * @version 1.0
     * @since 1.0
     */
    private void clear() {
        newUser = new TGpeMUser();
        passwordCheck = null;
        userRole = null;
        userCategory = null;
    }

    // Methods --------------------------------------------------------------

    /**
     * Método encargado de guardar el usuario que haya sido introducido en el formulario. Antes de guardarlo lo valida en {@link NewUserController#validateNewUser()}
     * <p>
     * Si pasa la validación, se guarda en la base de datos
     * </p>
     * 
     * @version 1.0
     * @since 1.0
     */
    public void createUser() {
        if (this.newUser != null) {
            this.newUser.setRole(new TGpeMRole(this.userRole));
            this.newUser.setUserPosition(new TGpePUserCategory(this.userCategory));
            if (validateNewUser()) {
                try {
                    this.userService.createNewUser(this.newUser);
                    clear();
                    swal("Se ha insertado el usuario correctamente", null, "success");
                } catch (ServiceException e) {
                    swal("Se ha producido un error al guardar en base de datos", null, "error");
                }
            } else {
                swal("El nuevo usuario tiene errores en el formulario", null, "error");
            }
        } else {
            swal("Se ha producido un error", null, "error");
        }
    }

    private Boolean validateNewUser() {
        ValidatorBuilder newUserValidator = new ValidatorBuilder();
        
        return newUserValidator
            .field(this.newUser.getIdentifier())
                .required()
                .rangeLength(5, 20)
                .and()
            .field(this.newUser.getEmail())
                .required()
                .email()
                .and()
            .field(this.newUser.getRole())
                .required()
                .and()
            .field(this.newUser.getPassword())
                .required()
                .rangeLength(8, 20)
                .sameText(this.passwordCheck)
                .and()
            .field(this.newUser.getName())
                .required()
                .maxLength(20)
                .and()
            .field(this.newUser.getSurname())
                .required()
                .maxLength(50)
                .and()
            .field(this.newUser.getIsEnabled())
                .required()
                .and()
            .field(this.newUser.getUserPosition())
                .required()
                .and()
            .validate();
    }

    // Getters and setters --------------------------------------------------
    /**
     * @return el atributo newUser
     */
    public TGpeMUser getNewUser() {
        return this.newUser;
    }

    public void setNewUser(TGpeMUser newUser) {
        this.newUser = newUser;
    }

    /**
     * @return el atributo passwordCheck
     */
    public String getPasswordCheck() {
        return this.passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    /**
     * @return el atributo roles
     */
    public List<EnumRoles> getRoles() {
        return this.roles;
    }

    public void setRoles(List<EnumRoles> roles) {
        this.roles = roles;
    }

    public List<EnumUserPosition> getUserCategories() {
        return this.userCategories;
    }

    public void setUserCategories(List<EnumUserPosition> userCategories) {
        this.userCategories = userCategories;
    }

    /**
     * @return el atributo userRole
     */
    public EnumRoles getUserRole() {
        return this.userRole;
    }

    public void setUserRole(EnumRoles userRole) {
        this.userRole = userRole;
    }

    /**
     * @return el atributo userCategory
     */
    public EnumUserPosition getUserCategory() {
        return this.userCategory;
    }

    public void setUserCategory(EnumUserPosition userCategory) {
        this.userCategory = userCategory;
    }
}