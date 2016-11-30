package com.awg.gpe.data.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.awg.gpe.data.model.TGpePTaskStatus;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public enum EnumTaskStatus {

    // Estados Metrica V3
    /**
     * Una tarea se define como completada cuando ha sido validada por el responsable
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    FINISHED(1L, "FINISHED", "task.status.finished", "#4CAF50"), 
    /**
     * Una tarea está planificada cuando se le ha asignado una fecha de inicio y un usuario
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    SCHEDULED(2L, "SCHEDULED", "task.status.scheduled", "#3F51B5"), 
    /**
     * Una tarea está iniciada cuando tiene un usuario asignado y se ha cumplido la fecha de inicio o si se pone en este estado
     * manualmente
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    STARTED(3L, "STARTED", "task.status.started", "#00BCD4"), 
    /**
     * Una tarea se encuentra parada cuando se ha cumplido la fecha de inicio y no tiene un usuario asignado o si se pone
     * en este estado manualmente
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    STOPPED(4L, "STOPPED", "task.status.stopped", "#FF5722"), 
    /**
     * Una tarea está bloqueada si se ha cumpido la fecha de inicio y existe otra tarea anterior que sea bloqueante y no haya finalizado
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    BLOCKED(5L, "BLOCKED", "task.status.blocked", "#F44336"), 
    /**
     * Una tarea se marca como pendiente cuando no tiene asociado un desarrollador
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    PENDING(6L, "PENDING", "task.status.pending", "#FFC107"), 
    /**
     * Una tarea se marca como archivada solamente de forma manual por el responsable o el jefe de proyecto
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    ARCHIVED(7L, "ARCHIVED", "task.status.archived", "#607D8B"), 
    /**
     * Una tarea se marca en el estado draft (o pendiente de aprobación) cuando el desarrollador la marca como finalizada pero el
     * responsable todavía no la ha validado
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    DRAFT(8L, "DRAFT", "task.status.draft", "#795548"), 
    /**
     * Una tarea se marca como retrasada cuando se ha cumplido la fecha de finalización y todavía no ha sido completada.
     * <p>
     * Este estado pertenece a los proyectos con metodología Métrica V3
     * </p>
     * @since 1.0
     */
    DELAYED(9L, "DELAYED", "task.status.delayed", "#FF9800"),
    
    // Estados Scrum
    /**
     * Una tarea se marca como Backlog si se encuentra en la bolsa de tareas del proyecto pero aún no ha sido planificada
     * <p>
     * Este estado pertenece a los proyectos con metodología Scrum
     * </p>
     * @since 1.0
     */
    BACKLOG(10L, "BACKLOG", "task.status.backlog", "#607D8B"),
    /**
     * Una tarea se marca como pendiente de hacer si se encuentra en la lista de ToDos de la iteración del proyecto
     * <p>
     * Este estado pertenece a los proyectos con metodología Scrum
     * </p>
     * @since 1.0
     */
    TODO(11L, "TODO", "task.status.todo", "#795548"),
    /**
     * Una tarea se marca como "En Progreso" en el caso de que se esté realizando en este momento
     * <p>
     * Este estado pertenece a los proyectos con metodología Scrum
     * </p>
     * @since 1.0
     */
    IN_PROGRESS(12L, "IN_PROGRESS", "task.status.inprogress", "#00BCD4"),
    /**
     * Una tarea se marca como "Probando" si ya se ha terminado su construcción y se encuentra en fase de pruebas
     * <p>
     * Este estado pertenece a los proyectos con metodología Scrum
     * </p>
     * @since 1.0
     */
    TESTING(13L, "TESTING", "task.status.testing", "#FFC107"),    
    /**
     * Una tarea se marca como "Hecha" si está lista para su entrega
     * <p>
     * Este estado pertenece a los proyectos con metodología Scrum
     * </p>
     * @since 1.0
     */
    DONE(14L, "DONE", "task.status.done", "#4CAF50"),
    /**
     * Una tarea se marca como "Reunión" cuando se trata de una reunón planificada.
     * <p>
     * Las tareas en este estado no cuentan a la hora de realizar la planificación de un proyecto
     * </p>     * 
     * @since 1.0
     */
    MEETING(15L, "MEETING", "task.status.meeting", "#F44336");

    EnumTaskStatus(Long id, String code, String description, String color) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.color = color;
    }

    private final Long id;

    private final String code;

    private final String description;
    
    private final String color;
    
    public static List<TGpePTaskStatus> getActiveStatus() {
    	List<TGpePTaskStatus> res = new ArrayList<>();
    	EnumTaskStatus[] finishedStatus = {EnumTaskStatus.FINISHED, EnumTaskStatus.DONE, EnumTaskStatus.ARCHIVED, EnumTaskStatus.DRAFT};
    	for(EnumTaskStatus status : EnumTaskStatus.values()) {
    		if (!Arrays.asList(finishedStatus).contains(status)) {
    			res.add(new TGpePTaskStatus(status));
    		}
    	}
    	return res;
    }
    
    public static List<TGpePTaskStatus> getMetrica3() {
    	List<TGpePTaskStatus> res = new ArrayList<>();
    	EnumTaskStatus[] finishedStatus = {EnumTaskStatus.FINISHED, EnumTaskStatus.STOPPED, EnumTaskStatus.ARCHIVED, EnumTaskStatus.SCHEDULED, EnumTaskStatus.BLOCKED, EnumTaskStatus.DELAYED, EnumTaskStatus.DRAFT, EnumTaskStatus.STARTED, EnumTaskStatus.PENDING};
    	for(EnumTaskStatus status : EnumTaskStatus.values()) {
    		if (Arrays.asList(finishedStatus).contains(status)) {
    			res.add(new TGpePTaskStatus(status));
    		}
    	}
    	return res;
    }
    
    public static List<TGpePTaskStatus> getScrum() {
    	List<TGpePTaskStatus> res = new ArrayList<>();
    	EnumTaskStatus[] finishedStatus = {EnumTaskStatus.MEETING, EnumTaskStatus.IN_PROGRESS, EnumTaskStatus.TESTING, EnumTaskStatus.TODO, EnumTaskStatus.DONE, EnumTaskStatus.BACKLOG};
    	for(EnumTaskStatus status : EnumTaskStatus.values()) {
    		if (Arrays.asList(finishedStatus).contains(status)) {
    			res.add(new TGpePTaskStatus(status));
    		}
    	}
    	return res;
    }

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

	/**
	 * @return el atributo color
	 */
	public String getColor() {
		return this.color;
	}

}
