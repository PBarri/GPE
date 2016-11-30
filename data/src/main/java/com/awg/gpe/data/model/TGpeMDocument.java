package com.awg.gpe.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que modela los documentos que serán subidos a la aplicación.
 * <p>Estos documentos pueden ser subidos por cualquier perfil de usuario, y estarán asociados a un proyecto o a un requerimiento</p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_DOCUMENTS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_REQUIREMENTS", allocationSize = 1)
public class TGpeMDocument extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMDocument.java
     * 
     * @since 1.0
     */
    public TGpeMDocument() {
    }

    // Attributes -------------------------------------------------------------

    /**
     * Nombre del archivo
     * 
     * @since 1.0
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * Descripción del fichero
     * 
     * @since 1.0
     */
    @Column(length = 500)
    private String description;

    /**
     * URL en la que está alojado el archivo
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private String url;

    /**
     * Marca que indica si el documento puede ser visto y descargado por los desarrolladores
     * 
     * @since 1.0
     */
    @Column(name = "DEVELOPER_AVAILABILITY", nullable = false)
    private Boolean developerAvailability;

    // Relationships ----------------------------------------------------------

    /**
     * Relación con los distintos tipos de documentos
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "DOCUMENT_TYPE_ID", referencedColumnName = "ID", nullable = false)
    private TGpePDocumentType documentType;

    /**
     * Relación con el proyecto
     * <p>
     * Esta relación solo será posible en el caso de que no tenga ningún requerimiento asociado
     * </p>
     * @since 1.0
     */
    @ManyToOne
    private TGpeMProject project;

    /**
     * Relación con el requerimiento
     * <p>
     * Esta relación solo seá posible en el caso de que no tenga ningún proyecto asociado
     * </p>
     * @since 1.0
     */
    @ManyToOne
    private TGpeMRequirement requirement;

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
     * @return el atributo url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url
     *            valor con el que se setea el campo url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return el atributo developerAvailability
     */
    public Boolean getDeveloperAvailability() {
        return this.developerAvailability;
    }

    /**
     * @param developerAvailability
     *            valor con el que se setea el campo developerAvailability
     */
    public void setDeveloperAvailability(Boolean developerAvailability) {
        this.developerAvailability = developerAvailability;
    }

    /**
     * @return el atributo documentType
     */
    public TGpePDocumentType getDocumentType() {
        return this.documentType;
    }

    /**
     * @param documentType
     *            valor con el que se setea el campo documentType
     */
    public void setDocumentType(TGpePDocumentType documentType) {
        this.documentType = documentType;
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
     * @param name
     *            valor con el que se setea el campo name
     */
    public void setName(String name) {
        this.name = name;
    }
}
