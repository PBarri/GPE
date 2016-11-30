package com.awg.gpe.web.utils.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * Clase que se encarga de la validación de los campos de tipo {@link String}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class StringFieldValidatorBuilder {
    
    private static final Pattern EMAIL_REGEX = Pattern.compile(".+@.+\\.[a-z]+");

    private final ValidatorBuilder validatorBuilder;

    private Boolean valid;

    private final String field;

    /**
     * Constructor de la clase StringFieldValidatorBuilder.java
     * @param validatorBuilder {@link ValidatorBuilder} que llama a esta clase
     * @param field Campo que se va a validar
     * @since 1.0
     */
    public StringFieldValidatorBuilder(ValidatorBuilder validatorBuilder, String field) {
        this.validatorBuilder = validatorBuilder;
        this.field = field;
        valid = validatorBuilder.isValid();
    }

    /**
     * Método que devuelve si el campo requerido está informado
     * 
     * @return Si el objeto está informado o no
     * @version 1.0
     * @since 1.0
     */
    public StringFieldValidatorBuilder required() {
        if (this.valid) {
            this.valid = StringUtils.hasText(this.field);
        }
        return this;
    }

    /**
     * Método que sirve para comprobar si un String es un email
     * 
     * @return Si el campo tiene formato de email
     * @version 1.0
     * @since 1.0
     */
    public StringFieldValidatorBuilder email() {
        // Comprobamos que el objeto sigue siendo válido
        if (this.valid) {
            Matcher matcher = StringFieldValidatorBuilder.EMAIL_REGEX.matcher(this.field);
            this.valid = matcher.matches();
        }
        return this;
    }
    
    /**
     * Método que comprueba si el campo tiene una longitud máxima definida
     * @param max Longitud máxima permitida (inclusive)
     * @return {@link StringFieldValidatorBuilder}
     * @version 1.0
     * @since 1.0
     */
    public StringFieldValidatorBuilder maxLength(Integer max) {
        if (this.valid && StringUtils.hasText(this.field)) {
            this.valid = this.field.length() <= max;
        }
        return this;
    }
    
    /**
     * Método que comprueba si el campo tiene una longitud mínima definida
     * @param min Longitud mínima permitida (inclusive)
     * @return {@link StringFieldValidatorBuilder}
     * @version 1.0
     * @since 1.0
     */
    public StringFieldValidatorBuilder minLength(Integer min) {
        if (this.valid && StringUtils.hasText(this.field)) {
            this.valid = this.field.length() >= min;
        }
        return this;
    }
    
    /**
     * Método que comprueba si el texto tiene una longitud definida en un rango
     * @param min Longitud mínima permitida (inclusive)
     * @param max Longitud máxima permitida (inclusive)
     * @return {@link StringFieldValidatorBuilder}
     * @version 1.0
     * @since 1.0
     */
    public StringFieldValidatorBuilder rangeLength(Integer min, Integer max) {
        if (this.valid && StringUtils.hasText(this.field)) {
            this.valid = this.field.length() <= max && this.field.length() >= min;
        }
        return this;
    }
    
    /**
     * Método para comprobar si el texto es igual a otro
     * @param value Texto que se quiere comprobar
     * @return {@link StringFieldValidatorBuilder}
     * @version 1.0
     * @since 1.0
     */
    public StringFieldValidatorBuilder sameText(String value) {
        if (this.valid && StringUtils.hasText(this.field)) {
            this.valid = this.field.equals(value);
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
    
    public Boolean isValid() {
        return this.valid;
    }

}
