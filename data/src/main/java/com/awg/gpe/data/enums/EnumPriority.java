package com.awg.gpe.data.enums;

/**
 * Enumerado en el que se describen las prioridades de los objetivos
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumPriority {

    CRITICAL(1, "CRITICAL", "priority.critical"), 
    IMPORTANT(2, "IMPORTANT", "priority.important"), 
    NORMAL(3, "NORMAL", "priority.normal"), 
    LOW(4, "LOW", "priority.low");

    /**
     * Constructor vacío de la clase EnumPriority.java
     * 
     * @since 1.0
     */
    EnumPriority(Integer priority, String code, String description) {
        this.priority = priority;
        this.code = code;
        this.description = description;
    }

    // Attributes -------------------------------------------------------------

    private final Integer priority;

    private final String code;

    private final String description;

    // Getters ----------------------------------------------------------------
    /**
     * @return el atributo priority
     */
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * @return el atributo code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @return el atributo description
     */
    public String getDescription() {
        return this.description;
    }

}
