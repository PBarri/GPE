package com.awg.gpe.web.controllers.user;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.services.ServiceMUser;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.controllers.HomeController;
import com.awg.gpe.web.primefaces.GPELazyDataModel;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "changePasswordController")
@ViewScoped
public class ChangePasswordController extends BaseController {
    
    private static final Logger log = Logger.getLogger(ChangePasswordController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_CHANGEPASSWORD_ADMIN = "/views/users/changePasswordAdmin.xhtml";
    private static final String VIEW_CHANGEPASSWORD = "/views/users/changePassword.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMUser userService;

    // Attributes ----------------------------------------------------------
    private GPELazyDataModel<TGpeMUser> userList;
    private TGpeMUser selectedUser;
    private String newPassword;
    private String confirmedPassword;

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
        goToChangePassword();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de cambiar contraseña.
     * <p>Este método comprueba el rol del usuario logado y lo envía, o bien a la 
     * página en la que se pueden cambiar todas las contraseñas (sólo en el caso de que el usuario sea
     * administrador), o a la pantalla para cambiar su propia contraseña.</p>
     * @version 1.0
     * @since 1.0
     */
    public void goToChangePassword() {
        HomeController home = getHomeController();
        if (home.getUser().isAdministrator()) {
            home.setActualPage(ChangePasswordController.VIEW_CHANGEPASSWORD_ADMIN);
        } else {
            home.setActualPage(ChangePasswordController.VIEW_CHANGEPASSWORD);
        }
        clear();
    }

    // Methods --------------------------------------------------------------
    private void clear() {
        this.userList = new GPELazyDataModel<>(this.userService);
        this.selectedUser = null;
        this.newPassword = null;
        this.confirmedPassword = null;
    }

    // Getters and setters --------------------------------------------------

    public GPELazyDataModel<TGpeMUser> getUserList() {
        return this.userList;
    }

    public void setUserList(GPELazyDataModel<TGpeMUser> userList) {
        this.userList = userList;
    }

    public TGpeMUser getSelectedUser() {
        return this.selectedUser;
    }

    public void setSelectedUser(TGpeMUser selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedPassword() {
        return this.confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

}