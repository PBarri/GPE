package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumReportType;

/**
 * Clase que modela la entidad paramétrica de tipo de informe
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_REPORT_TYPE")
public class TGpePReportType extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePReportType
     */
    public TGpePReportType() {
    }

    /**
     * Constructor que inicializa un objeto TGpePReportType con los datos correspondientes
     * 
     * @param status
     *            el {@link EnumReportType} con el que inicializar la paramétrica
     */
    public TGpePReportType(EnumReportType type) {
        id = type.getId();
        code = type.getCode();
        description = type.getDescription();
    }

    // Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePReportType
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumReportType , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePReportType.
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumReportType) ? id.equals(((EnumReportType) obj).getId()) : super.equals(obj);
    }

}
