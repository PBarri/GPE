package com.awg.gpe.web.utils.validations;

/**
 * Clase que se encarga de la validación de campos de clases que extiendan el tipo {@link Object}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class FieldValidatorBuilder {
    
    private final ValidatorBuilder validatorBuilder;
    
    private Boolean valid;
    
    private final Object field;
    
    /**
     * Constructor de la clase FieldValidatorBuilder.java
     * @param validatorBuilder {@link ValidatorBuilder} que llama a esta clase
     * @param field Objeto a validar
     * @since 1.0
     */
    public FieldValidatorBuilder(ValidatorBuilder validatorBuilder, Object field) {
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
    public FieldValidatorBuilder required() {
        // Comprobamos antes que el objeto sigue siendo válido
        if (this.valid) {
            if (this.field == null) {
                this.valid = false;
            }
        }       
        return this;
    }
    
    /**
     * Método que termina la validación de este campo
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