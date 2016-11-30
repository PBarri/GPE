package com.awg.gpe.data.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.awg.gpe.data.enums.EnumMetrica3;
import com.awg.gpe.data.enums.EnumPriority;
import com.awg.gpe.data.enums.EnumTaskStatus;
import com.awg.gpe.data.enums.EnumTaskType;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMTasksFilters;
import com.awg.gpe.data.model.QTGpeMTask;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMRequirement;
import com.awg.gpe.data.model.TGpeMTask;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpePPriority;
import com.awg.gpe.data.model.TGpePTaskStatus;
import com.awg.gpe.data.model.TGpePTaskType;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMTask;
import com.awg.gpe.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * Servicio de la entidad {@link TGpeMTask}
 * 
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMTask extends BaseService<TGpeMTask, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMTask repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMTask, Long> getRepository() {
        return this.repository;
    }

    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
        QTGpeMTask task = QTGpeMTask.tGpeMTask;
        BooleanBuilder predicate = new BooleanBuilder();

        filters.forEach((key, value) -> {
            switch (key) {
                case TGpeMTasksFilters.FILTER_CODE:
                    predicate.and(task.code.eq((String) value));
                    break;
                case TGpeMTasksFilters.FILTER_NAME:
                    predicate.and(task.name.eq((String) value));
                    break;
                case TGpeMTasksFilters.FILTER_PROJECT:
                    predicate.and(task.requirement.project.eq((TGpeMProject) value));
                    break;
                case TGpeMTasksFilters.FILTER_REQUIREMENT:
                    predicate.and(task.requirement.eq((TGpeMRequirement) value));
                    break;
                case TGpeMTasksFilters.FILTER_TASK_STATUS:
                    predicate.and(task.taskStatus.code.eq(((EnumTaskStatus) value).getCode()));
                    break;
                case TGpeMTasksFilters.FILTER_TASK_PRIORITY:
                    predicate.and(task.taskPriority.code.eq(((EnumPriority) value).getCode()));
                    break;
                case TGpeMTasksFilters.FILTER_TASK_TYPE:
                    predicate.and(task.taskType.code.eq(((EnumTaskType) value).getCode()));
                    break;
                case TGpeMTasksFilters.FILTER_DEVELOPER:
                    predicate.and(task.usersAssigned.contains((TGpeMUser) value));
                    break;
                case TGpeMTasksFilters.FILTER_START_DATE:
                    predicate.and(task.startDate.after((LocalDateTime) value));
                    break;
                case TGpeMTasksFilters.FILTER_END_DATE:
                    predicate.and(task.endDate.before((LocalDateTime) value));
                    break;
                case TGpeMTasksFilters.FILTER_USER:
                	TGpeMUser user = (TGpeMUser) value;
                	/*if (user.isProjectLeader()) {
                        predicate.and(task.requirement.project.leader.eq(user));
                    } else if (user.isProjectManager()) {
                    	predicate.and(task.requirement.project.managers.contains(user));
                    }*/
                	break;
                case TGpeMTasksFilters.FILTER_PARENT_TASK:
                	if (value != null) {
                		predicate.and(task.parent.eq((TGpeMTask) value));
                	}
                	break;
                default:
                    break;
            }
        });
        return predicate;
    }
    
    // Custom methods ------------------------------------------------
	/**
     * Método que crea las tareas iniciales para la metodología Métrica V3
     * @return La lista de tareas
     * @version 1.0
     * @since 1.0
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<TGpeMTask> newMetricaProjectTasks(String prefix) {
        LocalDateTime timestamp = LocalDateTime.now();
        List<TGpeMTask> res = new ArrayList<>();
        
        // Se crean las tareas principales
        TGpeMTask psi = createPSITask(prefix, timestamp);
        TGpeMTask evs = createEVSTask(prefix, timestamp);
        TGpeMTask gpi = createGPITask(prefix, timestamp);
        TGpeMTask asi = createASITask(prefix, timestamp);
        TGpeMTask dsi = createDSITask(prefix, timestamp);
        TGpeMTask csi = createCSITask(prefix, timestamp);
        TGpeMTask ias = createIASTask(prefix, timestamp);
        
        // Se crean las dependencias entre las tareas principales
        evs.addDependency(psi);
        gpi.addDependency(evs);
        asi.addDependency(gpi);
        dsi.addDependency(asi);
        csi.addDependency(dsi);
        ias.addDependency(csi);
        
        // Se rellena el resultado
        res.add(psi);
        res.add(evs);
        res.add(gpi);
        res.add(asi);
        res.add(dsi);
        res.add(csi);
        res.add(ias);
        
        return res;
    }
    
    /**
     * Método que crea las tareas iniciales para la metodología Scrum
     * <p>
     * Estas tareas serán todas las reuniones planificadas de Scrum
     * </p>
     * @return La lista de tareas
     * @version 1.0
     * @since 1.0
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<TGpeMTask> newScrumProjectTasks(String prefix, LocalDate startDate, LocalDate endDate) {
        LocalDateTime timestamp = LocalDateTime.now();
        List<TGpeMTask> res = new ArrayList<>();
        
        List<LocalDate> businessDays = DateUtils.getWorkingDays(startDate, endDate);
        
        TGpeMTask sprintPlanning = createSprintPlanningTask(prefix, timestamp, startDate);
        res.add(sprintPlanning);
        
        businessDays.stream().filter(date -> !date.isEqual(startDate) && !date.isEqual(endDate))
        	.forEach(date -> {
        		TGpeMTask task = createDailyScrumTask(prefix, timestamp, date);
        		res.add(task);
        	});
        
        TGpeMTask sprintReview = createSprintReviewTask(prefix, timestamp, endDate);
        res.add(sprintReview);
        
        TGpeMTask sprintRetrospective = createSprintRetrospectiveTask(prefix, timestamp, endDate);
        res.add(sprintRetrospective);
        
        return res;
    }
    
	/**
     * Método que crea las principales tareas hijas de la planificación del sistema de información
     * @param prefix Prefijo para el código de las tareas
     * @param timestamp Fecha de creación
     * @return La tarea PSI
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createPSITask(String prefix, LocalDateTime timestamp) {
        // Se crean las tareas
        TGpeMTask psi = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi1 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_1, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi2 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_2, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi3 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_3, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi4 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_4, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi5 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_5, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi6 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_6, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi7 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_7, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi8 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_8, EnumTaskType.SYSTEM_PLANNING);
        TGpeMTask psi9 = createMetrica3Task(prefix, timestamp, EnumMetrica3.PSI_9, EnumTaskType.SYSTEM_PLANNING);
        
        // Se crean las dependencias entre las tareas
        psi2.addDependency(psi1);
        psi3.addDependency(psi2);
        psi4.addDependency(psi2);
        psi5.addDependency(psi2);
        psi6.addDependency(psi3);
        psi6.addDependency(psi4);
        psi6.addDependency(psi5);
        psi7.addDependency(psi6);
        psi8.addDependency(psi7);
        psi9.addDependency(psi8);
        
        // Se añaden las tareas hijas al PSI
        psi.addChild(psi1);
        psi.addChild(psi2);
        psi.addChild(psi3);
        psi.addChild(psi4);
        psi.addChild(psi5);
        psi.addChild(psi6);
        psi.addChild(psi7);
        psi.addChild(psi8);
        psi.addChild(psi9);
        
        return psi;
    }
    
    /**
     * Método que crea las principales tareas hijas del estudio de viabilidad del sistema
     * @param prefix Prefijo para el código de las tareas
     * @param timestamp Fecha de creación
     * @return La tarea PSI
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createEVSTask(String prefix, LocalDateTime timestamp) {
        // Se crean las tareas
        TGpeMTask evs = createMetrica3Task(prefix, timestamp, EnumMetrica3.EVS, EnumTaskType.SYSTEM_VIABILITY_REPORT);
        TGpeMTask evs1 = createMetrica3Task(prefix, timestamp, EnumMetrica3.EVS_1, EnumTaskType.SYSTEM_VIABILITY_REPORT);
        TGpeMTask evs2 = createMetrica3Task(prefix, timestamp, EnumMetrica3.EVS_2, EnumTaskType.SYSTEM_VIABILITY_REPORT);
        TGpeMTask evs3 = createMetrica3Task(prefix, timestamp, EnumMetrica3.EVS_3, EnumTaskType.SYSTEM_VIABILITY_REPORT);
        TGpeMTask evs4 = createMetrica3Task(prefix, timestamp, EnumMetrica3.EVS_4, EnumTaskType.SYSTEM_VIABILITY_REPORT);
        TGpeMTask evs5 = createMetrica3Task(prefix, timestamp, EnumMetrica3.EVS_5, EnumTaskType.SYSTEM_VIABILITY_REPORT);
        TGpeMTask evs6 = createMetrica3Task(prefix, timestamp, EnumMetrica3.EVS_6, EnumTaskType.SYSTEM_VIABILITY_REPORT);
        
        // Se crean las dependencias entre las tareas
        evs2.addDependency(evs1);
        evs3.addDependency(evs1);
        evs4.addDependency(evs2);
        evs4.addDependency(evs3);
        evs5.addDependency(evs4);
        evs6.addDependency(evs5);
        
        // Se añaden las tareas hijas al PSI
        evs.addChild(evs1);
        evs.addChild(evs2);
        evs.addChild(evs3);
        evs.addChild(evs4);
        evs.addChild(evs5);
        evs.addChild(evs6);

        return evs;
    }

    /**
     * Método que crea las principales tareas hijas de las actividades de inicio de proyecto
     * @param prefix Prefijo para el código de las tareas
     * @param timestamp Fecha de creación
     * @return La tarea PSI
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createGPITask(String prefix, LocalDateTime timestamp) {
        // Se crean las tareas
        TGpeMTask gpi = createMetrica3Task(prefix, timestamp, EnumMetrica3.GPI, EnumTaskType.INITIAL_PROJECT_ACTIVITY);
        TGpeMTask gpi1 = createMetrica3Task(prefix, timestamp, EnumMetrica3.GPI_1, EnumTaskType.INITIAL_PROJECT_ACTIVITY);
        TGpeMTask gpi2 = createMetrica3Task(prefix, timestamp, EnumMetrica3.GPI_2, EnumTaskType.INITIAL_PROJECT_ACTIVITY);
        
        // Se crean las dependencias entre las tareas
        gpi2.addDependency(gpi1);
        
        // Se añaden las tareas hijas al PSI
        gpi.addChild(gpi1);
        gpi.addChild(gpi2);
        
        return gpi;
    }
    
    /**
     * Método que crea las principales tareas hijas de la planificación del sistema de información
     * @param prefix Prefijo para el código de las tareas
     * @param timestamp Fecha de creación
     * @return La tarea PSI
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createASITask(String prefix, LocalDateTime timestamp) {
        // Se crean las tareas
        TGpeMTask asi = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI, EnumTaskType.SYSTEM_ANALYSIS);
        TGpeMTask asi1 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_1, EnumTaskType.SYSTEM_ANALYSIS);
        TGpeMTask asi2 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_2, EnumTaskType.SYSTEM_ANALYSIS);
        TGpeMTask asi3 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_3, EnumTaskType.SYSTEM_ANALYSIS);
        TGpeMTask asi4 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_4, EnumTaskType.SYSTEM_ANALYSIS);
        TGpeMTask asi5 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_5, EnumTaskType.SYSTEM_ANALYSIS);
        // Se eliminan las tareas ASI6 y ASI7 porque no contemplamos desarrollos estructurados
        //TGpeMTask asi6 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_6);
        //TGpeMTask asi7 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_7);
        TGpeMTask asi8 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_8, EnumTaskType.SYSTEM_ANALYSIS);
        TGpeMTask asi9 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_9, EnumTaskType.SYSTEM_ANALYSIS);
        TGpeMTask asi10 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_10, EnumTaskType.SYSTEM_ANALYSIS);
        TGpeMTask asi11 = createMetrica3Task(prefix, timestamp, EnumMetrica3.ASI_11, EnumTaskType.SYSTEM_ANALYSIS);
        
        // Se crean las dependencias entre las tareas
        asi2.addDependency(asi1);
        asi3.addDependency(asi1);
        asi4.addDependency(asi1);
        asi5.addDependency(asi1);
        asi8.addDependency(asi1);
        asi9.addDependency(asi2);
        asi9.addDependency(asi3);
        asi9.addDependency(asi4);
        asi9.addDependency(asi5);
        asi9.addDependency(asi8);
        asi10.addDependency(asi9);
        asi11.addDependency(asi10);
        
        // Se añaden las tareas hijas al PSI
        asi.addChild(asi1);
        asi.addChild(asi2);
        asi.addChild(asi3);
        asi.addChild(asi4);
        asi.addChild(asi5);
        asi.addChild(asi8);
        asi.addChild(asi9);
        asi.addChild(asi10);
        asi.addChild(asi11);
        
        return asi;
    }
    
    /**
     * Método que crea las principales tareas hijas del diseño del sistema de información
     * @param prefix Prefijo para el código de las tareas
     * @param timestamp Fecha de creación
     * @return La tarea PSI
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createDSITask(String prefix, LocalDateTime timestamp) {
        // Se crean las tareas
        TGpeMTask dsi = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi1 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_1, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi2 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_2, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi3 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_3, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi4 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_4, EnumTaskType.SYSTEM_DESIGN);
        // Se elimina la tarea DSI 5 porque no contemplamos desarrollos estructurados 
        //TGpeMTask dsi5 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_5);
        TGpeMTask dsi6 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_6, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi7 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_7, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi8 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_8, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi9 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_9, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi10 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_10, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi11 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_11, EnumTaskType.SYSTEM_DESIGN);
        TGpeMTask dsi12 = createMetrica3Task(prefix, timestamp, EnumMetrica3.DSI_12, EnumTaskType.SYSTEM_DESIGN);
        
        // Se crean las dependencias entre las tareas
        dsi7.addDependency(dsi1);
        dsi7.addDependency(dsi2);
        dsi7.addDependency(dsi3);
        dsi7.addDependency(dsi4);
        dsi7.addDependency(dsi6);
        dsi8.addDependency(dsi7);
        dsi9.addDependency(dsi7);
        dsi10.addDependency(dsi7);
        dsi11.addDependency(dsi7);
        dsi12.addDependency(dsi8);
        dsi12.addDependency(dsi9);
        dsi12.addDependency(dsi10);
        dsi12.addDependency(dsi11);
        
        // Se añaden las tareas hijas al PSI
        dsi.addChild(dsi1);
        dsi.addChild(dsi2);
        dsi.addChild(dsi3);
        dsi.addChild(dsi4);
        dsi.addChild(dsi6);
        dsi.addChild(dsi7);
        dsi.addChild(dsi8);
        dsi.addChild(dsi9);
        dsi.addChild(dsi10);
        dsi.addChild(dsi11);
        dsi.addChild(dsi12);
        
        return dsi;
    }
    
    /**
     * Método que crea las principales tareas hijas de la construcción del sistema de información
     * @param prefix Prefijo para el código de las tareas
     * @param timestamp Fecha de creación
     * @return La tarea PSI
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createCSITask(String prefix, LocalDateTime timestamp) {
        // Se crean las tareas
        TGpeMTask csi = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi1 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_1, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi2 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_2, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi3 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_3, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi4 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_4, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi5 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_5, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi6 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_6, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi7 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_7, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi8 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_8, EnumTaskType.SYSTEM_CONSTRUCTION);
        TGpeMTask csi9 = createMetrica3Task(prefix, timestamp, EnumMetrica3.CSI_9, EnumTaskType.SYSTEM_CONSTRUCTION);
        
        // Se crean las dependencias entre las tareas
        csi2.addDependency(csi1);
        csi3.addDependency(csi1);
        csi4.addDependency(csi1);
        csi6.addDependency(csi1);
        csi7.addDependency(csi1);
        csi8.addDependency(csi1);
        csi5.addDependency(csi2);
        csi5.addDependency(csi3);
        csi5.addDependency(csi4);
        csi9.addDependency(csi5);
        csi9.addDependency(csi6);
        csi9.addDependency(csi7);
        csi9.addDependency(csi8);
        
        // Se añaden las tareas hijas al PSI
        csi.addChild(csi1);
        csi.addChild(csi2);
        csi.addChild(csi3);
        csi.addChild(csi4);
        csi.addChild(csi5);
        csi.addChild(csi6);
        csi.addChild(csi7);
        csi.addChild(csi8);
        csi.addChild(csi9);
        
        return csi;
    }
    
    /**
     * Método que crea las principales tareas hijas de la planificación del sistema de información
     * @param prefix Prefijo para el código de las tareas
     * @param timestamp Fecha de creación
     * @return La tarea PSI
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createIASTask(String prefix, LocalDateTime timestamp) {
        // Se crean las tareas
        TGpeMTask ias = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias1 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_1, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias2 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_2, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias3 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_3, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias4 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_4, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias5 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_5, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias6 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_6, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias7 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_7, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias8 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_8, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias9 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_9, EnumTaskType.SYSTEM_IMPLANTATION);
        TGpeMTask ias10 = createMetrica3Task(prefix, timestamp, EnumMetrica3.IAS_10, EnumTaskType.SYSTEM_IMPLANTATION);
        
        // Se crean las dependencias entre las tareas
        ias2.addDependency(ias1);
        ias8.addDependency(ias1);
        ias3.addDependency(ias2);
        ias4.addDependency(ias2);
        ias7.addDependency(ias2);
        ias5.addDependency(ias3);
        ias5.addDependency(ias4);
        ias6.addDependency(ias5);
        ias9.addDependency(ias6);
        ias9.addDependency(ias7);
        ias9.addDependency(ias8);
        ias10.addDependency(ias9);
        
        // Se añaden las tareas hijas al PSI
        ias.addChild(ias1);
        ias.addChild(ias2);
        ias.addChild(ias3);
        ias.addChild(ias4);
        ias.addChild(ias5);
        ias.addChild(ias6);
        ias.addChild(ias7);
        ias.addChild(ias8);
        ias.addChild(ias9);
        ias.addChild(ias10);
        
        return ias;
    }
    
    /**
     * Método que crea una de las tareas predefinidas de la metodología Métrica V3
     * @param prefix Prefijo para el código de la tarea
     * @param timestamp Fecha en la que se ha ordenado la creación de las tareas
     * @param metrica3 Indicación de la tarea a crear
     * @return La tarea
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createMetrica3Task(String prefix, LocalDateTime timestamp, EnumMetrica3 metrica3, EnumTaskType taskType) {
        TGpeMTask task = new TGpeMTask();
        task.setTimestamp(timestamp);
        task.setCode(prefix + metrica3.getCodeSuffix());
        String description = getMessage(metrica3.getDescription());
        task.setDescription(description);
        String name = (description.length() <= 50) ? description : description.substring(0, 50);
        task.setName(name);
        task.setManagement(metrica3.getManagement());
        
        task.setTaskStatus(new TGpePTaskStatus(EnumTaskStatus.PENDING));
        task.setTaskType(new TGpePTaskType(taskType));
        task.setTaskPriority(new TGpePPriority(EnumPriority.NORMAL));
        
        TGpeMUser user = LoginService.getPrincipal();
        task.setCreatedBy(user);
        task.setLastEditionBy(user);
        
        /* Para pruebas:
        task.setHours(5f);
        task.setStartDate(LocalDateTime.now());
        task.setEndDate(LocalDateTime.now().plusDays(30));
        // Fin de las pruebas */
        
        return task;
    }
    
    /**
     * Método que crea una de las tareas predefinidas de la metodología Scrum
     * @param prefix Prefijo para el código de la tarea
     * @param timestamp Fecha en la que se ha ordenado la creación de las tareas
     * @return La tarea
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createDailyScrumTask(String prefix, LocalDateTime timestamp, LocalDate date) {
        TGpeMTask task = new TGpeMTask();
        task.setTimestamp(timestamp);
        task.setCode(prefix + "DS" + date.getDayOfMonth());
        task.setDescription(getMessage("scrum.daily"));
        
        task.setTaskStatus(new TGpePTaskStatus(EnumTaskStatus.MEETING));
        task.setTaskType(new TGpePTaskType(EnumTaskType.SCRUM_TASK));
        
        TGpeMUser user = LoginService.getPrincipal();
        task.setCreatedBy(user);
        task.setLastEditionBy(user);
        
        task.setHours(0.25F);
        task.setStartDate(date.atTime(LocalTime.of(8, 30)));
        task.setEndDate(date.atTime(LocalTime.of(8, 45)));
        
        return task;
    }
    
    /**
     * Método que crea una de las tareas predefinidas de la metodología Scrum
     * @param prefix Prefijo para el código de la tarea
     * @param timestamp Fecha en la que se ha ordenado la creación de las tareas
     * @return La tarea
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createSprintPlanningTask(String prefix, LocalDateTime timestamp, LocalDate date) {
        TGpeMTask task = new TGpeMTask();
        task.setTimestamp(timestamp);
        task.setCode(prefix + "SP");
        task.setDescription(getMessage("scrum.sprint.planning"));
        
        task.setTaskStatus(new TGpePTaskStatus(EnumTaskStatus.MEETING));
        task.setTaskType(new TGpePTaskType(EnumTaskType.SCRUM_TASK));
        
        TGpeMUser user = LoginService.getPrincipal();
        task.setCreatedBy(user);
        task.setLastEditionBy(user);
        
        task.setHours(7F);
        task.setStartDate(date.atTime(LocalTime.of(8, 0)));
        task.setEndDate(date.atTime(LocalTime.of(15, 0)));
        
        return task;
    }
    
    /**
     * Método que crea una de las tareas predefinidas de la metodología Scrum
     * @param prefix Prefijo para el código de la tarea
     * @param timestamp Fecha en la que se ha ordenado la creación de las tareas
     * @return La tarea
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createSprintReviewTask(String prefix, LocalDateTime timestamp, LocalDate date) {
        TGpeMTask task = new TGpeMTask();
        task.setTimestamp(timestamp);
        task.setCode(prefix + "SR");
        task.setDescription(getMessage("scrum.sprint.review"));
        
        task.setTaskStatus(new TGpePTaskStatus(EnumTaskStatus.MEETING));
        task.setTaskType(new TGpePTaskType(EnumTaskType.SCRUM_TASK));
        
        TGpeMUser user = LoginService.getPrincipal();
        task.setCreatedBy(user);
        task.setLastEditionBy(user);
        
        task.setHours(7F);
        task.setStartDate(date.atTime(LocalTime.of(8, 0)));
        task.setEndDate(date.atTime(LocalTime.of(12, 0)));
        
        return task;
    }
    
    /**
     * Método que crea una de las tareas predefinidas de la metodología Scrum
     * @param prefix Prefijo para el código de la tarea
     * @param timestamp Fecha en la que se ha ordenado la creación de las tareas
     * @return La tarea
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask createSprintRetrospectiveTask(String prefix, LocalDateTime timestamp, LocalDate date) {
        TGpeMTask task = new TGpeMTask();
        task.setTimestamp(timestamp);
        task.setCode(prefix + "SRT");
        task.setDescription(getMessage("scrum.sprint.retrospective"));
        
        task.setTaskStatus(new TGpePTaskStatus(EnumTaskStatus.MEETING));
        task.setTaskType(new TGpePTaskType(EnumTaskType.SCRUM_TASK));
        
        TGpeMUser user = LoginService.getPrincipal();
        task.setCreatedBy(user);
        task.setLastEditionBy(user);
        
        task.setHours(7F);
        task.setStartDate(date.atTime(LocalTime.of(12, 0)));
        task.setEndDate(date.atTime(LocalTime.of(15, 0)));
        
        return task;
    }

    /**
     * Método que devuelve la tarea con todas las colecciones de datos inicializadas
     * 
     * @param task La tarea de la que se quieren obtener todos los detalles
     * @return La tarea con los detalles
     * @throws ServiceException En el caso de que se produzca una excepción
     * @version 1.0
     * @since 1.0
     */
    public TGpeMTask findCompleteTask(TGpeMTask task) throws ServiceException {
        TGpeMTask res = this.repository.findOne(task.getId());
        
        Hibernate.initialize(res.getChilds());
        Hibernate.initialize(res.getComments());
        Hibernate.initialize(res.getDependencies());
        Hibernate.initialize(res.getIncurreds());
        Hibernate.initialize(res.getUsersAssigned());
        
        res.getUsersAssigned().forEach(u -> Hibernate.initialize(u.getTasksAssigned()));
        res.getChilds().forEach(t -> findChildTask(t));
        
        return res;
    }
    
    public TGpeMTask findChildTask(TGpeMTask task) {
    	TGpeMTask res = this.repository.findOne(task.getId());
    	
    	Hibernate.initialize(task.getChilds());
    	Hibernate.initialize(res.getUsersAssigned());
    	
    	res.getUsersAssigned().forEach(u -> Hibernate.initialize(u.getTasksAssigned()));
        res.getChilds().forEach(t -> findChildTask(t));
    	
    	return res;
    }
    
    @Transactional
    public List<TGpeMTask> findTasksForUpdate() throws ServiceException {
    	QTGpeMTask task = QTGpeMTask.tGpeMTask;
    	List<TGpePTaskStatus> finishedStatus = EnumTaskStatus.getActiveStatus();
    	BooleanBuilder predicate = new BooleanBuilder();
    	predicate.and(task.taskStatus.in(finishedStatus));
    	List<TGpeMTask> tasks = this.repository.findAll(predicate);
    	
    	tasks.forEach(t -> {
    		Hibernate.initialize(t.getUsersAssigned());
    		Hibernate.initialize(t.getDependencies());
    	});
    	
    	return tasks;
    }
    
    @Transactional
    public Long countTasksForUpdate() throws ServiceException {
    	QTGpeMTask task = QTGpeMTask.tGpeMTask;
    	List<TGpePTaskStatus> finishedStatus = EnumTaskStatus.getActiveStatus();
    	BooleanBuilder predicate = new BooleanBuilder();
    	predicate.and(task.taskStatus.in(finishedStatus));
    	return this.repository.count(predicate);
    }
    
    /**
     * Método que actualiza el estado de una tarea. El funcionamiento es el siguiente:
     * <ul>
     * <li>
     * Si la tarea está pendiente:
     * <ul>
     * <li>Si no tiene usuarios asignados
     * <ul>
     * <li>Comprobamos si ya ha pasado la fecha de inicio de la tarea, en cuyo caso actualizamos el estado a parado</li>
     * </ul>
     * <li>Si tiene usuarios asignados
     * <ul>
     * <li>Si no ha pasado la fecha de inicio de la tarea, la marcamos como programada</li>
     * <li>Si ha pasado la fecha de inicio de la tarea
     * <ul>
     * <li>Si no hay ninguna tarea que la bloquee, comprobamos si ha pasado la fecha de fin, y la marcamos como empezada o retrasada, dependiendo de la fecha de finalización</li>
     * <li>Si hay alguna tarea que la bloquee, la ponemos como bloqueada</li>
     * </ul>
     * </li>
     * </ul>
     * </li>
     * </ul>
     * </li>
     * <li>
     * Si la tarea está programada:
     * <ul>
     * <li>Si no tiene usuarios asignados (porque se hayan eliminado usuarios asignados a dicha tarea):
     * <ul>
     * <li>Si ya ha comenzado la tarea, se marca como parada</li>
     * <li>Si no ha comenzado la tarea, se marca como pendiente</li>
     * </ul>
     * </li>
     * <li>Si tiene usuarios asignados, y ha pasado la fecha de comienzo de la tarea:
     * <ul>
     * <li>Si hay alguna tarea que la bloquee, se marca como bloqueada</li>
     * <li>Si no hay ninguna tarea que la bloquee, se marca como empezada (o retrasada en el caso de que lo esté)</li>
     * </ul>
     * </li>
     * </ul>
     * </li>
     * <li>
     * Si la tarea está está empezada:
     * <ul>
     * <li>Si no tiene usuarios asignados (porque se hayan eliminado usuarios asignados a dicha tarea):
     * <ul>
     * <li>Si ya ha comenzado la tarea, se marca como parada</li>
     * <li>Si no ha comenzado la tarea, se marca como pendiente</li>
     * </ul>
     * </li>
     * <li>Si tiene usuarios asignados, se comprueba si la tarea ha cumplido el plazo, en cuyo caso se marca como retrasada.</li>
     * </ul>
     * </li>
     * <li>
     * Si la tarea está bloqueada:
     * <ul>
     * <li>Si han desaparecido los bloqueos, se marca como empezada</li>
     * </ul>
     * </li>
     * </ul>
     * 
     * @param task Tarea que se quiere actualizar
     * @return La tarea actualizada
     * @throws ServiceException En el caso de que exista algún error
     * @version 1.0
     * @since 1.0
     */
    @Transactional
    public TGpeMTask updateTask(TGpeMTask task) throws ServiceException {
		updateTaskStatus(task);		
		return saveAndFlush(task);		
	}
    
    private void updateTaskStatus(TGpeMTask task) {    	
    	if (task.getTaskStatus().equals(EnumTaskStatus.PENDING)) {			
			if (task.getUsersAssigned().isEmpty()) {
				if (task.getStartDate().isBefore(LocalDateTime.now())) {
					// comprobamos si ha llegado la fecha de inicio y no se ha asignado a nadie a dicha tarea
					task.setTaskStatus(EnumTaskStatus.STOPPED);
				}
			} else {
				// comprobamos si hay alguna tarea que la bloquee o la ponemos como empezada
				if (task.getStartDate().isBefore(LocalDateTime.now())) {
					if (!isBlocked(task)) {
						if(task.getEndDate().isBefore(LocalDateTime.now())) {
							task.setTaskStatus(EnumTaskStatus.DELAYED);
						} else {
							task.setTaskStatus(EnumTaskStatus.STARTED);
						}						
					} else {
						task.setTaskStatus(EnumTaskStatus.BLOCKED);
					}
				} else {
					// Si no ha llegado la fecha de inicio pero tiene usuarios asignados la ponemos como scheduled
					task.setTaskStatus(EnumTaskStatus.SCHEDULED);
				}
			}
		} else if (task.getTaskStatus().equals(EnumTaskStatus.SCHEDULED)) {
			if (task.getUsersAssigned().isEmpty()) {
				if (task.getStartDate().isAfter(LocalDateTime.now())) {
					task.setTaskStatus(EnumTaskStatus.PENDING);
				} else {
					task.setTaskStatus(EnumTaskStatus.STOPPED);
				}				
			} else if (task.getStartDate().isBefore(LocalDateTime.now())) {
				// comprobamos si hay alguna tarea que lo bloquee o la ponemos como empezada
				if (!isBlocked(task)) {
					if(task.getEndDate().isBefore(LocalDateTime.now())) {
						task.setTaskStatus(EnumTaskStatus.DELAYED);
					} else {
						task.setTaskStatus(EnumTaskStatus.STARTED);
					}
				} else {
					task.setTaskStatus(EnumTaskStatus.BLOCKED);
				}
			}			
    	} else if (task.getTaskStatus().equals(EnumTaskStatus.STARTED)) {
    		if (task.getUsersAssigned().isEmpty()) {
				if (task.getStartDate().isAfter(LocalDateTime.now())) {
					task.setTaskStatus(EnumTaskStatus.PENDING);
				} else {
					task.setTaskStatus(EnumTaskStatus.STOPPED);
				}				
			} else if (task.getEndDate().isBefore(LocalDateTime.now())) {
				task.setTaskStatus(EnumTaskStatus.DELAYED);
			}
		} else if (task.getTaskStatus().equals(EnumTaskStatus.BLOCKED)) {
			// comprobamos si han desaparecido los bloqueos
			if (!isBlocked(task)) {
				task.setTaskStatus(EnumTaskStatus.STARTED);
			}
    	}
    	
    	if (task.getChilds() != null && !task.getChilds().isEmpty()) {
    		task.getChilds().forEach(c -> updateTaskStatus(c));
    	}
    }
    
    /**
     * Método que calcula si una tarea está bloqueada o no
     * 
     * @param task Tarea que se quiere comprobar
     * @return Si la tarea está bloqueada
     * @version 1.0
     * @since 1.0
     */
    public boolean isBlocked(TGpeMTask task) {
    	return !task.getDependencies().parallelStream().allMatch(t -> t.getTaskStatus().equals(EnumTaskStatus.FINISHED));
    }
}