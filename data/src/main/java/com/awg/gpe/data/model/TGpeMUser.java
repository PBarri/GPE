package com.awg.gpe.data.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.awg.gpe.data.enums.EnumRoles;

/**
 * Clase que modela los usuarios de la aplicación
 * <p>
 * Esta clase implementa la interfaz de Spring Security {@link UserDetails}
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_USERS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_USERS", allocationSize = 1)
public class TGpeMUser extends BaseEntity implements UserDetails {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMUsuario
     */
    public TGpeMUser() {
        projectList = new ArrayList<>();
        projectsManaged = new ArrayList<>();
        historicalProjects = new ArrayList<>();
        timesheets = new ArrayList<>();
        tasksAssigned = new ArrayList<>();
        toDoList = new ArrayList<>();
        incurreds = new ArrayList<>();
        vacations = new ArrayList<>();
        absences = new ArrayList<>();
        requirementsDeveloped = new ArrayList<>();
    }

    // Attributes -------------------------------------------------------------

    /**
     * Identificador del usuario de la aplicación.
     * <p>
     * Será el código interno que tenga asociado en la empresa o el documento de identificación.
     * </p>
     * 
     * @since 1.0
     */
    @Column(nullable = false, unique = true, length = 20)
    private String identifier;

    /**
     * Contraseña del usuario para poder acceder a la aplicación.
     * <p>
     * La contraseña vendrá hasheada por @see {com.awg.gp.web.appConfig.SecurityConfig#encoder()}`
     * </p>
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private String password;

    /**
     * Nombre del usuario de la aplicación
     * 
     * @since 1.0
     */
    @Column(length = 20, nullable = false)
    private String name;

    /**
     * Apellido del usuario de la aplicación
     * 
     * @since 1.0
     */
    @Column(length = 50, nullable = false)
    private String surname;

    /**
     * Ciudad en la que nació el usuario
     * 
     * @since 1.0
     */
    @Column(length = 20)
    private String city;

    /**
     * Nacionalidad del usuario
     * 
     * @since 1.0
     */
    @Column(length = 20)
    private String country;

    /**
     * Fecha de nacimiento del usuario
     * 
     * @since 1.0
     */
    private LocalDate birthday;

    /**
     * Correo electrónico del usuario de la aplicación
     * 
     * @since 1.0
     */
    @Column(unique = true)
    private String email;

    /**
     * Teléfono del usuario de la aplicación
     * 
     * @since 1.0
     */
    private String phone;

    /**
     * URL en el que se encuentra la fotografía de perfil del usuario
     * 
     * @since 1.0
     */
    @Column(name = "PHOTO_URL")
    private String photoUrl;

    /**
     * Variable que almacenará las iniciales en el caso de que el usuario no tenga una fotografía de perfil
     * 
     * @since 1.0
     */
    @Transient
    private String avatar;

    /**
     * Indicador de si un usuario está activo o no en la aplicación.
     * <p>
     * En el caso de que no esté activo, el usuario no podrá acceder a la aplicación, mostrándosele un mensaje de que no existe el usuario.
     * </p>
     * 
     * @since 1.0
     */
    @Column(name = "IS_ENABLED", nullable = false)
    private Boolean isEnabled;

    /**
     * Indicador del bloqueo de un usuario
     * <p>
     * En el caso de que un usuario se encuentre bloqueado, no podrá acceder a la aplicación, mostrándosele un mensaje de que su usuario se encuentra bloqueado.
     * </p>
     * <p>
     * El usuario se bloqueará automáticamente en el caso de que se detecten más de 5 intentos de acceso fallidos, o en el caso de que llegue a la fecha de expiración.
     * </p>
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private Boolean locked;

    /**
     * Indicador del número de intentos de acceso de un usuario
     * <p>
     * Cuando este contador llegue a 5, se bloqueará el usuario automáticamente
     * </p>
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private Integer attempts;

    /**
     * Marca de tiempo que se actualiza cada vez que el usuario se conecta a la aplicación.
     * 
     * @since 1.0
     */
    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLoginDate;

    /**
     * Fecha de expiración del usuario.
     * <p>
     * Una vez pasada esta fecha se bloqueará la cuenta del usuario.
     * </p>
     * <p>
     * Por defecto se bloquea a los dos meses de la última conexión.
     * </p>
     * 
     * @since 1.0
     */
    @Column(name = "EXPIRING_DATE")
    private LocalDateTime expiringDate;
    
    /**
     * Número de días que le corresponden de vacaciones al usuario anualmente
     */
    @Column(name = "VACATION_DAYS")
    private Integer vacationDays;

    // Relationships ----------------------------------------------------------
    /**
     * Rol del usuario en la aplicación
     * <p>
     * Este rol determina las acciones que el usuario puede realizar en la aplicación y las vistas a las que tendrá acceso.
     * </p>
     * 
     * @see TGpeMRole
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(referencedColumnName = "ID", nullable = false)
    private TGpeMRole role;

    /**
     * Categoría profesional del usuario de la aplicación
     * 
     * @see TGpePUserCategory
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "USER_POSITION", referencedColumnName = "ID")
    private TGpePUserCategory userPosition;

    /**
     * Colección que recoge la cartera de proyectos de un jefe de proyecto.
     * <p>
     * Este campo solamente estará informado en el caso de que el usuario sea un jefe de proyecto.
     * </p>
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY)
    private Collection<TGpeMProject> projectList;

    /**
     * Relación que indica los proyectos en los que este usuario es el responsable.
     * <p>
     * Este campo solo se mostrará en el caso de que el usuario tenga el rol de responsable.
     * </p>
     * 
     * @since 1.0
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_GPE_R_PROJECTS_MANAGED", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_PROJECT"))
    private Collection<TGpeMProject> projectsManaged;

    /**
     * Relación que indica los requerimientos en los que este usuario es desarrollador.
     * <p>
     * Este campo solamente se informará en el caso de que el rol del usuario sea de desarrollador
     * </p>
     * 
     * @since 1.0
     */
    @ManyToMany
    @JoinTable(name = "T_GPE_R_REQUIREMENTS_DEVELOPED", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_REQUIREMENT"))
    private Collection<TGpeMRequirement> requirementsDeveloped;

    /**
     * Relación que indica los proyectos en los que este usuario ha colaborado
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<TGpeRHistoricalProjects> historicalProjects;

    /**
     * Relación que indica las tareas que tiene asignadas actualmente el usuario
     * 
     * @since 1.0
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_GPE_R_TASKS_ASSIGNED", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_TASK"))
    private Collection<TGpeMTask> tasksAssigned;

    /**
     * Colección que recoge la lista de tareas a realizar por el usuario
     * <p>
     * Esta lista no se corresponde con las tareas asignadas por los distintos responsables, sino a una lista creada por el propio usuario a modo de recordatorio
     * </p>
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<TGpeMToDo> toDoList;

    /**
     * Colección que recoge los horarios que tenga asignado ese usuario. Los distintos horarios no deberán solaparse
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Collection<TGpeMTimesheet> timesheets;

    /**
     * Colección que recoge las imputaciones del usuario
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<TGpeMIncurred> incurreds;

    /**
     * Vacaciones asociadas a un usuario
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<TGpeMVacation> vacations;

    /**
     * Ausencias asociadas a un usuario
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<TGpeMAbsence> absences;

    // UserDetails implementation ---------------------------------------------

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(this.role);
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    @Transient
    public String getUsername() {
        return this.identifier;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return this.expiringDate.isAfter(LocalDateTime.now());
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    @Transient
    public boolean isEnabled() {
        return this.isEnabled;
    }

    // Helper methods ---------------------------------------------------------

    /**
     * Añadir un proyecto a la lista de proyectos de los que el usuario es responsable
     * <p>
     * Este método solamente puede ejecutarse sobre usuarios que tengan el rol de responsable.
     * </p>
     * 
     * @param project
     *            El proyecto que se quiere añadir
     * @version 1.0
     * @since 1.0
     */
    public void addManagedProject(TGpeMProject project) {
        Assert.isTrue(getRole().equals(EnumRoles.PROJECT_MANAGER));
        addManagedProject(project, true);
    }
    
    protected void addManagedProject(TGpeMProject project, boolean follow) {
        if (!projectsManaged.contains(project)) {
            projectsManaged.add(project);
	        if (follow) {
	            project.addManager(this, false);
	        }
        }
    }

    /**
     * Elimina un proyecto de la lista de proyectos de los que el usuario es responsable
     * <p>
     * Este método solamente puede ejecutarse sobre usuarios que tengan el rol de responsable
     * </p>
     * 
     * @param project
     *            El proyecto que se quiere quitar
     * @version 1.0
     * @since 1.0
     */
    public void removeManagedProject(TGpeMProject project) {
        Assert.isTrue(getRole().equals(EnumRoles.PROJECT_MANAGER));
        removeManagedProject(project, true);
    }
    
    protected void removeManagedProject(TGpeMProject project, boolean follow) {
        projectsManaged.remove(project);

        if (follow) {
            project.removeManager(this, false);
        }
    }

    /**
     * Añade un proyecto a la lista de proyectos que el usuario tiene en cartera.
     * <p>
     * Este método solo se ejecutará sobre usuarios que tengan el rol de jefe de proyecto
     * </p>
     * 
     * @param project
     *            El proyecto que quiere añadir
     * @version 1.0
     * @since 1.0
     */
    public void addProjectToProjectList(TGpeMProject project) {
        Assert.isTrue(getRole().equals(EnumRoles.PROJECT_LEADER));
        if (!projectList.contains(project)) {
            projectList.add(project);
	        project.setLeader(this);
        }
    }

    /**
     * Elimina un proyecto de la lista de proyectos que el usuario tiene en cartera
     * <p>
     * Este método solo se ejecutará sobre usuarios que tengan el rol de jefe de proyecto
     * </p>
     * 
     * @param project
     *            Proyecto a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeProjectFromProjectList(TGpeMProject project) {
        Assert.isTrue(getRole().equals(EnumRoles.PROJECT_LEADER));
        projectList.remove(project);
        project.setLeader(null);
    }

    public void addHistoricalProject(TGpeRHistoricalProjects hist) {
        hist.setUser(this);
        historicalProjects.add(hist);
    }

    public void addHistoricalProject(TGpeMProject project) {
        TGpeRHistoricalProjects hist = new TGpeRHistoricalProjects(this, project, getRole(), null, LocalDate.now());
        historicalProjects.add(hist);
    }

    public TGpeRHistoricalProjects addHistoricalProject(TGpeMProject project, TGpeMRequirement requirement) {
        TGpeRHistoricalProjects hist = new TGpeRHistoricalProjects(this, project, getRole(), requirement, LocalDate.now());
        historicalProjects.add(hist);
        return hist;
    }

    public void removeHistoricalProject(TGpeRHistoricalProjects hist) {
        historicalProjects.remove(hist);
    }

    /**
     * Método que añade un nuevo requerimiento en desarrollo al usuario
     * 
     * @param requirement
     *            El requerimiento a desarrollar
     * @version 1.0
     * @since 1.0
     */
    public void addRequirementDeveloped(TGpeMRequirement requirement) {
        addRequirementDeveloped(requirement, true);
    }
    
    protected void addRequirementDeveloped(TGpeMRequirement requirement, boolean follow) {
        if (!requirementsDeveloped.contains(requirement)) {
            requirementsDeveloped.add(requirement);
	
	        if (follow) {
	            requirement.addDeveloper(this, false);
	        }
        }
    }

    /**
     * Elimina un requerimiento de los requirimientos que actualmente está desarrollando el usuario
     * 
     * @param requirement
     *            El requerimiento a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeRequirementDeveloped(TGpeMRequirement requirement) {
        removeRequirementDeveloped(requirement, true);
    }
    
    protected void removeRequirementDeveloped(TGpeMRequirement requirement, boolean follow) {
        requirementsDeveloped.remove(requirement);

        if (follow) {
            requirement.removeDeveloper(this, false);
        }
    }

    /**
     * Método que añade una tarea a la lista de cosas por hacer
     * 
     * @param task
     *            ToDo que se quiere añadir
     * @version 1.0
     * @since 1.0
     */
    public void addTask(TGpeMToDo task) {
        toDoList.add(task);
        task.setUser(this);
    }

    /**
     * Método que elimina una tarea de la lista de cosas por hacer
     * 
     * @param task
     *            ToDo que se quiere eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeTask(TGpeMToDo task) {
        toDoList.remove(task);
        task.setUser(null);
    }

    /**
     * Método que añade un nuevo horario al usuario
     * 
     * @param timesheet
     *            Horario que se quiere añadir
     * @version 1.0
     * @since 1.0
     */
    public void addTimesheet(TGpeMTimesheet timesheet) {
        timesheets.add(timesheet);
        timesheet.setUser(this);
    }

    /**
     * Método que elimina un horario del usuario
     * 
     * @param timesheet
     *            Horario que se quiere eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeTimesheet(TGpeMTimesheet timesheet) {
        timesheets.remove(timesheet);
        timesheet.setUser(null);
    }

    /**
     * Método que añade un incurrido al usuario
     * 
     * @param incurred
     *            Incurrido que se quiere añadir
     * @version 1.0
     * @since 1.0
     */
    public void addIncurred(TGpeMIncurred incurred) {
        incurred.setUser(this);
        incurreds.add(incurred);
    }

    /**
     * Método que elimina un incurrido del usuario
     * 
     * @param incurred
     *            Incurrido que se quiere eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeIncurred(TGpeMIncurred incurred) {
        incurreds.remove(incurred);
    }

    /**
     * Método que añade un periodo vacacional al usuario
     * 
     * @param vacation
     *            Vacaciones que se quieren añadir
     * @version 1.0
     * @since 1.0
     */
    public void addVacation(TGpeMVacation vacation) {
        vacations.add(vacation);
        vacation.setUser(this);
    }

    /**
     * Método que elimina un periodo vacacional al usuario
     * 
     * @param vacation
     *            Vacaciones que se quieren eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeVacation(TGpeMVacation vacation) {
        vacations.remove(vacation);
        vacation.setUser(null);
    }

    /**
     * Método que añade una ausencia al usuario
     * 
     * @param absence
     *            Ausencia que se quiere añadir
     * @version 1.0
     * @since 1.0
     */
    public void addAbsence(TGpeMAbsence absence) {
        absences.add(absence);
        absence.setUser(this);
    }

    /**
     * Método que elimina una ausencia del usuario
     * 
     * @param absence
     *            Ausencia que se quiere eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeAbsence(TGpeMAbsence absence) {
        absences.remove(absence);
        absence.setUser(null);
    }

    /**
     * Método que añade una tarea asignada al usuario
     * 
     * @param task
     *            Tarea que se quiere asignar al usuario
     * @version 1.0
     * @since 1.0
     */
    public void addAssignedTask(TGpeMTask task) {
        addAssignedTask(task, true);
    }

    protected void addAssignedTask(TGpeMTask task, boolean follow) {
        if (!tasksAssigned.contains(task)) {
            tasksAssigned.add(task);
	
	        if (follow) {
	            task.addAssignedUser(this, false);
	        }
        }
    }

    /**
     * Método que elimina una tarea asignada del usuario
     * 
     * @param task
     *            Tarea que se quiere desasignar al usuario
     * @version 1.0
     * @since 1.0
     */
    public void removeAssignedTask(TGpeMTask task) {
        removeAssignedTask(task, true);
    }

    protected void removeAssignedTask(TGpeMTask task, boolean follow) {
        tasksAssigned.remove(task);

        if (follow) {
            task.removeAssignedUser(this, false);
        }
    }

    /**
     * Método auxiliar que ayuda a ver si el usuario es administrador o no
     * 
     * @return true si el usuario es administrador
     * @version 1.0
     * @since 1.0
     */
    public Boolean isAdministrator() {
        return role.equals(EnumRoles.ADMINISTRATOR);
    }

    /**
     * Método auxiliar que ayuda a ver si el usuario es responsable de proyecto o no
     * 
     * @return true si el usuario es responsable de proyecto
     * @version 1.0
     * @since 1.0
     */
    public Boolean isProjectManager() {
        return role.equals(EnumRoles.PROJECT_MANAGER);
    }

    /**
     * Método auxiliar que ayuda a ver si el usuario es jefe de proyecto o no
     * 
     * @return true si el usuario es jefe de proyecto
     * @version 1.0
     * @since 1.0
     */
    public Boolean isProjectLeader() {
        return role.equals(EnumRoles.PROJECT_LEADER);
    }

    /**
     * Método auxiliar que ayuda a ver si el usuario es desarrollador o no
     * 
     * @return true si el usuario es desarrollador
     * @version 1.0
     * @since 1.0
     */
    public Boolean isDeveloper() {
        return role.equals(EnumRoles.USER);
    }

    // Getters and Setters ----------------------------------------------------

    /**
     * @return el atributo identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * @return el atributo name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            valor con el que se setea el campo name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return el atributo surname
     */
    public String getSurname() {
        return this.surname;
    }

    /**
     * @param surname
     *            valor con el que se setea el campo surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return el atributo email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email
     *            valor con el que se setea el campo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return el atributo phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * @param phone
     *            valor con el que se setea el campo phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return el atributo photoUrl
     */
    public String getPhotoUrl() {
        return this.photoUrl;
    }

    /**
     * @param photoUrl
     *            valor con el que se setea el campo photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAvatar() {
        return "" + this.name.charAt(0) + this.surname.charAt(0);
    }

    /**
     * @return el atributo isEnabled
     */
    public Boolean getIsEnabled() {
        return this.isEnabled;
    }

    /**
     * @param isEnabled
     *            valor con el que se setea el campo isEnabled
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return el atributo locked
     */
    public Boolean getLocked() {
        return this.locked;
    }

    /**
     * @param locked
     *            valor con el que se setea el campo locked
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * @return el atributo attempts
     */
    public Integer getAttempts() {
        return this.attempts;
    }

    /**
     * @param attempts
     *            valor con el que se setea el campo attempts
     */
    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    /**
     * @return el atributo lastLoginDate
     */
    public LocalDateTime getLastLoginDate() {
        return this.lastLoginDate;
    }

    /**
     * @param lastLoginDate
     *            valor con el que se setea el campo lastLoginDate
     */
    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * @return el atributo expiringDate
     */
    public LocalDateTime getExpiringDate() {
        return this.expiringDate;
    }

    /**
     * @param expiringDate
     *            valor con el que se setea el campo expiringDate
     */
    public void setExpiringDate(LocalDateTime expiringDate) {
        this.expiringDate = expiringDate;
    }

    public Integer getVacationDays() {
		return this.vacationDays;
	}

	public void setVacationDays(Integer vacationDays) {
		this.vacationDays = vacationDays;
	}

	/**
     * @return el atributo role
     */
    public TGpeMRole getRole() {
        return this.role;
    }

    /**
     * @param role
     *            valor con el que se setea el campo role
     */
    public void setRole(TGpeMRole role) {
        this.role = role;
    }

    /**
     * @return el atributo userPosition
     */
    public TGpePUserCategory getUserPosition() {
        return this.userPosition;
    }

    /**
     * @param userPosition
     *            valor con el que se setea el campo userPosition
     */
    public void setUserPosition(TGpePUserCategory userPosition) {
        this.userPosition = userPosition;
    }

    /**
     * @return el atributo projectList
     */
    public Collection<TGpeMProject> getProjectList() {
        return this.projectList;
    }

    /**
     * @param projectList
     *            valor con el que se setea el campo projectList
     */
    public void setProjectList(Collection<TGpeMProject> projectList) {
        this.projectList = projectList;
    }

    /**
     * @return el atributo projectsManaged
     */
    public Collection<TGpeMProject> getProjectsManaged() {
        return this.projectsManaged;
    }

    /**
     * @param projectsManaged
     *            valor con el que se setea el campo projectsManaged
     */
    public void setProjectsManaged(Collection<TGpeMProject> projectsManaged) {
        this.projectsManaged = projectsManaged;
    }

    /**
     * @return el atributo toDoList
     */
    public Collection<TGpeMToDo> getToDoList() {
        return this.toDoList;
    }

    /**
     * @param toDoList
     *            valor con el que se setea el campo toDoList
     */
    public void setToDoList(Collection<TGpeMToDo> toDoList) {
        this.toDoList = toDoList;
    }

    /**
     * @return el atributo schedules
     */
    public Collection<TGpeMTimesheet> getTimesheets() {
        return this.timesheets;
    }

    /**
     * @param timesheets
     *            valor con el que se setea el campo schedules
     */
    public void setTimesheets(Collection<TGpeMTimesheet> timesheets) {
        this.timesheets = timesheets;
    }

    /**
     * @return el atributo incurreds
     */
    public Collection<TGpeMIncurred> getIncurreds() {
        return this.incurreds;
    }

    /**
     * @param incurreds
     *            valor con el que se setea el campo incurreds
     */
    public void setIncurreds(Collection<TGpeMIncurred> incurreds) {
        this.incurreds = incurreds;
    }

    /**
     * @return el atributo vacations
     */
    public Collection<TGpeMVacation> getVacations() {
        return this.vacations;
    }

    /**
     * @param vacations
     *            valor con el que se setea el campo vacations
     */
    public void setVacations(Collection<TGpeMVacation> vacations) {
        this.vacations = vacations;
    }

    /**
     * @return el atributo absences
     */
    public Collection<TGpeMAbsence> getAbsences() {
        return this.absences;
    }

    /**
     * @param absences
     *            valor con el que se setea el campo absences
     */
    public void setAbsences(Collection<TGpeMAbsence> absences) {
        this.absences = absences;
    }

    /**
     * @param identifier
     *            valor con el que se setea el campo identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @param password
     *            valor con el que se setea el campo password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return el atributo city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * @param city
     *            valor con el que se setea el campo city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return el atributo country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * @param country
     *            valor con el que se setea el campo country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return el atributo birthday
     */
    public LocalDate getBirthday() {
        return this.birthday;
    }

    /**
     * @param birthday
     *            valor con el que se setea el campo birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Collection<TGpeMTask> getTasksAssigned() {
        return this.tasksAssigned;
    }

    public void setTasksAssigned(Collection<TGpeMTask> tasksAssigned) {
        this.tasksAssigned = tasksAssigned;
    }

    public Collection<TGpeRHistoricalProjects> getHistoricalProjects() {
        return this.historicalProjects;
    }

    public void setHistoricalProjects(Collection<TGpeRHistoricalProjects> historicalProjects) {
        this.historicalProjects = historicalProjects;
    }

    public Collection<TGpeMRequirement> getRequirementsDeveloped() {
        return this.requirementsDeveloped;
    }

    public void setRequirementsDeveloped(Collection<TGpeMRequirement> requirementsDeveloped) {
        this.requirementsDeveloped = requirementsDeveloped;
    }

}
