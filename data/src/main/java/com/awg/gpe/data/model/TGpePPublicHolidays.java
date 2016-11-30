package com.awg.gpe.data.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Clase que modela la entidad paramétrica de días libres oficiales
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_PUBLIC_HOLIDAYS")
public class TGpePPublicHolidays extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;
    
    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePPublicHolidays.java
     * @since 1.0
     */
    public TGpePPublicHolidays() {  }
    
    /**
     * Día libre
     * 
     * @since 1.0
     */
    @Column(nullable = false)
    private LocalDate day;
    
    public LocalDate getDay() {
		return this.day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	// Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePReportStatus
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumReportStatus , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePReportStatus.
     */
    public boolean equals(Object obj) {
        return (obj instanceof TGpePPublicHolidays) ? id.equals(((TGpePPublicHolidays) obj).getId()) : super.equals(obj);
    }
    
}
