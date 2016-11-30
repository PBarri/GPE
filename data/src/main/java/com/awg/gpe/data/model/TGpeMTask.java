package com.awg.gpe.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.awg.gpe.data.enums.EnumTaskStatus;

/**
 * Clase que modela la entidad de Tareas
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_TASKS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_TASKS", allocationSize = 1)
public class TGpeMTask extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMTask.java
     * 
     * @since 1.0
     */
    public TGpeMTask() {
        childs = new ArrayList<>();
        incurreds = new ArrayList<>();
        comments = new ArrayList<>();
        usersAssigned = new ArrayList<>();
        dependencies = new ArrayList<>();
    }

    // Attributes -------------------------------------------------------------

    /**
     * Código de la tarea
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private String code;

    /**
     * Nombre de la tarea
     */
    @Column(length = 50)
    private String name;

    /**
     * Descripción de la tarea
     * 
     * @since 1.0
     */
    @Column(length = 200)
    private String description;
    
    /**
     * Indicador de si es una tarea de gestión
     * <p>
     * Este campo es obligatorio para las tareas de un proyecto de metrica v3
     * </p>
     * <p>
     * Si una tarea es de gestión, solamente podrán ser asignados a ella perfiles de jefe de proyecto o responsables de proyecto.
     * En el caso de que no sea de gestión, podrán asignarse a ella responsables de proyecto o desarrolladores.
     * </p>
     * @since 1.0
     */
    private Boolean management;

    /**
     * Número de horas en las que está estimada la tarea
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private Float hours;

    /**
     * Fecha y hora en la que se inicia la tarea
     * 
     * @since 1.0
     */
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    /**
     * Fecha y hora en la que la tarea se marca como finalizada
     * 
     * @since 1.0
     */
    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    /**
     * Número de horas que han sido incurridas en esta tarea
     * 
     * @since 1.0
     */
    @Formula(value = "NVL((select NVL(sum(i.minutes) / 60, 0) from T_GPE_M_INCURREDS i where i.TASK_ID = ID), 0)")
    private Float incurredHours;

    /**
     * Porcentaje de cumplimiento de la tarea
     * <p>
     * Este porcentaje es calculado a partir del número de horas estimado de la tarea y de las horas que ya han sido incurridas
     * </p>
     * @since 1.0
     */
    @Transient
    private Float percentage;

    // Relationships ----------------------------------------------------------

    /**
     * Relación con el requerimiento de la tarea
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "REQUIREMENT_ID", referencedColumnName = "ID")
    private TGpeMRequirement requirement;

    /**
     * Marca que informa del identificador del usuario que creó el proyecto
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "ID", nullable = false)
    private TGpeMUser createdBy;

    /**
     * Marca que informa del último usuario que modifica la información del proyecto.
     * <p>
     * Esta marca se informa con el identificador del usuario
     * </p>
     */
    @OneToOne
    @JoinColumn(name = "LAST_EDITION_BY", referencedColumnName = "ID", nullable = false)
    private TGpeMUser lastEditionBy;

    /**
     * Relación con la tabla de tipos de tarea.
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "TASK_TYPE_ID", referencedColumnName = "ID")
    private TGpePTaskType taskType;

    /**
     * Relación con el estado de la tarea
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "TASK_STATUS_ID", referencedColumnName = "ID")
    private TGpePTaskStatus taskStatus;

    /**
     * Relación con la prioridad de la tarea
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "TASK_PRIORITY_ID", referencedColumnName = "ID")
    private TGpePPriority taskPriority;

    /**
     * Atributo que relaciona a una tarea con su tarea padre
     * 
     * @since 1.0
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    private TGpeMTask parent;

    /**
     * Atributo que relaciona a una tarea con sus tareas hijas
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<TGpeMTask> childs;
    
    /**
     * Atributo que indica las tareas de las que esta tarea es dependiente.
     * Esto quiere decir que hasta que no se marquen como finalizadas las tareas contenidas en esta lista
     * no se podrá iniciar la ejecución de esta tarea.
     * <p>
     * A la hora de almacenarlo en la base de datos, el campo <code>DEPENDENCY_ID</code> será 
     * el id de la tarea que la bloquea.
     * </p>
     * 
     * @since 1.0
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_GPE_R_TASKS_DEPENDENCIES", 
        joinColumns = {@JoinColumn(name = "TASK_ID")}, 
        inverseJoinColumns = {@JoinColumn(name = "DEPENDENCY_ID")})
    private Collection<TGpeMTask> dependencies;

    /**
     * Colección de incurridos sobre la tarea
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private Collection<TGpeMIncurred> incurreds;

    /**
     * Colección de usuarios asignados a la tarea
     * 
     * @since 1.0
     */
    @ManyToMany(mappedBy = "tasksAssigned")
    private Collection<TGpeMUser> usersAssigned;
    
    
    /**
     * Colección de los comentarios realizados sobre esta tarea
     * @since 1.0
     */
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<TGpeMTaskComment> comments;

    // Helper methods ---------------------------------------------------------

    /**
     * Método que añade una tarea hija a la tarea
     * 
     * @param task La tarea hija que se quiere añadir
     * @version 1.0
     * @since 1.0
     */
    public void addChild(TGpeMTask task) {
        if (!childs.contains(task)) {
            childs.add(task);
	        task.setParent(this);
        }
    }

    /**
     * Método que elimina una tarea hija
     * 
     * @param task La tarea hija que se quiere eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeChild(TGpeMTask task) {
        childs.remove(task);
        task.setParent(null);
    }

    /**
     * Método que añade un incurrido a la tarea
     * 
     * @param incurred - El incurrido que se quiere añadir
     * @version 1.0
     * @since 1.0
     */
    public void addIncurred(TGpeMIncurred incurred) {
    	incurred.setTask(this);
        incurreds.add(incurred);
    }

    /**
     * Método que elimina un incurrido de la tarea
     * 
     * @param incurred El incurrido que se quiere eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeIncurred(TGpeMIncurred incurred) {
    	incurred.setTask(null);
        incurreds.remove(incurred);
    }

    /**
     * Método que añade a un usuario asignado a la tarea
     * 
     * @param user El usuario que se quiere asignar
     * @version 1.0
     * @since 1.0
     */
    public void addAssignedUser(TGpeMUser user) {
        addAssignedUser(user, true);
    }
    
    protected void addAssignedUser(TGpeMUser user, boolean follow) {
        if (!usersAssigned.contains(user)) {
            usersAssigned.add(user);
            if (follow) {
                user.addAssignedTask(this, false);
            }
        }    	
    }

    /**
     * Método que elimina un usuario asignado de la tarea
     * 
     * @param user El usuario que se quiere que deje de estar asignado a la tarea 
     * @version 1.0
     * @since 1.0
     */
    public void removeAssignedUser(TGpeMUser user) {
        removeAssignedUser(user, true);
    }
    
    protected void removeAssignedUser(TGpeMUser user, boolean follow) {
        usersAssigned.remove(user);
        
        if (follow) {
            user.removeAssignedTask(this, false);
        }
    }
    
    /**
     * Método que añade un nuevo comentario a la tarea
     * @param comment Comentario a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addComment(TGpeMTaskComment comment) {
        comment.setTask(this);
        comments.add(comment);
    }
    
    /**
     * Método que elimina un comentario de la tarea
     * @param comment Comentario a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeComment(TGpeMTaskComment comment) {
        comment.setTask(null);
        comments.remove(comment);
    }
    
    /**
     * Método que añade una nueva dependencia a la tarea
     * @param task Tarea de la que depende
     * @version 1.0
     * @since 1.0
     */
    public void addDependency(TGpeMTask task) {
        if (!dependencies.contains(task)) {
            dependencies.add(task);
        }
    }
    
    /**
     * Método que elimina una dependencia de la tarea
     * @param task Tarea que se va a eliminar de las dependencias
     * @version 1.0
     * @since 1.0
     */
    public void removeDependency(TGpeMTask task) {
        dependencies.remove(task);
    }
    
    // Getters and Setters ----------------------------------------------------
    /**
     * @return el atributo code
     */
    public String getCode() {
        return this.code;
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
     * @return el atributo hours
     */
    public Float getHours() {
        return this.hours;
    }

    /**
     * @param hours
     *            valor con el que se setea el campo hours
     */
    public void setHours(Float hours) {
        this.hours = hours;
    }

    /**
     * @return el atributo startDate
     */
    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    /**
     * @param startDate
     *            valor con el que se setea el campo startDate
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
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
     * @return el atributo incurredHours
     */
    public Float getIncurredHours() {
        return this.incurredHours;
    }

    /**
     * @param incurredHours
     *            valor con el que se setea el campo incurredHours
     */
    public void setIncurredHours(Float incurredHours) {
        this.incurredHours = incurredHours;
    }

    /**
     * @return el atributo percentage
     */
    public Float getPercentage() {
        return (this.incurredHours / this.hours) * 100;
    }

    /**
     * @param percentage
     *            valor con el que se setea el campo percentage
     */
    public void setPercentage(Float percentage) {
        this.percentage = percentage;
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
     * @return el atributo createdBy
     */
    public TGpeMUser getCreatedBy() {
        return this.createdBy;
    }

    /**
     * @param createdBy
     *            valor con el que se setea el campo createdBy
     */
    public void setCreatedBy(TGpeMUser createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return el atributo lastEditionBy
     */
    public TGpeMUser getLastEditionBy() {
        return this.lastEditionBy;
    }

    /**
     * @param lastEditionBy
     *            valor con el que se setea el campo lastEditionBy
     */
    public void setLastEditionBy(TGpeMUser lastEditionBy) {
        this.lastEditionBy = lastEditionBy;
    }

    /**
     * @return el atributo taskType
     */
    public TGpePTaskType getTaskType() {
        return this.taskType;
    }

    /**
     * @param taskType
     *            valor con el que se setea el campo taskType
     */
    public void setTaskType(TGpePTaskType taskType) {
        this.taskType = taskType;
    }

    /**
     * @return el atributo taskStatus
     */
    public TGpePTaskStatus getTaskStatus() {
        return this.taskStatus;
    }

    /**
     * @param taskStatus
     *            valor con el que se setea el campo taskStatus
     */
    public void setTaskStatus(TGpePTaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    public void setTaskStatus(EnumTaskStatus taskStatus) {
        this.taskStatus = new TGpePTaskStatus(taskStatus);
    }

    /**
     * @return el atributo taskPriority
     */
    public TGpePPriority getTaskPriority() {
        return this.taskPriority;
    }

    /**
     * @param taskPriority
     *            valor con el que se setea el campo taskPriority
     */
    public void setTaskPriority(TGpePPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    /**
     * @return el atributo parent
     */
    public TGpeMTask getParent() {
        return this.parent;
    }

    /**
     * @param parent
     *            valor con el que se setea el campo parent
     */
    public void setParent(TGpeMTask parent) {
        this.parent = parent;
    }

    /**
     * @return el atributo childs
     */
    public Collection<TGpeMTask> getChilds() {
        return this.childs;
    }

    /**
     * @param childs
     *            valor con el que se setea el campo childs
     */
    public void setChilds(Collection<TGpeMTask> childs) {
        this.childs = childs;
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
     * @param code
     *            valor con el que se setea el campo code
     */
    public void setCode(String code) {
        this.code = code;
    }

    public Collection<TGpeMUser> getUsersAssigned() {
        return this.usersAssigned;
    }

    public void setUsersAssigned(Collection<TGpeMUser> usersAssigned) {
        this.usersAssigned = usersAssigned;
    }

    /**
     * @return el atributo comments
     */
    public Collection<TGpeMTaskComment> getComments() {
        return this.comments;
    }

    /**
     * @param comments valor con el que se setea el campo comments
     */
    public void setComments(Collection<TGpeMTaskComment> comments) {
        this.comments = comments;
    }

    /**
     * @return el atributo management
     */
    public Boolean getManagement() {
        return this.management;
    }

    /**
     * @param management valor con el que se setea el campo management
     */
    public void setManagement(Boolean management) {
        this.management = management;
    }

    public Collection<TGpeMTask> getDependencies() {
        return this.dependencies;
    }

    public void setDependencies(Collection<TGpeMTask> dependencies) {
        this.dependencies = dependencies;
    }
}
