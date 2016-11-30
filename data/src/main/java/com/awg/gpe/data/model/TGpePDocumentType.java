package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumDocumentType;

/**
 * Clase que modela la entidad paramétrica de tipos de documento
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_DOCUMENT_TYPES")
public class TGpePDocumentType extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePDocumentType
     */
    public TGpePDocumentType() {
    }

    /**
     * Constructor que inicializa un objeto TGpePDocumentType con los datos correspondientes
     * 
     * @param type
     *            el {@link EnumDocumentType} con el que inicializar la paramétrica
     */
    public TGpePDocumentType(EnumDocumentType type) {
        id = type.getId();
        code = type.getCode();
        description = type.getDescription();
    }

    // Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePDocumentType
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumTaskStatus , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePDocumentType.
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumDocumentType) ? id.equals(((EnumDocumentType) obj).getId()) : super.equals(obj);
    }

}
