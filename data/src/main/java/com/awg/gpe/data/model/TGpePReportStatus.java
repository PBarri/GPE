package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumReportStatus;

/**
 * Clase que modela la entidad paramétrica de estado de informes
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_REPORT_STATUS")
public class TGpePReportStatus extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePReportStatus
     */
    public TGpePReportStatus() {
    }

    /**
     * Constructor que inicializa un objeto TGpePReportStatus con los datos correspondientes
     * 
     * @param status
     *            el {@link EnumReportStatus} con el que inicializar la paramétrica
     */
    public TGpePReportStatus(EnumReportStatus type) {
        id = type.getId();
        code = type.getCode();
        description = type.getDescription();
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
        return (obj instanceof EnumReportStatus) ? id.equals(((EnumReportStatus) obj).getId()) : super.equals(obj);
    }

}
