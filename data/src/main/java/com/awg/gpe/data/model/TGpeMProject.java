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

import org.springframework.util.Assert;

import com.awg.gpe.data.enums.EnumMethodology;

/** 
 * Clase que modela la entidad de Proyectos
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_PROJECTS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_PROJECTS", allocationSize = 1)
public class TGpeMProject extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMProject
     */
    public TGpeMProject() {
        requeriments = new ArrayList<>();
        managers = new ArrayList<>();
        managers = new ArrayList<>();
        servers = new ArrayList<>();
    }

    // Attributes -------------------------------------------------------------

    /**
     * Nombre del proyecto
     * 
     * @since 1.0
     */
    @Column(unique = true, nullable = false, length = 20)
    private String name;

    /**
     * Descripción del proyecto
     * 
     * @since 1.0
     */
    @Column(length = 4000)
    private String description;

    /**
     * URL del repositorio en el que se encuentre alojado el c�digo fuente del proyecto
     * 
     * @since 1.0
     */
    @Column(name = "CVS_CODE")
    private String cvsCode;

    /**
     * Código del proyecto
     * 
     * @since 1.0
     */
    @Column(name = "PROJECT_CODE", length = 30)
    private String projectCode;
    
    /**
     * Siglas del proyecto
     * @since 1.0
     */
    @Column(name = "PROJECT_TITLE", length = 3)
    private String projectTitle;
    
    /**
     * Logo del proyecto a mostrar. El sistema la redimensionará para ocupar menos espacio en disco
     * @since 1.0
     */
    @Column(name = "PROJECT_LOGO")
    private String projectLogo;

    /**
     * Indicador de borrado de un proyecto.
     * <p>
     * Si esta marca se encuentra activada, el proyecto dejará de ser accesible para todos los usuarios de la aplicación menos para el administrador de la misma.
     * </p>
     * @since 1.0
     */
    @Column(nullable = false)
    private Boolean deleted;

    /**
     * Indicador de archivado de un proyecto
     * <p>
     * Si esta marca se encuentra activa, el proyecto dejará de ser accesible para todos los usuarios de la aplicación excepto para el adminsitrador, el jefe de proyecto y el
     * responsable del proyecto.
     * </p>
     * <p>
     * El responsable solamente tendrá acceso a la información del proyecto, pero no podrá modificar nada.
     * </p>
     * <p>
     * El jefe de proyecto y el administrador podrán volver a activar el proyecto.
     * </p>
     * @since 1.0
     */
    @Column(nullable = false)
    private Boolean archived;

    /**
     * Fecha de inicio del proyecto
     * 
     * @since 1.0
     */
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    /**
     * Fecha de fin del proyecto.
     * <p>
     * Esta fecha solamente se informa cuando el proyecto se borra o se archiva.
     * </p>
     * @since 1.0
     */
    @Column(name = "END_DATE")
    private LocalDate endDate;

    /**
     * Marca que informa del identificador del usuario que creó el proyecto
     * 
     * @since 1.0
     */
    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    /**
     * Marca que informa del último usuario que modifica la información del proyecto.
     * <p>
     * Esta marca se informa con el identificador del usuario
     * </p>
     */
    @Column(name = "LAST_EDITION_BY", nullable = false)
    private String lastEditionBy;

    // Relationships ----------------------------------------------------------
    /**
     * Relación que indica el jefe de este proyecto.
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "PROJECT_LEADER", nullable = false)
    private TGpeMUser leader;

    /**
     * Relación con los requerimientos bajo este proyecto 
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Collection<TGpeMRequirement> requeriments;

    // // Special fields for Scrum Requirements
    /**
     * Relación que indica el usuario que va a realizar la función de Product Owner
     * <p>
     * Este campo solamente estará informado para los proyectos que usen metodología Scrum
     * </p>
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "PRODUCT_OWNER", referencedColumnName = "ID")
    private TGpeMUser productOwner;

    /**
     * Relación que indica el usuario que realizará la función de Scrum Master.
     * <p>
     * Este campo solamente estará informado para los proyectos que usen la metodología Scrum
     * </p>
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "SCRUM_MASTER", referencedColumnName = "ID")
    private TGpeMUser scrumMaster;

    // // Special fields for Metodo v3 Requirements
    /**
     * Relación que indica los responsables que tiene este proyectos.
     * <p>
     * Este campo solamente estará informado para los proyectos que usen la metodología Método V3
     * </p>
     * @since 1.0
     */
    @ManyToMany(mappedBy = "projectsManaged")
    private Collection<TGpeMUser> managers;

    /**
     * Relación que indica la metodología que se va a usar en el proyectos.
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "METHODOLOGY", referencedColumnName = "ID", nullable = false)
    private TGpePMethodology methodology;

    /**
     * Relación con los documentos asociados a el proyecto
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Collection<TGpeMDocument> documents;

    /**
     * Relacón con los servidores asociados al proyecto
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<TGpeMServer> servers;
    
    /**
     * Relación que indica los usuarios que han pasado por este proyecto
     * 
     * @since 1.0
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private Collection<TGpeRHistoricalProjects> historicalUsers;

    // Helper methods ---------------------------------------------------------

    /**
     * Método auxiliar que devuelve si el proyecto es de metodología Scrum
     * 
     * @return si el proyecto usa la metodología Scrum
     * @version 1.0
     * @since 1.0
     */
    public boolean isScrum() {
        return this.methodology.equals(EnumMethodology.SCRUM);
    }

    /**
     * Método auxiliar que devuelve si el proyecto es de metodología Metrica V3
     * 
     * @return si el proyecto usa la metodología Metrica V3.
     * @version 1.0
     * @since 1.0
     */
    public boolean isMetricaV3() {
        return this.methodology.equals(EnumMethodology.METRICA_V3);
    }

    /**
     * Método que añade un nuevo requerimiento al proyecto
     * 
     * @param requirement El requerimiento a añadir
     * @see java.util.Collection#add(java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    public void addRequirement(TGpeMRequirement requirement) {
        if (!requeriments.contains(requirement)) {
            requeriments.add(requirement);
	        requirement.setProject(this);
        }
    }

    /**
     * Método que elimina un requerimiento del proyecto
     * 
     * @param requirement El requerimiento a eliminar
     * @see java.util.Collection#remove(java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    public void removeRequirement(TGpeMRequirement requirement) {
        this.requeriments.remove(requirement);
        requirement.setProject(null);
    }

    /**
     * Método que añade un nuevo responsable al proyecto
     * 
     * @param manager El responsable de proyecto a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addManager(TGpeMUser manager) {
        Assert.isTrue(methodology.equals(EnumMethodology.METRICA_V3));
        addManager(manager, true);
    }
    
    protected void addManager(TGpeMUser manager, boolean follow) {
        if (!managers.contains(manager)) {
            managers.add(manager);
	        if (follow) {
	            manager.addManagedProject(this, false);
	        }
        }
    }

    /**
     * Método que elimina un responsable del proyecto
     * 
     * @param manager El responsable del proyecto a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeManager(TGpeMUser manager) {
        Assert.isTrue(methodology.equals(EnumMethodology.METRICA_V3));
        removeManager(manager, true);
    }
    
    protected void removeManager(TGpeMUser manager, boolean follow) {
        managers.remove(manager);

        if (follow) {
            manager.removeManagedProject(this, false);
        }
    }

    /**
     * Método que añade un documento al proyecto
     * 
     * @param document El documento a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addDocument(TGpeMDocument document) {
        documents.add(document);
        document.setProject(this);
    }

    /**
     * Método que elimina un documento del proyecto
     * 
     * @param document El documento a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeDocument(TGpeMDocument document) {
        documents.remove(document);
        document.setProject(null);
    }

    /**
     * Método que añade un servidor al proyecto
     * 
     * @param server Servidor a añadir
     * @version 1.0
     * @since 1.0
     */
    public void addServer(TGpeMServer server) {
        if (!servers.contains(server)) {
            servers.add(server);
	        server.setProject(this);
        }
    }

    /**
     * Método que elimina un servidor del proyecto
     * 
     * @param server Servidor a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeServer(TGpeMServer server) {
        servers.remove(server);
        server.setProject(null);
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
     * @return el atributo projectCode
     */
    public String getProjectCode() {
        return this.projectCode;
    }

    /**
     * @param projectCode
     *            valor con el que se setea el campo projectCode
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * @return el atributo deleted
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * @param deleted
     *            valor con el que se setea el campo deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
     * @return el atributo createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * @param createdBy
     *            valor con el que se setea el campo createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return el atributo lastEditionBy
     */
    public String getLastEditionBy() {
        return this.lastEditionBy;
    }

    /**
     * @param lastEditionBy
     *            valor con el que se setea el campo lastEditionBy
     */
    public void setLastEditionBy(String lastEditionBy) {
        this.lastEditionBy = lastEditionBy;
    }

    /**
     * @return el atributo leader
     */
    public TGpeMUser getLeader() {
        return this.leader;
    }

    /**
     * @param leader
     *            valor con el que se setea el campo leader
     */
    public void setLeader(TGpeMUser leader) {
        this.leader = leader;
    }

    /**
     * @return el atributo requeriments
     */
    public Collection<TGpeMRequirement> getRequeriments() {
        return this.requeriments;
    }

    /**
     * @param requeriments
     *            valor con el que se setea el campo requeriments
     */
    public void setRequeriments(Collection<TGpeMRequirement> requeriments) {
        this.requeriments = requeriments;
    }

    /**
     * @return el atributo productOwner
     */
    public TGpeMUser getProductOwner() {
        return this.productOwner;
    }

    /**
     * @param productOwner
     *            valor con el que se setea el campo productOwner
     */
    public void setProductOwner(TGpeMUser productOwner) {
        this.productOwner = productOwner;
    }

    /**
     * @return el atributo scrumMaster
     */
    public TGpeMUser getScrumMaster() {
        return this.scrumMaster;
    }

    /**
     * @param scrumMaster
     *            valor con el que se setea el campo scrumMaster
     */
    public void setScrumMaster(TGpeMUser scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    /**
     * @return el atributo managers
     */
    public Collection<TGpeMUser> getManagers() {
        return this.managers;
    }

    /**
     * @param managers
     *            valor con el que se setea el campo managers
     */
    public void setManagers(Collection<TGpeMUser> managers) {
        this.managers = managers;
    }

    /**
     * @return el atributo methodology
     */
    public TGpePMethodology getMethodology() {
        return this.methodology;
    }

    /**
     * @param methodology
     *            valor con el que se setea el campo methodology
     */
    public void setMethodology(TGpePMethodology methodology) {
        this.methodology = methodology;
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
     * @return el atributo servers
     */
    public Collection<TGpeMServer> getServers() {
        return this.servers;
    }

    /**
     * @param servers
     *            valor con el que se setea el campo servers
     */
    public void setServers(Collection<TGpeMServer> servers) {
        this.servers = servers;
    }

    /**
     * @param name
     *            valor con el que se setea el campo name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que devuelve el título del proyecto.
     * <p>
     * En el caso de que se haya informado el título del proyecto, se devuelve el título.
     * </p>
     * <p>
     * En el caso de que no esté informado, devuelve los tres primeros caracteres del nombre.
     * </p>
     * 
     * @return el título del proyecto
     * @version 1.0
     * @since 1.0
     */
    public String getProjectTitle() {
        return this.projectTitle != null ? this.projectTitle : this.name != null ? this.name.substring(0, 3) : null;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = (projectTitle != null) ? projectTitle.toUpperCase() : projectTitle;
    }

	/**
	 * @return the projectLogo
	 */
	public String getProjectLogo() {
		return this.projectLogo;
	}

	/**
	 * @param projectLogo the projectLogo to set
	 */
	public void setProjectLogo(String projectLogo) {
		this.projectLogo = projectLogo;
	}

	/**
	 * @return the historicalUsers
	 */
	public Collection<TGpeRHistoricalProjects> getHistoricalUsers() {
		return this.historicalUsers;
	}

	/**
	 * @param historicalUsers the historicalUsers to set
	 */
	public void setHistoricalUsers(Collection<TGpeRHistoricalProjects> historicalUsers) {
		this.historicalUsers = historicalUsers;
	}

}
