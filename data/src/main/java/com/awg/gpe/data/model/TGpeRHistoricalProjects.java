package com.awg.gpe.data.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.awg.gpe.data.enums.EnumRoles;

/**
 * Clase que modela la relación entre usuarios y los proyectos en los que han estado
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_R_HISTORICAL_PROJECTS",
    uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "PROJECT_ID", "ROLE_ID"}))
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_HISTORICAL_PROJECTS", allocationSize = 1)
public class TGpeRHistoricalProjects extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;
    
    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeRHistoricalProjects.java
     * @since 1.0
     */
    public TGpeRHistoricalProjects() {  }
    
    /**
     * Constructor de la clase TGpeRHistoricalProjects.java
     * @param user El usuario del histórico de proyectos
     * @param project El proyecto referido
     * @param role El rol que tuvo el usuario en dicho proyecto
     * @since 1.0
     */
    public TGpeRHistoricalProjects(TGpeMUser user, TGpeMProject project, TGpeMRole role, TGpeMRequirement requirement, LocalDate startDate) {
        this.user = user;
        this.project = project;
        this.role = role;
        this.requirement = requirement;
        this.startDate = startDate;
    }
    
    /**
     * Constructor de la clase TGpeRHistoricalProjects.java
     * @param user El usuario del histórico de proyectos
     * @param project El proyecto referido
     * @param role El rol que tuvo el usuario en el proyecto
     * @since 1.0
     */
    public TGpeRHistoricalProjects(TGpeMUser user, TGpeMProject project, EnumRoles role, TGpeMRequirement requirement, LocalDate startDate) {
        this.user = user;
        this.project = project;
        this.role = new TGpeMRole(role);
        this.requirement = requirement;
        this.startDate = startDate;
    }
    
    
    // Attributes -------------------------------------------------------------
    
    /**
     * Usuario que realizó alguna tarea en el proyecto
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private TGpeMUser user;
    
    /**
     * Proyecto asignado al usuario
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private TGpeMProject project;
    
    /**
     * Rol que desempeñó el usuario en el proyecto
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(referencedColumnName = "ID", nullable = false)
    private TGpeMRole role;
    
    @ManyToOne
    private TGpeMRequirement requirement;
    
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "END_DATE")
    private LocalDate endDate;
    

    // Getters and Setters ----------------------------------------------------
    
    public TGpeMUser getUser() {
        return this.user;
    }

    public void setUser(TGpeMUser user) {
        this.user = user;
    }

    public TGpeMProject getProject() {
        return this.project;
    }

    public void setProject(TGpeMProject project) {
        this.project = project;
    }

    public TGpeMRole getRole() {
        return this.role;
    }

    public void setRole(TGpeMRole role) {
        this.role = role;
    }    
    
}
