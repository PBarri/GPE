package com.awg.gpe.data.enums;

/**
 * Enumerado que contiene información sobre las tareas predeterminadas de un proyecto de Métrica V3
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumMetrica3 {
    
    // Planificación de sistemas de información
    PSI("-PSI", "metrica3.psi", true),
    PSI_1("-PSI-1", "metrica3.psi.start", true),
    PSI_2("-PSI-2", "metrica3.psi.organization", true),
    PSI_3("-PSI-3", "metrica3.psi.relevant.information.study", true),
    PSI_4("-PSI-4", "metrica3.psi.requisites.identifying", true),
    PSI_5("-PSI-5", "metrica3.psi.actual.is.study", true),
    PSI_6("-PSI-6", "metrica3.psi.model.design", true),
    PSI_7("-PSI-7", "metrica3.psi.architecture.definition", true),
    PSI_8("-PSI-8", "metrica3.psi.action.plan.definition", true),
    PSI_9("-PSI-9", "metrica3.psi.approval", true),
    // Estudio de viabilidad del sistema
    EVS("-EV", "metrica3.evs", true),
    EVS_1("-EVS-1", "metrica3.evs.system.scope", true),
    EVS_2("-EVS-2", "metrica3.evs.actual.situation.study", true),
    EVS_3("-EVS-3", "metrica3.evs.system.requisites.definition", true),
    EVS_4("-EVS-4", "metrica3.evs.alternative.solutions.study", true),
    EVS_5("-EVS-5", "metrica3.evs.alternatives.valoration", true),
    EVS_6("-EVS-6", "metrica3.evs.solution.selection", true),
    
    // Actividades inicio proyecto
    GPI("-GPI", "metrica3.gpi", true),
    GPI_1("-GPI-1", "metrica3.gpi.effortEstimation", true),
    GPI_2("-GPI-2", "metrica3.gpi.planning", true),
    
    // Análisis del sistema de información
    ASI("-ASI", "metrica3.asi", true),
    ASI_1("-ASI-1", "metrica3.asi.system.definition", true),
    ASI_2("-ASI-2", "metrica3.asi.requisites.establishment", true),
    ASI_3("-ASI-3", "metrica3.asi.subsystem.identifying", true),
    ASI_4("-ASI-4", "metrica3.asi.use.case.analysis", true),
    ASI_5("-ASI-5", "metrica3.asi.class.analysis", true),
    ASI_6("-ASI-6", "metrica3.asi.data.model.elaboration", true),
    ASI_7("-ASI-7", "metrica3.asi.proccess.model.elaboration", true),
    ASI_8("-ASI-8", "metrica3.asi.user.interface.definition", true),
    ASI_9("-ASI-9", "metrica3.asi.requisites.specification.analysis", true),
    ASI_10("-ASI-10", "metrica3.asi.test.planning.specification", true),
    ASI_11("-ASI-11", "metrica3.asi.is.analysis.approval", true),
    
    // Diseño del sistema de información
    DSI("-DSI", "metrica3.dsi", true),
    DSI_1("-DSI-1", "metrica3.dsi.system.architecture.definition", true),
    DSI_2("-DSI-2", "metrica3.dsi.support.architecture.design", true),
    DSI_3("-DSI-3", "metrica3.dsi.real.use.cases.design", true),
    DSI_4("-DSI-4", "metrica3.dsi.classes.design", true),
    DSI_5("-DSI-5", "metrica3.dsi.system.modules.architecture.design", true),
    DSI_6("-DSI-6", "metrica3.dsi.physical.data.design", true),
    DSI_7("-DSI-7", "metrica3.dsi.system.architecture.approval", true),
    DSI_8("-DSI-8", "metrica3.dsi.construction.specification.generation", true),
    DSI_9("-DSI-9", "metrica3.dsi.migration.design", true),
    DSI_10("-DSI-10", "metrica3.dsi.test.planning.technical.specification", true),
    DSI_11("-DSI-11", "metrica3.dsi.implantation.requisites.establishment", true),
    DSI_12("-DSI-12", "metrica3.dsi.is.design.approval", true),
    
    CSI("-CSI", "metrica3.csi", false),
    CSI_1("-CSI-1", "metrica3.csi.environment.preparation", false),
    CSI_2("-CSI-2", "metrica3.csi.code.generation", false),
    CSI_3("-CSI-3", "metrica3.csi.unit.tests", false),
    CSI_4("-CSI-4", "metrica3.csi.integration.tests", false),
    CSI_5("-CSI-5", "metrica3.csi.system.tests", false),
    CSI_6("-CSI-6", "metrica3.csi.user.manual.elaboration", false),
    CSI_7("-CSI-7", "metrica3.csi.final.users.training", false),
    CSI_8("-CSI-8", "metrica3.csi.migration.component.construction", false),
    CSI_9("-CSI-9", "metrica3.csi.is.approval", false),
    
    IAS("-IAS", "metrica3.ias", true),
    IAS_1("-IAS-1", "metrica3.ias.plan.establishment", true),
    IAS_2("-IAS-2", "metrica3.ias.neccesary.training", true),
    IAS_3("-IAS-3", "metrica3.ias.system.incorporation", true),
    IAS_4("-IAS-4", "metrica3.ias.data.loading", true),
    IAS_5("-IAS-5", "metrica3.ias.implantation.tests", true),
    IAS_6("-IAS-6", "metrica3.ias.acceptance.tests", true),
    IAS_7("-IAS-7", "metrica3.ias.maintenance.preparation", true),
    IAS_8("-IAS-8", "metrica3.ias.service.agreement.establishment", true),
    IAS_9("-IAS-9", "metrica3.ias.system.presentation", true),
    IAS_10("-IAS-10", "metrica3.ias.production.installation", true)
    ;

    EnumMetrica3(String codeSuffix, String description, Boolean management) {
        this.codeSuffix = codeSuffix;
        this.description = description;
        this.management = management;
    }

    // Attributes -------------------------------------------------------------

    private final String codeSuffix;

    private final String description;

    private final Boolean management;
    
    // Getters ----------------------------------------------------------------

    /**
     * @return el atributo codigo
     */
    public String getCodeSuffix() {
        return this.codeSuffix;
    }

    /**
     * @return el atributo descripcion
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return el atributo management
     */
    public Boolean getManagement() {
        return this.management;
    }
}
