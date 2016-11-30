package com.awg.gpe.data.enums;

/**
 * Enumerado en el que se describen todos los tipos de servidores posibles
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */

public enum EnumServerType {

    APPLICATION_SERVER(1L, "APPLICATION_SERVER", "server.type.application"), 
    DATABASE_SERVER(2L, "DATABASE_SERVER", "server.type.database"), 
    FTP_SERVER(3L, "FTP_SERVER", "server.type.ftp");

    EnumServerType(Long id, String code, String description) {
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
