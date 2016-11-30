package com.awg.gpe.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumTaskStatus;

/**
 * Clase que modela la entidad paramétrica de estado de tarea
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_TASK_STATUS")
public class TGpePTaskStatus extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePTaskStatus
     */
    public TGpePTaskStatus() {
    }

    /**
     * Constructor que inicializa un objeto TGpePTaskStatus con los datos correspondientes
     * 
     * @param status
     *            el {@link EnumTaskStatus} con el que inicializar la paramétrica
     */
    public TGpePTaskStatus(EnumTaskStatus status) {
        id = status.getId();
        code = status.getCode();
        description = status.getDescription();
        color = status.getColor();
    }
    
    
    /**
     * Color que se mostrará en los gráficos para este estado de la tarea. Se mostrará en formato hexadecimal
     */
    @Column(length = 7)
    private String color;

    public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	// Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePTaskStatus
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumTaskStatus , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePTaskStatus.
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumTaskStatus) ? id.equals(((EnumTaskStatus) obj).getId()) : super.equals(obj);
    }

}
