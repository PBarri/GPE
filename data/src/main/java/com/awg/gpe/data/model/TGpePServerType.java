package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumServerType;

/**
 * Clase que modela la entidad de tipo de servidores
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_SERVER_TYPE")
public class TGpePServerType extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePServerType
     */
    public TGpePServerType() {
    }

    /**
     * Constructor que inicializa un objeto TGpePServerType con los datos correspondientes
     * 
     * @param status
     *            el {@link EnumServerType} con el que inicializar la paramétrica
     */
    public TGpePServerType(EnumServerType status) {
        id = status.getId();
        code = status.getCode();
        description = status.getDescription();
    }

    // Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePServerType
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumServerType , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePServerType.
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumServerType) ? id.equals(((EnumServerType) obj).getId()) : super.equals(obj);
    }
}
