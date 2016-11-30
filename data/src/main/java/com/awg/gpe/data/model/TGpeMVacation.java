package com.awg.gpe.data.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que modela la entidad de vacaciones
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_VACATIONS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_VACATIONS", allocationSize = 1)
public class TGpeMVacation extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMVacation.java
     * 
     * @since 1.0
     */
    public TGpeMVacation() {
    }

    // Attributes -------------------------------------------------------------

    /**
     * Fecha en la que comienzan las vacaciones
     * 
     * @since 1.0
     */
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    /**
     * Fecha en la que terminan las vacaciones
     * 
     * @since 1.0
     */
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    /**
     * Año en el que se computan las vacaciones
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private Integer year;

    /**
     * Marca que indica que las vacaciones han sido aprobadas por un responsable
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private Boolean approved;
    
    /**
     * Marca que indica que las vacaciones han sido aprobadas o rechazadas por el responsable
     */
    @Column(nullable = false)
    private Boolean managed;

    // Relationships ----------------------------------------------------------

    /**
     * Usuario que tiene asignadas las vacaciones
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private TGpeMUser user;

    // Getters and Setters ----------------------------------------------------
    /**
     * @return el atributo startDate
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * @return el atributo endDate
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * @param endDate
     *            valor con el que se setea el campo endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return el atributo year
     */
    public Integer getYear() {
        return this.year;
    }

    /**
     * @param year
     *            valor con el que se setea el campo year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return el atributo approved
     */
    public Boolean getApproved() {
        return this.approved;
    }

    /**
     * @param approved
     *            valor con el que se setea el campo approved
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    /**
     * @return el atributo user
     */
    public TGpeMUser getUser() {
        return this.user;
    }

    /**
     * @param user
     *            valor con el que se setea el campo user
     */
    public void setUser(TGpeMUser user) {
        this.user = user;
    }

    /**
     * @param startDate
     *            valor con el que se setea el campo startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

	/**
	 * @return the managed
	 */
	public Boolean getManaged() {
		return this.managed;
	}

	/**
	 * @param managed the managed to set
	 */
	public void setManaged(Boolean managed) {
		this.managed = managed;
	}

}
