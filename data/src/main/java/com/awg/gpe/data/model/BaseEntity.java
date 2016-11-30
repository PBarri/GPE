package com.awg.gpe.data.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Clase base para todas las entidades
 * <p>
 * Esta clase contiene el mapeo de la PK, haciendo uso de una secuencia, así como la versión de la entidad,
 * un timestamp para controlar el último momento en el que la entidad fue modificada, y una implementación
 * de los métodos {@link Object#equals(Object)}, {@link Object#hashCode()} y {@link Object#toString()}
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;
    
    // Order fields -----------------------------------------------------------
    public static final String TIMESTAMP = "timestamp";

    // Constructors -----------------------------------------------------------
    public BaseEntity() {}

    // Attributes -------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Version
    @Column(nullable = false)
    protected Long version;

    @Column(nullable = false)
    protected LocalDateTime timestamp;

    // Equals and Hashcode ----------------------------------------------------
    @Override
    public int hashCode() {
        return (id != null) ? id.intValue() : super.hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d , version=%d]", getClass().getSimpleName(), this.id, this.version);
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
        } else if (getId() != null){
            result = getId().equals(((BaseEntity) other).getId());
        } else {
        	result = false;
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

    /**
     * @return el atributo version
     */
    public Long getVersion() {
        return this.version;
    }

    /**
     * @param version
     *            valor con el que se setea el campo version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return el atributo timestamp
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * @param timestamp
     *            valor con el que se setea el campo timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
