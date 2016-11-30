package com.awg.gpe.data.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que modela la entidad de Horario, que se corresponde al horario que tenga un determinado usuario
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_TIMESHEETS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_TIMESHEETS", allocationSize = 1)
public class TGpeMTimesheet extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMSchedule.java
     * 
     * @since 1.0
     */
    public TGpeMTimesheet() {
    }

    // Attributes -------------------------------------------------------------
    /**
     * Fecha desde la que comienza a aplicar este horario
     * 
     * @since 1.0
     */
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    /**
     * Fecha hasta la que aplica este horario
     * 
     * @since 1.0
     */
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    /**
     * Número de horas de trabajo correspondientes al lunes
     * <p>
     * Deberá de ser un mínimo de 0 y un máximo de 10h
     * </p> 
     * @since 1.0
     */
    @Column(name = "MONDAY_HOURS", nullable = false)
    private Float mondayHours;

    /**
     * Número de horas de trabajo correspondientes al martes
     * <p>
     * Deberá de ser un mínimo de 0 y un máximo de 10h
     * </p>
     * @since 1.0
     */
    @Column(name = "TUESDAY_HOURS", nullable = false)
    private Float tuesdayHours;

    /**
     * Número de horas de trabajo correspondientes al miércoles
     * <p>
     * Deberá de ser un mínimo de 0 y un máximo de 10h
     * </p>
     * @since 1.0
     */
    @Column(name = "WEDNESDAY_HOURS", nullable = false)
    private Float wednesdayHours;

    /**
     * Número de horas de trabajo correspondientes al jueves
     * <p>
     * Deberá de ser un mínimo de 0 y un máximo de 10h
     * </p>
     * @since 1.0
     */
    @Column(name = "THURSDAY_HOURS", nullable = false)
    private Float thursdayHours;

    /**
     * Número de horas de trabajo correspondientes al viernes
     * <p>
     * Deberá de ser un mínimo de 0 y un máximo de 10h
     * </p>
     * @since 1.0
     */
    @Column(name = "FRIDAY_HOURS", nullable = false)
    private Float fridayHours;

    /**
     * Número de horas de trabajo correspondientes al sábado
     * <p>
     * Deberá de ser un mínimo de 0 y un máximo de 10h
     * </p>
     * @since 1.0
     */
    @Column(name = "SATURDAY_HOURS", nullable = false)
    private Float saturdayHours;

    /**
     * Número de horas de trabajo correspondientes al domingo
     * <p>
     * Deberá de ser un mínimo de 0 y un máximo de 10h
     * </p>
     * @since 1.0
     */
    @Column(name = "SUNDAY_HOURS", nullable = false)
    private Float sundayHours;

    // Relationships ----------------------------------------------------------

    /**
     * Usuario al que corresponde el calendario
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
     * @return el atributo mondayHours
     */
    public Float getMondayHours() {
        return this.mondayHours;
    }

    /**
     * @param mondayHours
     *            valor con el que se setea el campo mondayHours
     */
    public void setMondayHours(Float mondayHours) {
        this.mondayHours = mondayHours;
    }

    /**
     * @return el atributo tuesdayHours
     */
    public Float getTuesdayHours() {
        return this.tuesdayHours;
    }

    /**
     * @param tuesdayHours
     *            valor con el que se setea el campo tuesdayHours
     */
    public void setTuesdayHours(Float tuesdayHours) {
        this.tuesdayHours = tuesdayHours;
    }

    /**
     * @return el atributo wednesdayHours
     */
    public Float getWednesdayHours() {
        return this.wednesdayHours;
    }

    /**
     * @param wednesdayHours
     *            valor con el que se setea el campo wednesdayHours
     */
    public void setWednesdayHours(Float wednesdayHours) {
        this.wednesdayHours = wednesdayHours;
    }

    /**
     * @return el atributo thursdayHours
     */
    public Float getThursdayHours() {
        return this.thursdayHours;
    }

    /**
     * @param thursdayHours
     *            valor con el que se setea el campo thursdayHours
     */
    public void setThursdayHours(Float thursdayHours) {
        this.thursdayHours = thursdayHours;
    }

    /**
     * @return el atributo fridayHours
     */
    public Float getFridayHours() {
        return this.fridayHours;
    }

    /**
     * @param fridayHours
     *            valor con el que se setea el campo fridayHours
     */
    public void setFridayHours(Float fridayHours) {
        this.fridayHours = fridayHours;
    }

    /**
     * @return el atributo saturdayHours
     */
    public Float getSaturdayHours() {
        return this.saturdayHours;
    }

    /**
     * @param saturdayHours
     *            valor con el que se setea el campo saturdayHours
     */
    public void setSaturdayHours(Float saturdayHours) {
        this.saturdayHours = saturdayHours;
    }

    /**
     * @return el atributo sundayHours
     */
    public Float getSundayHours() {
        return this.sundayHours;
    }

    /**
     * @param sundayHours
     *            valor con el que se setea el campo sundayHours
     */
    public void setSundayHours(Float sundayHours) {
        this.sundayHours = sundayHours;
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
}
