package com.awg.gpe.web.utils.validations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Clase encargada de controlar la validación
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class ValidatorBuilder {
    
    private Boolean valid;
    
    public ValidatorBuilder() {
        this.valid = true;
    }
    
    /**
     * @return una nueva instancia del constructor de una validación
     * @version 1.0
     * @since 1.0
     */
    public static ValidatorBuilder newInstance() {
        return new ValidatorBuilder();
    }
    
    /**
     * Método final que realiza la validación
     * @return si se ha pasado la validación
     * @version 1.0
     * @since 1.0
     */
    public Boolean validate() {
        return this.valid;
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link String}
     * @param field Campo a validar
     * @return un constructor de tipo {@link StringFieldValidatorBuilder}	
     * @version 1.0
     * @since 1.0
     */
    public StringFieldValidatorBuilder field(String field) {
        return new StringFieldValidatorBuilder(this, field);
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link Integer}
     * @param field Campo a validar
     * @return un constructor de tipo {@link NumericFieldValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<Integer> field(Integer field) {
        return new NumericFieldValidationBuilder<Integer>(this, field);
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link Double}
     * @param field Campo a validar
     * @return un constructor de tipo {@link NumericFieldValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<Double> field(Double field) {
        return new NumericFieldValidationBuilder<Double>(this, field);
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link Long}
     * @param field Campo a validar
     * @return un constructor de tipo {@link NumericFieldValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<Long> field(Long field) {
        return new NumericFieldValidationBuilder<Long>(this, field);
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link Float}
     * @param field Campo a validar
     * @return un constructor de tipo {@link NumericFieldValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<Float> field(Float field) {
        return new NumericFieldValidationBuilder<Float>(this, field);
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link BigDecimal}
     * @param field Campo a validar
     * @return un constructor de tipo {@link NumericFieldValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public NumericFieldValidationBuilder<BigDecimal> field(BigDecimal field) {
        return new NumericFieldValidationBuilder<BigDecimal>(this, field);
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link Collection}
     * @param field Campo a validar
     * @return un constructor de tipo {@link CollectionFieldValidatorBuilder}
     * @version 1.0
     * @since 1.0
     */
    public CollectionFieldValidatorBuilder field(Collection<?> field) {
        return new CollectionFieldValidatorBuilder(this, field);
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link LocalDate}
     * @param field Campo a validar
     * @return un constructor de tipo {@link LocalDateValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public LocalDateValidationBuilder field(LocalDate field) {
        return new LocalDateValidationBuilder(this, field);
    }
    
    /**
     * Devuelve un nuevo constructor para validar un campo de tipo {@link LocalDateTime}
     * @param field Campo a validar
     * @return un constructor de tipo {@link LocalDateTimeValidationBuilder}
     * @version 1.0
     * @since 1.0
     */
    public LocalDateTimeValidationBuilder field(LocalDateTime field) {
        return new LocalDateTimeValidationBuilder(this, field);
    }

    /**
     * Devuelve un nuevo constructor para validar un campo de tipo general
     * @param field Campo a validar
     * @return un constructor de tipo {@link FieldValidatorBuilder}
     * @version 1.0
     * @since 1.0
     */
    public FieldValidatorBuilder field(Object field) {
        return new FieldValidatorBuilder(this, field);
    }
    
    // Métodos para volver de otros validadores ---------------------------------------------    
    public ValidatorBuilder and(StringFieldValidatorBuilder stringFieldValidator) {
        this.valid = stringFieldValidator.isValid();
        return this;
    }
    
    public ValidatorBuilder and(NumericFieldValidationBuilder<?> numericFieldValidator) {
        this.valid = numericFieldValidator.isValid();
        return this;
    }
    
    public ValidatorBuilder and(FieldValidatorBuilder fieldValidator) {
        this.valid = fieldValidator.isValid();
        return this;
    }
    
    public ValidatorBuilder and(CollectionFieldValidatorBuilder collectionFieldValidator) {
        this.valid = collectionFieldValidator.isValid();
        return this;
    }
    
    public ValidatorBuilder and(LocalDateTimeValidationBuilder localDateTimeValidator) {
        return this;
    }
    
    public ValidatorBuilder and(LocalDateValidationBuilder localDateValidator) {
        return this;
    }
    
    public Boolean isValid() {
        return this.valid;
    }

}
