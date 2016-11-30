package com.awg.gpe.web.utils.validations;

/**
 * Clase que se encarga de la validación de los campos de tipo {@link Number}, y todos los que extiendan de ella
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class NumericFieldValidationBuilder<T extends Number & Comparable<T>> {

    private final ValidatorBuilder validatorBuilder;

    private final T field;

    private Boolean valid;
    
    /**
     * Constructor de la clase NumericFieldValidationBuilder.java
     * @param validatorBuilder {@link ValidatorBuilder} que crea esta clase
     * @param field Campo que se va a validar
     * @since 1.0
     */
    public NumericFieldValidationBuilder(ValidatorBuilder validatorBuilder, T field) {
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
    public NumericFieldValidationBuilder<T> required() {
        if (this.valid) {
            this.valid = this.field != null;
        }
        return this;
    }
    
    /**
     * Comprueba si el valor es menor que el pasado por parámetros
     * @param value Valor a comparar
     * @return {@link NumericFieldValidationBuilder} 
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<T> minor(T value) {
        if (this.valid) {
            this.valid = this.field.compareTo(value) < 0;
        }
        return this;
    }
    
    /**
     * Comprueba si el valor es mayor que el pasado por parámetros
     * @param value Valor a comparar
     * @return {@link NumericFieldValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<T> greater(T value) {
        if (this.valid) {
            this.valid = this.field.compareTo(value) > 0;
        }
        return this;
    }
    
    /**
     * Comprueba si el valor es igual al pasado por parámetros
     * @param value Valor a comparar
     * @return {@link NumericFieldValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<T> equal(T value) {
        if (this.valid) {
            this.valid = this.field.compareTo(value) == 0;
        }
        return this;
    }
    
    /**
     * Comprueba si el valor está comprendido en el rango pasado por parámetros
     * @param min Límite inferior del rango (inclusive)
     * @param max Límite superior del rango (inclusive)
     * @return {@link NumericFieldValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<T> range(T min, T max) {
        if (this.valid) {
            this.valid = this.field.compareTo(min) >= 0 && this.field.compareTo(max) <= 0;
        }
        return this;
    }   
    
    /**
     * Termina de setear {@link NumericFieldValidationBuilder} y sigue configurando el resto de la validación
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
