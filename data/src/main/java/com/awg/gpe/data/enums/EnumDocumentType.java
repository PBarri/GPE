package com.awg.gpe.data.enums;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumDocumentType {

    // Documentos posibles de Metrica v3
    
    // Actividades de inicio de proyecto
    PROJECT_GENERAL_INFORMATION(1L, "PROJECT_GENERAL_INFORMATION", "document.type.project.general.information"),
    PROJECT_GENERAL_PLANNING(2L, "PROJECT_GENERAL_PLANNING", "document.type.project.general.planning"),    

    // Plan de Sistemas de Información
    PSI_GENERAL_DESCRIPTION(3L, "PSI_GENERAL_DESCRIPTION", "document.type.psi.general.description"), 
    ORGANIZATION_AFFECTED_PROCESSES(4L, "PSI2", "document.type.organization.affected.processes"), 
    PSI_GOALS_CATALOG(5L, "PSI_GOALS_CATALOG", "document.type.psi.goals.catalog"), 
    WORK_TEAMS(6L, "WORK_TEAMS", "document.type.work.teams"), 
    WORK_PLAN(7L, "WORK_PLAN", "document.type.work.plans"), 
    BACKGROUND_VALORATION(8L, "BACKGROUND_VALORATION", "document.type.background.valoration"), 
    REQUISITES_CATALOG(9L, "REQUISITES_CATALOG", "document.type.requisites.catalog"), 
    GENERAL_REQUISITES(10L, "GENERAL_REQUISITES", "document.type.requisites.general"), 
    PSI_RULES_CATALOG(11L, "PSI_RULES_CATALOG", "document.type.psi.rules.catalog"), 
    ORGANIZATION_PROCESSES_MODEL(12L, "ORGANIZATION_PROCESSES_MODEL", "document.type.organization.processes"), 
    INFORMATION_NECESSITIES(13L, "INFORMATION_NECESSITIES",  "document.type.information.necessities"), 
    INFORMATION_MODEL(14L, "INFORMATION_MODEL", "document.type.information.model"), 
    ACTUAL_IS_DESCRIPTION(15L, "ACTUAL_IS_DESCRIPTION", "document.type.actual.is.description"), 
    ACTUAL_SITUATION_VALORATION(16L, "ACTUAL_SITUATION_VALORATION", "document.type.actual.valoration");
    
    // Estudio Viabilidad Sistema
    

    EnumDocumentType(Long id, String code, String description) {
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