package com.awg.gpe.data.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que modela los posibles requerimientos de los distintos proyectos.
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_REQUIREMENTS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_REQUIREMENTS", allocationSize = 1)
public class TGpeMRequirement extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMRequirement
     * 
     * @since 1.0
     */
    public TGpeMRequirement() {
        goals = new ArrayList<>();
        tasks = new ArrayList<>();
        documents = new ArrayList<>();
        developers = new ArrayList<>();
    }

    // Attributes -------------------------------------------------------------

    /**
     * Nombre del requerimiento
     * 
     * @since 1.0
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * Descripción del requerimiento
     * 
     * @since 1.0
     */
    @Column(length = 200)
    private String description;

    /**
     * URL del repositorio en el que se encuentre alojado el código fuente del requerimiento
     * 
     * @since 1.0
     */
    @Column(name = "CVS_CODE")
    private String cvsCode;

    /**
     * Código del requerimiento
     */
    @Column(name = "REQUIREMENT_CODE", length = 30)
    private String requirementCode;

    /**
     * Indicador de archivado de un requerimiento
     * <p>
     * Si esta marca se encuentra activa, el requerimiento dejará de ser accesible para todos los usuarios de la aplicación excepto para el adminsitrador, el jefe de proyecto y el
     * responsable del proyecto.
     * </p>
     * <p>
     * El responsable solamente tendrá acceso a la información del requerimiento, pero no podrá modificar nada.
     * </p>
     * <p>
     * El jefe de proyecto y el administrador podrán volver a activar el requerimiento.
     * </p>
     * @since 1.0
     */
    @Column(nullable = false)
    private Boolean archived;

    /**
     * Fecha de inicio del requerimiento
     * 
     * @since 1.0
     */
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    /**
     * Fecha prevista de fin del requerimiento.
     * <p>
     * Esta fecha puede ser modificada, pero deberá hacerse con cuidado ya que influirá en los datos que se muestre en el dashboard
     * </p>
     * @since 1.0
     */
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    /**
     * Número de horas en las que esta valorado un requerimiento
     * <p>
     * Este campo solamente es necesario en requerimientos que sigan la metodología Método v3.
     * </p>
     * @since 1.0
     */
    private Long hours;

    // Relationships ----------------------------------------------------------
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
     * El proyecto sobre el que se crea este requirimiento
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID", nullable = false)
    private TGpeMProject project;

    /**
     * Colección de objetivos asociados al requerimiento
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL)
    private Collection<TGpeMGoal> goals;

    /**
     * Colección de tareas contenidas en el requerimientos
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL)
    private Collection<TGpeMTask> tasks;

    /**
     * Colección de documentos del requerimiento
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "requirement", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Collection<TGpeMDocument> documents;
    
    /**
     * Colección conteniendo los desarrolladores asociados al proyecto
     * 
     * @since 1.0
     */
    @ManyToMany(mappedBy = "requirementsDeveloped")
    private Collection<TGpeMUser> developers;

    // Helper methods ---------------------------------------------------------

    /**
     * Método que incluye un nuevo objetivo en el requerimiento
     * 
     * @param goal
     *            - El objetivo a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addGoal(TGpeMGoal goal) {
        if (!goals.contains(goal)) {
            goals.add(goal);
	        goal.setRequirement(this);
        }
    }

    /**
     * Método que elimina un objetivo determinado del requerimiento
     * 
     * @param goal
     *            - El objetivo a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeGoal(TGpeMGoal goal) {
        goals.remove(goal);
        goal.setRequirement(null);
    }

    /**
     * Método que añade una tarea al requerimiento
     * 
     * @param task La tarea a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addTask(TGpeMTask task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
	        task.setRequirement(this);
        }
    }
    
    /**
     * Método que añade una tarea al requerimiento, incluyendo todas sus subtareas de forma recursiva
     * 
     * @param task Tarea a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addCompleteTask(TGpeMTask task) {
        if (!tasks.contains(task)) {
	    	if (!task.getChilds().isEmpty()) {
	            task.getChilds().forEach(t -> addTask(t));
	        }
            tasks.add(task);
	        task.setRequirement(this);
        }
    }

    /**
     * Método que elimina una tarea del requerimiento
     * 
     * @param task La tarea a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeTask(TGpeMTask task) {
        tasks.remove(task);
        task.setRequirement(null);
    }

    /**
     * Método que añade un documento al requerimiento
     * 
     * @param document El documento a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addDocument(TGpeMDocument document) {
        documents.add(document);
        document.setRequirement(this);
    }

    /**
     * Método que elimina un documento del requerimiento
     * 
     * @param document El documento a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeDocument(TGpeMDocument document) {
        documents.remove(document);
        document.setRequirement(null);
    }
    
    /**
     * Método que añade un desarrollador del requerimiento
     * 
     * @param user Desarrollador a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addDeveloper(TGpeMUser user) {
        addDeveloper(user, true);
    }
    
    protected void addDeveloper(TGpeMUser user, boolean follow) {
        if (!developers.contains(user)) {
            developers.add(user);
	        if (follow) {
	            user.addRequirementDeveloped(this, false);
	        }
        }
    }
    
    /**
     * Método que elimina a un desarrollador del requerimiento
     * 
     * @param user Desarrollador a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeDeveloper(TGpeMUser user) {
        removeDeveloper(user, true);
    }
    
    protected void removeDeveloper(TGpeMUser user, boolean follow) {
        developers.remove(user);

        if (follow) {
            user.removeRequirementDeveloped(this, false);
        }
    }

    // Getters and Setters ----------------------------------------------------

    /**
     * @return el atributo name
     */
    public String getName() {
        return this.name;
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
     * @return el atributo cvsCode
     */
    public String getCvsCode() {
        return this.cvsCode;
    }

    /**
     * @param cvsCode
     *            valor con el que se setea el campo cvsCode
     */
    public void setCvsCode(String cvsCode) {
        this.cvsCode = cvsCode;
    }

    /**
     * @return el atributo requirementCode
     */
    public String getRequirementCode() {
        return this.requirementCode;
    }

    /**
     * @param requirementCode
     *            valor con el que se setea el campo requirementCode
     */
    public void setRequirementCode(String requirementCode) {
        this.requirementCode = requirementCode;
    }

    /**
     * @return el atributo archived
     */
    public Boolean getArchived() {
        return this.archived;
    }

    /**
     * @param archived
     *            valor con el que se setea el campo archived
     */
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    /**
     * @return el atributo startDate
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * @param startDate
     *            valor con el que se setea el campo startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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
     * @return el atributo hours
     */
    public Long getHours() {
        return this.hours;
    }

    /**
     * @param hours
     *            valor con el que se setea el campo hours
     */
    public void setHours(Long hours) {
        this.hours = hours;
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
     * @return el atributo project
     */
    public TGpeMProject getProject() {
        return this.project;
    }

    /**
     * @param project
     *            valor con el que se setea el campo project
     */
    public void setProject(TGpeMProject project) {
        this.project = project;
    }

    /**
     * @return el atributo goals
     */
    public Collection<TGpeMGoal> getGoals() {
        return this.goals;
    }

    /**
     * @param goals
     *            valor con el que se setea el campo goals
     */
    public void setGoals(Collection<TGpeMGoal> goals) {
        this.goals = goals;
    }

    /**
     * @return el atributo tasks
     */
    public Collection<TGpeMTask> getTasks() {
        return this.tasks;
    }

    /**
     * @param tasks
     *            valor con el que se setea el campo tasks
     */
    public void setTasks(Collection<TGpeMTask> tasks) {
        this.tasks = tasks;
    }

    /**
     * @return el atributo documents
     */
    public Collection<TGpeMDocument> getDocuments() {
        return this.documents;
    }

    /**
     * @param documents
     *            valor con el que se setea el campo documents
     */
    public void setDocuments(Collection<TGpeMDocument> documents) {
        this.documents = documents;
    }

    /**
     * @param name
     *            valor con el que se setea el campo name
     */
    public void setName(String name) {
        this.name = name;
    }

	/**
	 * @return the developers
	 */
	public Collection<TGpeMUser> getDevelopers() {
		return this.developers;
	}

	/**
	 * @param developers the developers to set
	 */
	public void setDevelopers(Collection<TGpeMUser> developers) {
		this.developers = developers;
	}
}
