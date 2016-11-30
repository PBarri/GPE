package com.awg.gpe.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Clase base para todas las entidades paramétricas del sistema.
 * <p>
 * Esta clase contiene una PK, un código y una descripción, además de una implementación
 * de los métodos {@link Object#equals(Object)}, {@link Object#hashCode()} y {@link Object#toString()}
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@MappedSuperclass
public class BaseParametricsEntity implements Serializable {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    public BaseParametricsEntity() {
    }

    // Attributes -------------------------------------------------------------
    @Id
    protected Long id;

    @Column(unique = true, updatable = false, length = 50)
    protected String code;

    @Column(length = 500)
    protected String description;

    // Equals and Hashcode ----------------------------------------------------
    @Override
    public int hashCode() {
        return getClass().hashCode() + getId().intValue();
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d , code=%s]", getClass().getSimpleName(), this.id, this.code);
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
     * @return el atributo code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code
     *            valor con el que se setea el campo code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return el atributo description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *            valor con el que se setea el campo description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param id
     *            valor con el que se setea el campo id
     */
    public void setId(Long id) {
        this.id = id;
    }

}
