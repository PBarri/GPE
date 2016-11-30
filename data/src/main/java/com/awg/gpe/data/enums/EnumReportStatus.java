package com.awg.gpe.data.enums;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumReportStatus {

    PENDING(1L, "PENDING", "report.status.pending"), 
    CORRECT(2L, "CORRECT", "report.status.correct"), 
    FAILED(3L, "FAILED", "report.status.failed");

    EnumReportStatus(Long id, String code, String description) {
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
