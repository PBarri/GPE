package com.awg.gpe.data.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Clase base para facilitar el mapeo de las vistas de la base de datos en el sistema.
 * <p>
 * Esta clase contiene el mapeo de la PK  y una implementación de los métodos 
 * {@link Object#equals(Object)}, {@link Object#hashCode()} y {@link Object#toString()}
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@MappedSuperclass
public abstract class BaseViewEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Constructors -----------------------------------------------------------
    public BaseViewEntity() {}
    
    // Attributes -------------------------------------------------------------
    @Id
    protected Long id;
    
    // Equals and Hashcode ----------------------------------------------------
    @Override
    public int hashCode() {
        return (id != null) ? id.intValue() : super.hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), this.id);
    }

    @Override
    public boolean equals(Object other) {
        boolean result;

        if (this == other) {
            result = true;
        } else if (other == null) {
            result = false;
        } else if (other instanceof Long) {
            result = getId().equals(other);
        } else if (!getClass().isInstance(other)) {
            result = false;
        } else {
            result = getId().equals(((BaseEntity) other).getId());
        }

        return result;
    }

    // Getters and Setters ----------------------------------------------------

    /**
     * @return el atributo id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            valor con el que se setea el campo id
     */
    public void setId(Long id) {
        this.id = id;
    }

}
