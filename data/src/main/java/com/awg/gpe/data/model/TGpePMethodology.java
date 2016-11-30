package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumMethodology;

/**
 * Clase que modela la entidad paramétrica de metodologías 
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_METHODOLOGIES")
public class TGpePMethodology extends BaseParametricsEntity {

    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------

    /**
     * Constructor vacío de la clase TGpePMethodology
     */
    public TGpePMethodology() {
    }

    /**
     * Constructor de la clase TGpePMethodology a partir de un enum
     * 
     * @param methodology
     *            el {@link EnumMethodology} con el que inicializar la paramétrica
     */
    public TGpePMethodology(EnumMethodology methodology) {
        id = methodology.getId();
        code = methodology.getCode();
        description = methodology.getDescription();
    }

    // Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePMethodology
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumMethodology , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePMethodology.
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumMethodology) ? id.equals(((EnumMethodology) obj).getId()) : super.equals(obj);
    }
}
