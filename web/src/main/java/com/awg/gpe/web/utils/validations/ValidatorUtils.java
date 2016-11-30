package com.awg.gpe.web.utils.validations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.awg.gpe.web.exceptions.ValidationException;
import com.awg.gpe.web.utils.NumericUtils;

/**
 * Clase auxiliar para ayudar a validar formularios
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 * @deprecated A partir de ahora se deberá usar la clase {@link ValidatorBuilder}
 *
 */
@Deprecated
public class ValidatorUtils {

    public static final String REQUIRED = "required";
    public static final String EMAIL = "email";
    public static final String RANGE = "range";
    public static final String MIN_LENGTH = "minlength";
    public static final String MAX_LENGTH = "maxlength";

    private static final Pattern emailRegex = Pattern.compile(".+@.+\\.[a-z]+");

    /**
     * Método que construye una validación a partir del campo y una lista de validaciones a aplicar
     * 
     * @param field
     *            Objeto que se quiere validar
     * @param validations
     *            Número indeterminado de validaciones a pasar
     * @return Si el objeto ha pasado las distintas validaciones
     * @version 1.0
     * @throws ValidationException
     *             En el caso de que se produzca una excepción
     * @since 1.0
     */
    public static Boolean validate(Object field, String... validations) throws ValidationException {
        if (validations.length == 0) {
            throw new IllegalArgumentException("validation.error.illegalArguments");
        }
        List<String> validationNames = Arrays.asList(validations);
        if (validationNames.contains(RANGE)) {
            throw new ValidationException("validation.error.notAllowedValidation");
        }
        return ValidatorUtils.validate(field, Optional.empty(), Optional.empty(), validations);
    }

    /**
     * Método completo para construir una validación
     * 
     * @param field Objeto que se quiere validar
     * @param from [Opcional] En el caso de que se quiera validar un rango, límite inferior del mismo
     * @param to [Opcional] En el caso de que se quiera validar un rango, límite superior del mismo
     * @param validations Validaciones a pasar por el objeto
     * @return Si el objeto ha pasado todas las validaciones indicadas
     * @version 1.0
     * @throws ValidationException En el caso de que se produzca algún error
     * @since 1.0
     */
    public static Boolean validate(Object field, Optional<? extends Number> from, Optional<? extends Number> to, String... validations) throws ValidationException {
        Boolean res = true;

        if (validations.length != 0) {
            for (String validation : validations) {
                switch (validation) {
                    case ValidatorUtils.REQUIRED:
                        res = ValidatorUtils.required(field);
                        break;
                    case ValidatorUtils.RANGE:
                        if (field == null) {
                            res = false;
                        } else if (!from.isPresent() || !to.isPresent()) {
                            throw new ValidationException("validation.error.null.values");
                        } else {
                            res = ValidatorUtils.range(field, from.get(), to.get());
                        }
                        break;
                    case ValidatorUtils.EMAIL:
                        res = (field instanceof String) ? ValidatorUtils.isEmail((String) field) : false;
                        break;
                    case ValidatorUtils.MIN_LENGTH:
                        if (!from.isPresent()) {
                            throw new ValidationException("validation.error.null.values");
                        }
                        if (field == null) {
                            res = true;
                        } else {
                            res = (field instanceof String) ? ValidatorUtils.minLength((String) field, from.get().intValue()) : false;
                        }
                        break;
                    case ValidatorUtils.MAX_LENGTH:
                        if (!to.isPresent()) {
                            throw new ValidationException("validation.error.null.values");
                        }
                        if (field == null) {
                            res = true;
                        } else {
                            res = (field instanceof String) ? ValidatorUtils.maxLength((String) field, to.get().intValue()) : false;
                        }
                        break;
                    default:
                        break;
                }
                // En el momento en el que no pase una validación se sale del for
                if (!res) {
                    break;
                }
            }
        }

        return res;

    }

    /**
     * Método que devuelve si el campo requerido está informado
     * 
     * @param field Objeto a validar
     * @return Si el objeto está informado o no
     * @version 1.0
     * @since 1.0
     */
    public static Boolean required(Object field) {
        Boolean res = true;
        if (field == null) {
            res = false;
        } else if (field.getClass().equals(String.class)) {
            res = StringUtils.hasText((String) field);
        }
        return res;
    }

    /**
     * Método que sirve para comprobar si un String es un email
     * 
     * @param field Campo que se quiere validar
     * @return Si el campo tiene formato de email
     * @version 1.0
     * @since 1.0
     */
    public static Boolean isEmail(String field) {
        Matcher matcher = ValidatorUtils.emailRegex.matcher(field);
        return matcher.matches();
    }

    /**
     * Método que valida si una cadena de texto tiene una longitud dentro de un determinado rango o si un número (Integer, Long, o BigDecimal) está en un cierto rango
     * 
     * @param field Campo que se quiere validar
     * @param from Límite inferior del rango
     * @param to Límite superior del rango
     * @return Si el número proporcionado se encuentra dentro del rango
     * @version 1.0
     * @since 1.0
     */
    public static Boolean range(Object field, Number from, Number to) {
        Boolean res = true;

        if (field instanceof String) {
            res = NumericUtils.between(((String) field).length(), from.intValue(), to.intValue());
        } else if (field instanceof Integer) {
            res = NumericUtils.between((Integer) field, from.intValue(), to.intValue());
        } else if (field instanceof Long) {
            res = NumericUtils.between((Long) field, from.longValue(), to.longValue());
        } else if (field instanceof BigDecimal) {
            res = NumericUtils.between((BigDecimal) field, (BigDecimal) from, (BigDecimal) to);
        }
        return res;
    }

    public static Boolean minLength(String field, Integer from) {
        return field.length() >= from;
    }

    public static Boolean maxLength(String field, Integer to) {
        return field.length() <= to;
    }

}
