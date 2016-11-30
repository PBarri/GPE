package com.awg.gpe.web.utils.validations;

import java.util.Collection;

/**
 * Clase encargada de la validación de colecciones
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class CollectionFieldValidatorBuilder {
    
    private final ValidatorBuilder validatorBuilder;

    private Boolean valid;

    private final Collection<?> field;
    
    /**
     * Constructor vacío de la clase CollectionFieldValidatorBuilder.java
     * @since 1.0
     */
    public CollectionFieldValidatorBuilder(ValidatorBuilder validatorBuilder, Collection<?> field) {
        this.validatorBuilder = validatorBuilder;
        this.field = field;
        valid = validatorBuilder.isValid();
    }
    
    /**
     * Comprueba que la colección no sea nula o esté vacía
     * 
     * @return Si el campo ha pasado la validación
     * @version 1.0
     * @since 1.0
     */
    public CollectionFieldValidatorBuilder required() {
        if (this.valid) {
            this.valid = this.field != null && !this.field.isEmpty();
        }
        return this;
    }
    
    /**
     * Comprueba que la colección tenga un número máximo de elementos
     * 
     * @param maxLength Límite superior de elementos para la colección
     * @return true si el campo ha pasado la validación
     * @version 1.0
     * @since 1.0
     */
    public CollectionFieldValidatorBuilder length(Integer maxLength) {
        if (this.valid && this.field != null) {
            this.valid = this.field.size() <= maxLength;
        }
        return this;
    }
    
    /**
     * Termina la validación de este campo
     * @return {@link ValidatorBuilder} para seguir configurando la validación
     * @version 1.0
     * @since 1.0
     */
    public ValidatorBuilder and() {
        return this.validatorBuilder.and(this);
    }
    
    /**
     * @return si es válido o no
     * @version 1.0
     * @since 1.0
     */
    public Boolean isValid() {
        return this.valid;
    }

}
