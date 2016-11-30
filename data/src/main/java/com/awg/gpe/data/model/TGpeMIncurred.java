package com.awg.gpe.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Clase que mapea las imputaciones de los usuarios sobre las distintas tareas.
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_INCURREDS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_INCURRED", allocationSize = 1)
public class TGpeMIncurred extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMIncurred.java
     * 
     * @since 1.0
     */
    public TGpeMIncurred() {
    }

    /**
     * Constructor de la clase TGpeMIncurred que acepta un usuario y una tarea como parametros
     * 
     * @param user - Usuario que ha incurrido
     * @param task - Tarea sobre la que se incurre
     * @since 1.0
     */
    public TGpeMIncurred(TGpeMUser user, TGpeMTask task) {
        // Insertamos el incurrido en los dos objetos
        user.addIncurred(this);
        task.addIncurred(this);
    }
    
    /**
     * Método que actualiza una imputación con el usuario y la tarea pasada por parámetros
     * 
     * @param user Usuario que realiza la imputación
     * @param task Tarea sobre la que se imputa
     * @version 1.0
     * @since 1.0
     */
    public void updateIncurred(TGpeMUser user, TGpeMTask task) {
    	// Insertamos el incurrido en los dos objetos
        user.addIncurred(this);
        task.addIncurred(this);
    }

    // Attributes -------------------------------------------------------------
    /**
     * Comentario del incurrido
     * 
     * @since 1.0
     */
    @Column(length = 200)
    private String commentary;

    /**
     * Minutos incurridos en la tarea
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private Integer minutes;

    /**
     * Minutos que le quedarían por incurrir al usuario.
     * <p>
     * Este campo se calcula a través del horario del usuario, observando las horas que tiene asignadas en el horario y restándole los minutos ya incurridos.
     * </p>
     * @since 1.0
     */
    @Transient
    private Integer minutesRemainig;

    /**
     * Marca que indica si el incurrido se hace en horario fuera del trabajo
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private Boolean extra;

    // Relationships ----------------------------------------------------------

    /**
     * Relación que hace referencia al usuario sobre el que se incurren las horas
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private TGpeMUser user;

    /**
     * Relación que hace referencia a la tarea sobre la que se incurren las horas
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "TASK_ID", referencedColumnName = "ID", nullable = false)
    private TGpeMTask task;

    // Getters and Setters ----------------------------------------------------

    /**
     * @return el atributo comment
     */
    public String getCommentary() {
        return this.commentary;
    }

    /**
     * @return el atributo minutes
     */
    public Integer getMinutes() {
        return this.minutes;
    }

    /**
     * @param minutes
     *            valor con el que se setea el campo minutes
     */
    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    /**
     * @return el atributo minutesRemainig
     */
    public Integer getMinutesRemainig() {
        return this.minutesRemainig;
    }

    /**
     * @param minutesRemainig
     *            valor con el que se setea el campo minutesRemainig
     */
    public void setMinutesRemainig(Integer minutesRemainig) {
        this.minutesRemainig = minutesRemainig;
    }

    /**
     * @return el atributo extra
     */
    public Boolean getExtra() {
        return this.extra;
    }

    /**
     * @param extra
     *            valor con el que se setea el campo extra
     */
    public void setExtra(Boolean extra) {
        this.extra = extra;
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
     * @return el atributo task
     */
    public TGpeMTask getTask() {
        return this.task;
    }

    /**
     * @param task
     *            valor con el que se setea el campo task
     */
    public void setTask(TGpeMTask task) {
        this.task = task;
    }

    /**
     * @param comment
     *            valor con el que se setea el campo comment
     */
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

}
