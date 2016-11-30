package com.awg.gpe.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que modela los comentarios de las tareas
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_TASK_COMMENT")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_TASKS_COMMENT", allocationSize = 1)
public class TGpeMTaskComment extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;
    
    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMTaskComment.java
     * @since 1.0
     */
    public TGpeMTaskComment() {  }
    
    // Attributes -------------------------------------------------------------
    
    /**
     * Comentario realizado
     * 
     * @since 1.0
     */
    @Column(nullable = false, length = 500)
    private String commentary;
    
    // Relationships ----------------------------------------------------------
    
    /**
     * Usuario que ha creado el comentario
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private TGpeMUser user;
    
    /**
     * Tarea sobre la que se crea el comentario
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private TGpeMTask task;

    // Getters and Setters ----------------------------------------------------
    
    /**
     * @return el atributo commentary
     */
    public String getCommentary() {
        return this.commentary;
    }

    /**
     * @param commentary valor con el que se setea el campo commentary
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
     * @param user valor con el que se setea el campo user
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
     * @param task valor con el que se setea el campo task
     */
    public void setTask(TGpeMTask task) {
        this.task = task;
    }    
}
