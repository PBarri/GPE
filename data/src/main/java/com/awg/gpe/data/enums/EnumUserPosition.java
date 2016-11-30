package com.awg.gpe.data.enums;

/**
 * Enumerado en el que se describen las categorías profesionales posibles
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumUserPosition {

    PROJECT_MANAGER(1L, "PROJECT_MANAGER", "user.category.project.manager"), 
    ANALYST(2L, "ANALYST", "user.category.analyst"), 
    PROGRAMMER_ANALYST(3L, "PROGRAMMER_ANALYST", "user.category.programmer.analyst"), 
    SENIOR_PROGRAMMER(4L, "SENIOR_PROGRAMMER", "user.category.senior.programmer"), 
    JUNIOR_PROGRAMMER(5L, "JUNIOR_PROGRAMMER", "user.category.junior.programmer"), 
    INTERN(6L, "INTERN", "user.category.intern");

    EnumUserPosition(Long id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    private final Long id;

    private final String code;

    private final String description;

    // Getters ----------------------------------------------------------------

    /**
     * @return el atributo id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return el atributo codigo
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @return el atributo descripcion
     */
    public String getDescription() {
        return this.description;
    }

}
