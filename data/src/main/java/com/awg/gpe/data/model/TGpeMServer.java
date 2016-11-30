package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que modela la entidad de Servidores
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_SERVERS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_SERVERS", allocationSize = 1)
public class TGpeMServer extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMServer.java
     * 
     * @since 1.0
     */
    public TGpeMServer() {
    }

    // Attributes -------------------------------------------------------------

    /**
     * Nombre del host del servidor
     * 
     * @since 1.0
     */
    private String hostname;

    /**
     * IP del servidor
     * 
     * @since 1.0
     */
    private String ip;

    // Relationships ----------------------------------------------------------

    /**
     * Tipo de servidor
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "SERVER_TYPE", referencedColumnName = "ID")
    private TGpePServerType serverType;

    /**
     * Proyecto al que está asociado el servidor
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    private TGpeMProject project;

    // Getters and Setters ----------------------------------------------------

    /**
     * @return el atributo hostname
     */
    public String getHostname() {
        return this.hostname;
    }

    /**
     * @param hostname
     *            valor con el que se setea el campo hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return el atributo ip
     */
    public String getIp() {
        return this.ip;
    }

    /**
     * @param ip
     *            valor con el que se setea el campo ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return el atributo serverType
     */
    public TGpePServerType getServerType() {
        return this.serverType;
    }

    /**
     * @param serverType
     *            valor con el que se setea el campo serverType
     */
    public void setServerType(TGpePServerType serverType) {
        this.serverType = serverType;
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
}
