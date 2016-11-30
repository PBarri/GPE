package com.awg.gpe.data.exceptions;

/**
 * Excepción que será lanzada por todos los métodos de los servicios en el caso de que ocurra una excepción
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class ServiceException extends Exception {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor vacío de la clase ServiceException.java
     * 
     * @since 1.0
     */
    public ServiceException() {
    }

    /**
     * Constructor que acepta un mensaje de la clase ServiceException.java
     * 
     * @param message
     *            Mensaje a mostrar en la excepción
     * @since 1.0
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta un objeto Throwable de la clase ServiceException.java
     * 
     * @param cause
     *            {@link Throwable} causante de la excepción
     * @since 1.0
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor que acepta un mensaje y un objeto Throwable de la clase ServiceException.java
     * 
     * @param message
     *            Mensaje a mostrar en la excepción
     * @param cause
     *            {@link Throwable} causante de la excepción
     * @since 1.0
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
