package com.awg.gpe.data.enums;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumTaskType {

    // Scrum
    SCRUM_TASK(1L, "SCRUM", "task.type.scrum"),
    
    // Metrica V3    
    SYSTEM_PLANNING(2L, "SYSTEM_PLANNING", "task.type.system.planning"),
    SYSTEM_VIABILITY_REPORT(3L, "SYSTEM_VIABILITY_REPORT", "task.type.system.viability"),
    SYSTEM_ANALYSIS(4L, "SYSTEM_ANALYSIS", "task.type.system.analysis"),
    SYSTEM_DESIGN(5L, "SYSTEM_DESIGN", "task.type.system.design"),
    SYSTEM_CONSTRUCTION(6L, "SYSTEM_CONSTRUCTION", "task.type.system.construction"),
    SYSTEM_IMPLANTATION(7L, "SYSTEM_IMPLANTATION", "task.type.system.implantation"),
    INITIAL_PROJECT_ACTIVITY(8L, "INITIAL_PROJECT_ACTIVITY", "task.type.initial.project");
    
    EnumTaskType(Long id, String code, String description) {
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
