package com.awg.gpe.data.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase base que mapea la tabla T_GPE_M_ABSENCES, conteniendo las posibles ausencias
 * de los usuarios.
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_ABSENCES")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_ABSENCES", allocationSize = 1)
public class TGpeMAbsence extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMAbsence.java
     * 
     * @since 1.0
     */
    public TGpeMAbsence() {
        
    }

    // Attributes -------------------------------------------------------------

    /**
     * Fecha y hora en la que comienza la ausencia
     * 
     * @since 1.0
     */
    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    /**
     * Fecha y hora en la que se termina la ausencia
     * 
     * @since 1.0
     */
    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    /**
     * Comentario acerca de la ausencia
     * 
     * @since 1.0
     */
    @Column(length = 100)
    private String commentary;

    // Relationships ----------------------------------------------------------

    /**
     * Usuario que tiene la ausencia
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
    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    /**
     * @return el atributo endDate
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    /**
     * @param endDate
     *            valor con el que se setea el campo endDate
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * @return el atributo comment
     */
    public String getCommentary() {
        return this.commentary;
    }

    /**
     * @param commentary
     *            valor con el que se setea el campo comment
     */
    public void setCommentary(String commentary) {
        this.commentary = commentary;
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
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

}
