package com.awg.gpe.data.enums;

/**
 * Enumerado en el que se describen todos los tipos de roles posibles
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumRoles {

    ADMINISTRATOR(1L, "ADMINISTRATOR", "role.administrator"), 
    PROJECT_LEADER(2L, "PROJECT_LEADER", "role.project.leader"), 
    PROJECT_MANAGER(3L, "PROJECT_MANAGER", "role.project.manager"), 
    USER(4L, "USER", "role.user");

    EnumRoles(Long id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    // Attributes -------------------------------------------------------------

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
