package com.awg.gpe.web.utils.validations;

import java.time.LocalDateTime;

import org.springframework.util.Assert;

/**
 * Clase encargada de la validación de los campos de tipo {@link LocalDateTime}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class LocalDateTimeValidationBuilder {
    
    private final ValidatorBuilder validatorBuilder;
    
    private Boolean valid;
    
    private final LocalDateTime field;
    
    /**
     * Constructor de la clase FieldValidatorBuilder.java
     * @param validatorBuilder {@link ValidatorBuilder} que llama a esta clase
     * @param field Objeto a validar
     * @since 1.0
     */
    public LocalDateTimeValidationBuilder(ValidatorBuilder validatorBuilder, LocalDateTime field) {
        this.validatorBuilder = validatorBuilder;
        this.field = field;
        valid = validatorBuilder.isValid();
    }
    
    /**
     * Comprueba que el campo está informado
     * 
     * @return el validador para seguir haciendo comprobaciones
     * @version 1.0
     * @since 1.0
     */
    public LocalDateTimeValidationBuilder required() {
        if (this.valid) {
            if (this.field == null) {
                this.valid = false;
            }
        }
        return this;
    }
    
    /**
     * Comprueba que la fecha del campo es inferior a la pasada por parámetro
     * 
     * @param value {@link LocalDateTime} con el que se quiere hacer la comprobación
     * @return el validador para seguir haciendo comprobaciones
     * @version 1.0
     * @since 1.0
     */
    public LocalDateTimeValidationBuilder before(LocalDateTime value) {
        if (this.valid && this.field != null) {
            Assert.notNull(value);
            this.valid = this.field.isBefore(value);
        }
        return this;
    }
    
    /**
     * Comprueba que la fecha del campo es superior a la pasada por parámetro
     * 
     * @param value {@link LocalDateTime} con el que se quiere hacer la comprobación
     * @return el validador para seguir haciendo comprobaciones
     * @version 1.0
     * @since 1.0
     */
    public LocalDateTimeValidationBuilder after(LocalDateTime value) {
        if (this.valid && this.field != null) {
            Assert.notNull(value);
            this.valid = this.field.isAfter(value);
        }
        return this;
    }
    
    /**
     * Comprueba que la fecha del campo se encuentra entre dos instantes de tiempo
     * 
     * @param before {@link LocalDateTime} que marca el inicio del rango
     * @param after {@link LocalDateTime} que marca el fin del rango
     * @return el validador para seguir haciendo comprobaciones
     * @version 1.0
     * @since 1.0
     */
    public LocalDateTimeValidationBuilder isBetween(LocalDateTime before, LocalDateTime after) {
        if (this.valid && this.field != null) {
            Assert.notNull(before);
            Assert.notNull(after);
            this.valid = this.field.isAfter(before) && this.field.isBefore(after);
        }
        return this;
    }
    
    /**
     * Método que termina la validación de este campo
     * 
     * @return
     * @version 1.0
     * @since 1.0
     */
    public ValidatorBuilder and() {
        return this.validatorBuilder.and(this);
    }
    
    public Boolean isValid() {
        return this.valid;
    }

}
