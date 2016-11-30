package com.awg.gpe.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumReportStatus;
import com.awg.gpe.data.enums.EnumReportType;

/**
 * Clase que modela las peticiones de informes de la aplicación
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_REPORTS")
@SequenceGenerator(name = "generator", sequenceName = "S_GPE_REPORTS", allocationSize = 1)
public class TGpeMReport extends BaseEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMReport.java
     * 
     * @since 1.0
     */
    public TGpeMReport() {
        status = new TGpePReportStatus(EnumReportStatus.PENDING);
    }

    /**
     * Constructor que recoge un {@link EnumReportType} con el tipo de informe solicitado
     * @param type El tipo del informe solicitado
     * @since 1.0
     */
    public TGpeMReport(EnumReportType type) {
        status = new TGpePReportStatus(EnumReportStatus.PENDING);
        this.type = new TGpePReportType(type);
    }

    // Attributes -------------------------------------------------------------

    /**
     * Nombre del informe
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private String name;

    /**
     * Nombre del fichero en el que está contenido el informe
     * 
     * @since 1.0
     */
    private String filename;

    // Relationships ----------------------------------------------------------
    /**
     * Usuario que ha encargado el informe. En el caso de que sea nulo se tratará de informes automáticos, accesibles para todos los usuarios
     * 
     * @since 1.0
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private TGpeMUser user;

    /**
     * Tipo del informe
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "REPORT_TYPE", referencedColumnName = "ID")
    private TGpePReportType type;

    /**
     * Estado en el que se encuentra el informe
     * 
     * @since 1.0
     */
    @OneToOne
    @JoinColumn(name = "REPORT_STATUS", referencedColumnName = "ID")
    private TGpePReportStatus status;

    	// Getters and Setters ----------------------------------------------------
    
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the user
	 */
	public TGpeMUser getUser() {
		return this.user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(TGpeMUser user) {
		this.user = user;
	}

	/**
	 * @return the type
	 */
	public TGpePReportType getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TGpePReportType type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public TGpePReportStatus getStatus() {
		return this.status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TGpePReportStatus status) {
		this.status = status;
	}

}
