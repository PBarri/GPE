package com.awg.gpe.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumPriority;

/**
 * Clase que modela los objetivos de un determinado requerimiento
 * <p>
 * Estos objetivos son meramente informativos y con la intención de poner en perspectiva el objetivo del requirimiento que estamos desarrollando
 * </p>
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_GOALS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_GOALS", allocationSize = 1)
public class TGpeMGoal extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMGoal
     * 
     * @since 1.0
     */
    public TGpeMGoal() {
    }

    // Attributes -------------------------------------------------------------

    /**
     * Título del objetivo.
     * 
     * @since 1.0
     */
    @Column(nullable = false, length = 30)
    private String title;

    /**
     * Descripción del objetivo
     * 
     * @since 1.0
     */
    @Column(length = 200)
    private String description;

    /**
     * Prioridad de consecución del objetivo
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private EnumPriority priority;

    /**
     * Marca que indica si el objetivo se ha completado
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private Boolean achieved;

    // Relationships ----------------------------------------------------------

    /**
     * Requerimiento al que está asociado el objetivo
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "REQUIREMENT_ID", referencedColumnName = "ID", nullable = false)
    private TGpeMRequirement requirement;

    // Getters and Setters ----------------------------------------------------

    /**
     * @return el atributo title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return el atributo description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *            valor con el que se setea el campo description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return el atributo priority
     */
    public EnumPriority getPriority() {
        return this.priority;
    }

    /**
     * @param priority
     *            valor con el que se setea el campo priority
     */
    public void setPriority(EnumPriority priority) {
        this.priority = priority;
    }

    /**
     * @return el atributo achieved
     */
    public Boolean getAchieved() {
        return this.achieved;
    }

    /**
     * @param achieved
     *            valor con el que se setea el campo achieved
     */
    public void setAchieved(Boolean achieved) {
        this.achieved = achieved;
    }

    /**
     * @return el atributo requirement
     */
    public TGpeMRequirement getRequirement() {
        return this.requirement;
    }

    /**
     * @param requirement
     *            valor con el que se setea el campo requirement
     */
    public void setRequirement(TGpeMRequirement requirement) {
        this.requirement = requirement;
    }

    /**
     * @param title
     *            valor con el que se setea el campo title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
