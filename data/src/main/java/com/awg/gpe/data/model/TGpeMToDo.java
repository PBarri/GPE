package com.awg.gpe.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que modela la lista de tareas por hacer del usuario
 * <p>
 * Las tareas completadas se borran todas las noches en un proceso batch
 * </p>
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_TODOS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_TODO", allocationSize = 1)
public class TGpeMToDo extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMToDo.java
     * 
     * @since 1.0
     */
    public TGpeMToDo() {
    }

    // Attributes -------------------------------------------------------------

    @Column(nullable = false, length = 30)
    private String task;

    @Column(nullable = false)
    private Boolean done;

    // Relationships ----------------------------------------------------------

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private TGpeMUser user;

    // Getters and Setters ----------------------------------------------------

    /**
     * @return el atributo task
     */
    public String getTask() {
        return this.task;
    }

    /**
     * @return el atributo done
     */
    public Boolean getDone() {
        return this.done;
    }

    /**
     * @param done
     *            valor con el que se setea el campo done
     */
    public void setDone(Boolean done) {
        this.done = done;
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
     * @param task
     *            valor con el que se setea el campo task
     */
    public void setTask(String task) {
        this.task = task;
    }

}
