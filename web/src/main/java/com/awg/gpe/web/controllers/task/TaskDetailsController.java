package com.awg.gpe.web.controllers.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.enums.EnumPriority;
import com.awg.gpe.data.enums.EnumTaskStatus;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMIncurred;
import com.awg.gpe.data.model.TGpeMTask;
import com.awg.gpe.data.model.TGpeMTaskComment;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpePPriority;
import com.awg.gpe.data.model.TGpePTaskStatus;
import com.awg.gpe.data.model.TGpePTaskType;
import com.awg.gpe.data.services.LoginService;
import com.awg.gpe.data.services.ServiceMIncurred;
import com.awg.gpe.data.services.ServiceMTask;
import com.awg.gpe.data.services.ServiceMTaskComment;
import com.awg.gpe.data.services.ServiceMUser;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.utils.NumericUtils;

/**
 * Controlador del detalle de la tarea
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "taskDetailsController")
@ViewScoped
public class TaskDetailsController extends BaseController {
    
    private static final Logger log = Logger.getLogger(TaskDetailsController.class);
    
    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_TASK_DETAILS = "/views/tasks/taskDetail.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMTask taskService;
    
    @Autowired
    private ServiceMTaskComment commentService;
    
    @Autowired
    private ServiceMIncurred incurredService;
    
    @Autowired
    private ServiceMUser userService;

    // Attributes ----------------------------------------------------------
    /**
     * Tarea seleccionada
     * @version 1.0
     * @since 1.0
     */
    private TGpeMTask task;
    /**
     * Lista con las tareas hijas de la tarea seleccionada
     * @version 1.0
     * @since 1.0
     */
    private List<TGpeMTask> childTasks;
    /**
     * Objeto con el contenido de los nuevos comentarios
     * @version 1.0
     * @since 1.0
     */
    private TGpeMTaskComment newComment;
    /**
     * Objeto con la nueva imputación
     * @version 1.0
     * @since 1.0
     */
    private TGpeMIncurred newIncurred;
    
	/**
	 * Listado con los usuarios disponibles para asignar
	 * @version 1.0
	 * @since 1.0
	 */
	private List<TGpeMUser> availableUsers;

    /**
     * Objeto conteniendo los dos listados mostrados al asignar los usuarios
     * @version 1.0
     * @since 1.0
     */
    private DualListModel<TGpeMUser> picklist;
    
    // New task
    /**
     * Objeto conteniendo los datos de la tarea hija a crear
     * @version 1.0
     * @since 1.0
     */
    private TGpeMTask newTask;
    private List<EnumPriority> taskPriorities;
    private EnumPriority newTaskPriority;
    
    /**
     * @see com.awg.gpe.web.controllers.BaseController#init()
     * @version 1.0
     * @since 1.0
     */
    @Override
    @PostConstruct
    public void init() {
        initialize();
    }

    /**
     * @see com.awg.gpe.web.controllers.BaseController#reloadPage()
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected void reloadPage() {
        goToTaskDetails();
    }
    
    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de detalle de proyecto
     * @version 1.0
     * @since 1.0
     */
    public void goToTaskDetails() {
        goToView(TaskDetailsController.VIEW_TASK_DETAILS);
        clear();
    }
    
    /**
     * Método que ejecuta la navegación hacia la pantalla de detalle de tarea
     * 
     * @param task La tarea de la que se quieren ver los detalles
     * @version 1.0
     * @since 1.0
     */
    public void goToTaskDetails(TGpeMTask task) {
        this.task = task;
        clear();
        goToView(TaskDetailsController.VIEW_TASK_DETAILS);
    }
    
    public String progressStyleClass() {
    	Float percentage = this.task.getPercentage();
    	Float successLimit = 100F;
        Float warningLimit = 85F;
        Float dangerLimit = 50F;
        
        // Cambiamos el color de la barra de tareas dependiendo del rango en el que se encuentre
        if (NumericUtils.between(percentage, 0F, dangerLimit)) {
        	return "danger";
        } else if (NumericUtils.between(percentage, dangerLimit, warningLimit)) {
            return "warning";
        } else if (NumericUtils.between(percentage, warningLimit, successLimit)) {
        	return "success";
        } else {
            return "critical";
        }
    }
    
    private void clear() {
        taskPriorities = Arrays.asList(EnumPriority.values());
        newTaskPriority = null;
    	try {
            task = this.taskService.findCompleteTask(this.task);
			if (task.getChilds() != null && !task.getChilds().isEmpty()) {
                childTasks = task.getChilds().stream().sorted((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate())).collect(Collectors.toList());
			} else {
                childTasks = new ArrayList<>();
			}
		} catch (ServiceException e) {
            TaskDetailsController.log.error("Se ha producido un error al buscar la tarea completa: " + e.getMessage());
		}
        newComment = new TGpeMTaskComment();
        newIncurred = new TGpeMIncurred();
        newTask = new TGpeMTask();
    	if (this.task != null) {
            availableUsers = this.userService.findAvailableUsersForTask(this.task);
            picklist = new DualListModel<>(this.availableUsers, new ArrayList<>(this.task.getUsersAssigned()));
    	}
    }
    
    
    /**
     * Método que añade un nuevo comentario a la tarea
     */
    public void addComment() {
    	if (this.newComment != null) {
    		try {
                this.task.addComment(this.newComment);
                this.newComment.setUser(this.loggedUser);
                this.commentService.createComment(this.newComment);
    		} catch (ServiceException e) {
    			swal("Se ha producido un error desconocido", null, BaseController.ALERT_ERROR);
    		}
    	}
        this.newComment = new TGpeMTaskComment();
    }
    
    /**
     * Método que añade una imputación a la tarea
     * @version 1.0
     * @since 1.0
     */
    public void addIncurred() {
    	if (this.newIncurred != null) {
    		try {
                this.newIncurred.updateIncurred(this.loggedUser, this.task);
                this.incurredService.createIncurred(this.newIncurred);
    			goToTaskDetails(this.task);
    		} catch (ServiceException e) {
    			swal("Se ha producido un error desconocido", null, BaseController.ALERT_ERROR);
    		}
    	}
        this.newIncurred = new TGpeMIncurred();
    }
    
    /**
     * Método ejecutado al asignar un usuario
     * @version 1.0
     * @since 1.0
     */
    public void assignUser() {
    	List<TGpeMUser> newAssignedUsers = this.picklist.getTarget();
    	newAssignedUsers.removeAll(this.task.getUsersAssigned());
    	try {
    		if (newAssignedUsers != null && !newAssignedUsers.isEmpty()) {
    			newAssignedUsers.forEach(u -> assignUserToTask(this.task, u));
                this.userService.save(newAssignedUsers);
    		}
    		
    		List<TGpeMUser> oldAssignedUsers = this.picklist.getSource();
    		oldAssignedUsers.removeAll(this.availableUsers);
    		if (oldAssignedUsers != null && !oldAssignedUsers.isEmpty()) {
    			oldAssignedUsers.forEach(u -> removeUserFromTask(this.task, u));
                this.userService.save(oldAssignedUsers);
    		}

    		// actualizamos el estado de la tarea
            this.task = this.taskService.updateTask(this.task);
    		
    	} catch (ServiceException e) {
    		swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    	}
    	
    	clear();
    	swal("Se han modificado los usuarios asignados", null, BaseController.ALERT_SUCCESS);
    }

	/**
	 * Método que cambia el estado de la tarea a empezada
	 * @version 1.0
	 * @since 1.0
	 */
	public void startTask() {
    	if (this.task != null) {
    		try {
    			if (this.task.getTaskStatus().equals(EnumTaskStatus.SCHEDULED) ||
                        this.task.getTaskStatus().equals(EnumTaskStatus.STOPPED)) {
    				String msg;
        			if (this.taskService.isBlocked(this.task)) {
                        this.task.setTaskStatus(EnumTaskStatus.BLOCKED);
        				msg = "La tarea se encuentra bloqueada";
        			} else {
                        this.task.setTaskStatus(EnumTaskStatus.STARTED);
        				msg = "Se ha empezado la tarea";
        			}
                    this.task = this.taskService.updateTask(this.task);
            		swal(msg, null, BaseController.ALERT_SUCCESS);
        		} else {
        			swal("La tarea ya se encuentra empezada", null, BaseController.ALERT_ERROR);
        		}        		
    		} catch (ServiceException e) {
    			swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		}    			
    	}
    }
    
	/**
	 * Método que cambia el estado de la tarea a parada
	 * @version 1.0
	 * @since 1.0
	 */
    public void stopTask() {
    	if (this.task != null) {
    		try {
    			if (this.task.getTaskStatus().equals(EnumTaskStatus.STARTED) ||
                        this.task.getTaskStatus().equals(EnumTaskStatus.DELAYED)) {
    				// Paramos la tarea y todas sus tareas hijas
                    this.task.setTaskStatus(EnumTaskStatus.STOPPED);
        			if (this.task.getChilds() != null && !this.task.getChilds().isEmpty()) {
                        this.task.getChilds().parallelStream()
        					.filter(t -> (t.getTaskStatus().equals(EnumTaskStatus.STARTED)
        							||
        							t.getTaskStatus().equals(EnumTaskStatus.DELAYED)))
        					.forEach(t -> t.setTaskStatus(EnumTaskStatus.STOPPED));
        			}
                    this.task = this.taskService.saveAndFlush(this.task);
            		swal("Se ha parado la tarea", null, BaseController.ALERT_SUCCESS);
        		} else {
        			swal("La tarea ya está parada", null, BaseController.ALERT_ERROR);
        		}        		
    		} catch (ServiceException e) {
    			swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		}    			
    	}
    }
    
    /**
	 * Método que cambia el estado de la tarea a archivada
	 * @version 1.0
	 * @since 1.0
	 */
    public void archiveTask() {
    	if (this.task != null) {
    		try {
                this.task.setTaskStatus(EnumTaskStatus.ARCHIVED);
                this.task = this.taskService.saveAndFlush(this.task);
        		swal("Se ha archivado la tarea", null, BaseController.ALERT_SUCCESS);
    		} catch (ServiceException e) {
    			swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		}    			
    	}
    }
    
    /**
	 * Método que cambia el estado de la tarea a finalizada
	 * @version 1.0
	 * @since 1.0
	 */
    public void approveTask() {
    	if (this.task != null) {
    		try {
                this.task.setTaskStatus(EnumTaskStatus.FINISHED);
                this.task = this.taskService.saveAndFlush(this.task);
        		swal("Se ha aprobado la tarea", null, BaseController.ALERT_SUCCESS);
    		} catch (ServiceException e) {
    			swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		}    			
    	}
    }
    
    /**
	 * Método que rechaza la finalización de una nueva tarea, cambiándole el estado a empezada o retrasada
	 * @version 1.0
	 * @since 1.0
	 */
    public void rejectTask() {
    	if (this.task != null) {
    		try {
                this.task.setTaskStatus(EnumTaskStatus.STARTED);
                this.task = this.taskService.updateTask(this.task);
    			swal("Se ha rechazado la tarea", null, BaseController.ALERT_SUCCESS);
    		} catch (ServiceException e) {
    			swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		}    			
    	}
    }
    
    /**
	 * Método que marca una tarea como finalizada por el desarrollador, a la espera de la aprobación del responsanble
	 * @version 1.0
	 * @since 1.0
	 */
    public void draftTask() {
    	if (this.task != null) {
    		try {
                this.task.setTaskStatus(EnumTaskStatus.DRAFT);
                this.task = this.taskService.saveAndFlush(this.task);
        		swal("Se ha modificado el estado de la tarea", null, BaseController.ALERT_SUCCESS);
    		} catch (ServiceException e) {
    			swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		}    			
    	}
    }
    
    public void createTask() {
    	if (this.newTask != null) {
    		try {
                this.task.addChild(this.newTask);

                this.newTask.setTaskStatus(new TGpePTaskStatus(EnumTaskStatus.PENDING));
                this.newTask.setTaskType(this.task.getTaskType());
                this.newTask.setTaskPriority(new TGpePPriority(this.newTaskPriority));


                this.newTask.setCreatedBy(this.loggedUser);
                this.newTask.setLastEditionBy(this.loggedUser);

                this.taskService.save(this.newTask);
    			clear();
    			swal("Se ha creado la tarea correctamente", null, BaseController.ALERT_SUCCESS);
    		} catch (ServiceException e) {
    			swal("Se ha producido un error", null, BaseController.ALERT_ERROR);
    		}
    	}
    }
    
    private void removeUserFromTask(TGpeMTask task, TGpeMUser user) {
    	task.removeAssignedUser(user);
    	if (task.getChilds() != null && !task.getChilds().isEmpty()) {
    		task.getChilds().forEach(t -> removeUserFromTask(t, user));
    	}
	}

	private void assignUserToTask(TGpeMTask task, TGpeMUser user) {
		task.addAssignedUser(user);
		if (task.getChilds() != null && !task.getChilds().isEmpty()) {
			task.getChilds().forEach(t -> assignUserToTask(t, user));
		}
	}

    // Getters and setters --------------------------------------------------
    
    public TGpeMTask getTask() {
        return this.task;
    }

    public void setTask(TGpeMTask task) {
        this.task = task;
    }

	public TGpeMTaskComment getNewComment() {
		return this.newComment;
	}

	public void setNewComment(TGpeMTaskComment newComment) {
		this.newComment = newComment;
	}

	public TGpeMIncurred getNewIncurred() {
		return this.newIncurred;
	}

	public void setNewIncurred(TGpeMIncurred newIncurred) {
		this.newIncurred = newIncurred;
	}

	public DualListModel<TGpeMUser> getPicklist() {
		return this.picklist;
	}

	public void setPicklist(DualListModel<TGpeMUser> picklist) {
		this.picklist = picklist;
	}

	public List<TGpeMTask> getChildTasks() {
		return this.childTasks;
	}

	public void setChildTasks(List<TGpeMTask> childTasks) {
		this.childTasks = childTasks;
	}

	public TGpeMTask getNewTask() {
		return this.newTask;
	}

	public void setNewTask(TGpeMTask newTask) {
		this.newTask = newTask;
	}

	public List<EnumPriority> getTaskPriorities() {
		return this.taskPriorities;
	}

	public void setTaskPriorities(List<EnumPriority> taskPriorities) {
		this.taskPriorities = taskPriorities;
	}

	public EnumPriority getNewTaskPriority() {
		return this.newTaskPriority;
	}

	public void setNewTaskPriority(EnumPriority newTaskPriority) {
		this.newTaskPriority = newTaskPriority;
	}

}
