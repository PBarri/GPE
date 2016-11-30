package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumTaskType;

/**
 * Clase que modela la entidad paramétrica de tipo de tarea
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_TASK_TYPE")
public class TGpePTaskType extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePTaskType.java
     * 
     * @since 1.0
     */
    public TGpePTaskType() {
    }

    /**
     * Constructor a partir de un enum de la clase TGpePTaskType.java
     * 
     * @param taskType
     *            el {@link EnumTaskType} con el que inicializar la paramétrica
     * @since 1.0
     */
    public TGpePTaskType(EnumTaskType taskType) {
        id = taskType.getId();
        code = taskType.getCode();
        description = taskType.getDescription();
    }

    // Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePTaskType
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumTaskType , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePTaskType.
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumTaskType) ? id.equals(((EnumTaskType) obj).getId()) : super.equals(obj);
    }

}
