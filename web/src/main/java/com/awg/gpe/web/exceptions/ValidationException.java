package com.awg.gpe.web.exceptions;

/**
 * Excepción que salta cuando existe un error relacionado con la validación
 * 
 * @author Pablo Barrientos
 * @versi@SuppressWarnings("serial") on 1.0
 * @since 1.0
 *
 */
public class ValidationException extends Exception {

    /**
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor vacío de la clase ValidationException.java
     * 
     * @since 1.0
     */
    public ValidationException() {
    }

    /**
     * Constructor vacío de la clase ValidationException.java
     * 
     * @param message - El mensaje que mostrará la excepción
     * @since 1.0
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor vacío de la clase ValidationException.java
     * 
     * @param cause - La causa que ha provocado esta excepción
     * @since 1.0
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor vacío de la clase ValidationException.java
     * 
     * @param message - El mensaje que mostrará la excepción
     * @param cause - La causa que ha provocado esta excepción
     * @since 1.0
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}