package com.awg.gpe.web.controllers.user;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.enums.EnumRoles;
import com.awg.gpe.data.enums.EnumUserPosition;
import com.awg.gpe.data.filters.TGpeMUserFilters;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.services.ServiceMUser;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.primefaces.GPELazyDataModel;
import com.awg.gpe.web.utils.GPELazyDataModelFilterBuilder;

/**
 * Controlador encargado de realizar la gestión de usuarios de la aplicación
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "userListController")
@ViewScoped
public class UserListController extends BaseController {
    
    private static final Logger log = Logger.getLogger(UserListController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_USER_LIST = "/views/users/userList.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMUser userService;

    // Attributes ---------------------------------------------------------
    /**
     * Atributo que contiene la lista de usuarios
     * 
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMUser> userList;
    private List<EnumRoles> roles;
    private List<EnumUserPosition> userPositions;

    // Filters ------------------------------------------------------------
    /**
     * Filtro del identificador
     * @version 1.0
     * @since 1.0
     */
    private String identifierFilter;
    /**
     * Filtro del nombre 
     * @version 1.0
     * @since 1.0
     */
    private String nameFilter;
    /**
     * Filtro del rol de usuario
     * @version 1.0
     * @since 1.0
     */
    private EnumRoles roleFilter;
    /**
     * Filtro de la categoría profesional
     * @version 1.0
     * @since 1.0
     */
    private EnumUserPosition userPositionFilter;
    /**
     * Filtro de la activación
     * @version 1.0
     * @since 1.0
     */
    private Boolean enabledFilter;
    /**
     * Filtro del correo electrónico
     * @version 1.0
     * @since 1.0
     */
    private String emailFilter;

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
        goToUserList();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que inserta la vista del listado de usuarios en la página
     * 
     * @version 1.0
     * @since 1.0
     */
    public void goToUserList() {
        goToView(UserListController.VIEW_USER_LIST);
        roles = Arrays.asList(EnumRoles.values());
        userPositions = Arrays.asList(EnumUserPosition.values());
        clear();
    }

    // Methods --------------------------------------------------------------
    /**
     * Método que limpia los filtros y la lista de usuarios de la página
     * 
     * @version 1.0
     * @since 1.0
     */
    private void clear() {
        this.userList = new GPELazyDataModel<>(this.userService);
        this.identifierFilter = null;
        this.nameFilter = null;
        this.roleFilter = null;
        this.userPositionFilter = null;
        this.enabledFilter = null;
        this.emailFilter = null;
        resetDatatable();
    }

    /**
     * Método encargado de ejecutar la búsqueda por los parámetros definidos por el usuario
     * @version 1.0
     * @since 1.0
     */
    public void search() {
        GPELazyDataModelFilterBuilder builder = new GPELazyDataModelFilterBuilder();
        Map<String, Object> filters = builder
            .add(TGpeMUserFilters.FILTER_IDENTIFIER, this.identifierFilter)
            .add(TGpeMUserFilters.FILTER_NAME, this.nameFilter)
            .add(TGpeMUserFilters.FILTER_EMAIL, this.emailFilter)
            .add(TGpeMUserFilters.FILTER_ACTIVE, this.enabledFilter)
            .add(TGpeMUserFilters.FILTER_ROLE, this.roleFilter)
            .add(TGpeMUserFilters.FILTER_USER_POSITION, this.userPositionFilter)
            .build();
        
        resetDatatable();

        this.userList = new GPELazyDataModel<>(this.userService, filters);
    }

    // Getters and setters --------------------------------------------------

    /**
     * @return el atributo userList
     */
    public GPELazyDataModel<TGpeMUser> getUserList() {
        return this.userList;
    }

    public void setUserList(GPELazyDataModel<TGpeMUser> userList) {
        this.userList = userList;
    }

    public ServiceMUser getUserService() {
        return this.userService;
    }

    public void setUserService(ServiceMUser userService) {
        this.userService = userService;
    }

    public List<EnumUserPosition> getUserPositions() {
        return this.userPositions;
    }

    public void setUserPositions(List<EnumUserPosition> userPositions) {
        this.userPositions = userPositions;
    }

    public List<EnumRoles> getRoles() {
        return this.roles;
    }

    public void setRoles(List<EnumRoles> roles) {
        this.roles = roles;
    }

    public String getIdentifierFilter() {
        return this.identifierFilter;
    }

    public void setIdentifierFilter(String identifierFilter) {
        this.identifierFilter = identifierFilter;
    }

    public String getNameFilter() {
        return this.nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public EnumRoles getRoleFilter() {
        return this.roleFilter;
    }

    public void setRoleFilter(EnumRoles roleFilter) {
        this.roleFilter = roleFilter;
    }

    public EnumUserPosition getUserPositionFilter() {
        return this.userPositionFilter;
    }

    public void setUserPositionFilter(EnumUserPosition userPositionFilter) {
        this.userPositionFilter = userPositionFilter;
    }

    public Boolean getEnabledFilter() {
        return this.enabledFilter;
    }

    public void setEnabledFilter(Boolean enabledFilter) {
        this.enabledFilter = enabledFilter;
    }

    public String getEmailFilter() {
        return this.emailFilter;
    }

    public void setEmailFilter(String emailFilter) {
        this.emailFilter = emailFilter;
    }
}
