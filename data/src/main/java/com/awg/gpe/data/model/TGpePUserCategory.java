package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.awg.gpe.data.enums.EnumUserPosition;

/**
 * Clase que modela la entidad paramétrica de categoría profesional de los usuarios
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_P_USER_CATEGORY")
public class TGpePUserCategory extends BaseParametricsEntity {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpePUserCategory
     */
    public TGpePUserCategory() {
    }

    /**
     * Constructor que inicializa un objeto TGpePUserCategory con los datos correspondientes
     * 
     * @param category
     *            el {@link EnumUserPosition} con el que inicializar la paramétrica
     */
    public TGpePUserCategory(EnumUserPosition category) {
        id = category.getId();
        code = category.getCode();
        description = category.getDescription();
    }

    // Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpePUserCategory
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumUserCategory , con un Long, que corresponda al campo ID de la categoría, o con otro objeto
     * TGpePUserCategory.
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumUserPosition) ? id.equals(((EnumUserPosition) obj).getId()) : super.equals(obj);
    }

}
