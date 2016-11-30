package com.awg.gpe.data.enums;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumMethodology {

    SCRUM(1L, "SCRUM", "methodology.scrum"), 
    METRICA_V3(2L, "METRICA_V3", "methodology.metricav3");

    EnumMethodology(Long id, String code, String description) {
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
