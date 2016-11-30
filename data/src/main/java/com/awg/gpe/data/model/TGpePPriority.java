package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumPriority;

/**
 * Clase que modela la entidad paramétrica de prioridades
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_PRIORITY")
public class TGpePPriority extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePPriority.java
     * 
     * @since 1.0
     */
    public TGpePPriority() {
    }

    /**
     * Constructor que inicializa un objeto TGpePTaskStatus con los datos correspondientes
     * 
     * @param priority
     *            el {@link EnumPriority} con el que inicializar la paramétrica
     */
    public TGpePPriority(EnumPriority priority) {
        id = priority.getPriority().longValue();
        code = priority.getCode();
    }

    // Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePPriority
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumPriority , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePPriority.
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumPriority) ? id.equals(((EnumPriority) obj).getPriority().longValue()) : super.equals(obj);
    }

}
