package com.awg.gpe.web.controllers.project;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.awg.gpe.data.enums.EnumMethodology;
import com.awg.gpe.data.enums.EnumSprintDuration;
import com.awg.gpe.data.enums.EnumTaskType;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMGoal;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMRequirement;
import com.awg.gpe.data.model.TGpeMTask;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpePPublicHolidays;
import com.awg.gpe.data.services.ServiceMGoal;
import com.awg.gpe.data.services.ServiceMProject;
import com.awg.gpe.data.services.ServiceMRequirement;
import com.awg.gpe.data.services.ServiceMTask;
import com.awg.gpe.data.services.ServiceMUser;
import com.awg.gpe.data.services.ServicePPublicHolidays;
import com.awg.gpe.utils.BusinessHours;
import com.awg.gpe.utils.DateUtils;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.utils.NumericUtils;
import com.awg.gpe.web.utils.validations.ValidatorBuilder;

/**
 * Controlador que maneja las acciones en la vista de nuevo proyecto
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "newProjectController")
@ViewScoped
public class NewProjectController extends BaseController {

    private static final Logger log = Logger.getLogger(NewProjectController.class);

    private static final String FLOW_BACKWARD = "back";
    private static final String FLOW_FORWARD = "forward";
    private static final String FLOW_STAY = "stay";
    // Se corresponden con los id de los paneles del wizard
    private static final String WIZARD_INFO_STEP = "projectInfoTab";
    private static final String WIZARD_DETAILS_STEP = "projectDetailsTab";
    private static final String WIZARD_OVERVIEW_STEP = "projectOverviewTab";

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_NEW_PROJECT = "/views/projects/newProject.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMProject projectService;
    @Autowired
    private ServiceMRequirement requirementService;
    @Autowired
    private ServiceMUser userService;
    @Autowired
    private ServiceMTask taskService;
    @Autowired
    private ServiceMGoal goalService;
    @Autowired
    private ServicePPublicHolidays holidaysService;
    
    @Value("${gpe.projectLogoFolder}")
    private String projectLogoFolder;

    // Attributes ----------------------------------------------------------
    /**
     * Objeto que recoge los datos del nuevo proyecto
     * @version 1.0
     * @since 1.0
     */
    private TGpeMProject newProject;
    /**
     * Objeto que recoge los datos del nuevo requerimiento
     * @version 1.0
     * @since 1.0
     */
    private TGpeMRequirement newRequirement;
    /**
     * Objeto que recoge las nuevas tareas que se van a crear para el nuevo proyecto
     * @version 1.0
     * @since 1.0
     */
    private List<TGpeMTask> newTasks;
    
    /**
     * Listado de días festivos
     * @version 1.0
     * @since 1.0
     */
    private List<TGpePPublicHolidays> bankHolidays;

    private List<EnumSprintDuration> sprintDurations;
    private EnumSprintDuration newSprintPeriod;
    /**
     * Lista con todos los posibles jefes de proyecto disponibles en el sistema
     * 
     * @since 1.0
     */
    private List<TGpeMUser> projectLeaders;
    /**
     * Lista con todos los posibles responsables de proyecto disponibles en el sistema
     * 
     * @since 1.0
     */
    private List<TGpeMUser> projectManagers;
    /**
     * Lista con todos los posibles desarrolladores disponibles en el sistema
     * @version 1.0
     * @since 1.0
     */
    private List<TGpeMUser> projectDevelopers;

    /**
     * Jefe del nuevo proyecto
     * 
     * @since 1.0
     */
    private TGpeMUser leader;
    /**
     * Responsables del nuevo proyecto, solamente para proyectos en metrica 3
     * 
     * @since 1.0
     */
    private TGpeMUser[] managers;

    /**
     * Desarrolladores del nuevo proyecto
     * @version 1.0
     * @since 1.0
     */
    private TGpeMUser[] developers;

    /**
     * Valor del scrum master, solamente para proyectos scrum
     * 
     * @since 1.0
     */
    private TGpeMUser scrumMaster;

    /**
     * {@link TreeNode} en el que se almacenarán las tareas iniciales del proyecto para mostrarlo
     * 
     * @since 1.0
     */
    private TreeNode taskTree;

    /**
     * Objeto que recoge las horas utilizadas a la hora de planificar el nuevo requerimiento
     * @version 1.0
     * @since 1.0
     */
    private Float usedHours;
    /**
     * Objeto que recoge el porcentaje de horas usadas en el nuevo requerimiento
     * @version 1.0
     * @since 1.0
     */
    private Float hoursPercentage;
    
    /**
     * Peso asignado a la tarea de PSI 
     * @version 1.0
     * @since 1.0
     */
    private Integer psiWeight;
    /**
     * Peso asignado a la tarea de EV 
     * @version 1.0
     * @since 1.0
     */
    private Integer evWeight;
    /**
     * Peso asignado a la tarea de GPI
     * @version 1.0
     * @since 1.0
     */
    private Integer gpiWeight;
    /**
     * Peso asignado a la tarea de ASI
     * @version 1.0
     * @since 1.0
     */
    private Integer asiWeight;
    /**
     * Peso asignado a la tarea de DSI
     * @version 1.0
     * @since 1.0
     */
    private Integer dsiWeight;
    /**
     * Peso asignado a la tarea de CSI
     * @version 1.0
     * @since 1.0
     */
    private Integer csiWeight;
    /**
     * Peso asignado a la tarea de IAS
     * @version 1.0
     * @since 1.0
     */
    private Integer iasWeight;
    
    // Show panels flags
    /**
     * Marca para ver que asistente mostrar
     * @version 1.0
     * @since 1.0
     */
    private Boolean showMetricaWizard;
    private Boolean showScrumWizard;

    // Init ---------------------------------------------------------------
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
        goToNewProject();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de nuevo proyecto
     * 
     * @version 1.0
     * @since 1.0
     */
    public void goToNewProject() {
        goToView(NewProjectController.VIEW_NEW_PROJECT);
        projectLeaders = this.userService.findProjectLeaders();
        projectManagers = this.userService.findProjectManagers();
        projectDevelopers = this.userService.findDevelopers();
        clear();
    }

    // Methods --------------------------------------------------------------

    /**
     * Método llamado al crear un nuevo proyecto de Métrica V3
     * 
     * @version 1.0
     * @since 1.0
     */
    public void createMetricaV3Project() {
        clear();
        this.showMetricaWizard = true;
        this.newProject = this.projectService.newProject(EnumMethodology.METRICA_V3);
        this.newRequirement = this.requirementService.createNewRequirement(this.newProject);
        TGpeMGoal initGoal = this.goalService.createInitGoal();
        this.newRequirement.addGoal(initGoal);
        this.newProject.addRequirement(this.newRequirement);
        this.usedHours = 0F;
    }

    /**
     * Método llamado al pulsar el botón de crear un nuevo proyecto Scrum
     * 
     * @version 1.0
     * @since 1.0
     */
    public void createScrumProject() {
        clear();
        this.showScrumWizard = true;
        this.newProject = this.projectService.newProject(EnumMethodology.SCRUM);
        this.newRequirement = this.requirementService.createNewSprint(this.newProject);
        this.newProject.addRequirement(this.newRequirement);
        this.sprintDurations = EnumSprintDuration.getValues();
    }
    
    /**
     * Método que maneja el cambio de logo del proyecto.
     * <p>
     * Este manejador copia la imagen subida en el FS de la aplicación, copiando la ruta del archivo a
     * la propiedad del proyecto
     * </p>
     * 
     * @param event Evento de Primefaces
     * @version 1.0
     * @since 1.0
     */
    public void handleProjectLogo(FileUploadEvent event) {
    	UploadedFile file = event.getFile();
    	try (InputStream is = file.getInputstream()) {
    		Path newFile = Paths.get(this.projectLogoFolder, file.getFileName());
    		//Path newFile = Files.createTempFile(Paths.get(folder.toString(), file.getFileName()));
    		Files.copy(is, newFile, StandardCopyOption.REPLACE_EXISTING);

            this.newProject.setProjectLogo("img/projectLogos/" + newFile.getFileName());
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Método que guarda el nuevo proyecto en la base de datos
     * 
     * @version 1.0
     * @since 1.0
     */
    public void createProject() {
        if (this.newProject != null) {
            try {
                // Añadimos al jefe de proyecto o al product owner
                this.leader.addProjectToProjectList(this.newProject);
                
                // Añadimos a los responsables del proyecto o al scrum master
                if (this.showMetricaWizard) {
                    Arrays.asList(this.managers).forEach(m -> this.newProject.addManager(m));
                } else if (this.showScrumWizard) {
                    this.newProject.setScrumMaster(this.scrumMaster);
                }
                // Añadimos a los desarrolladores
                Arrays.asList(this.developers).forEach(dev -> this.newRequirement.addDeveloper(dev));
                
                // Añadimos las tareas al proyecto
                this.newTasks.forEach(t -> this.newRequirement.addCompleteTask(t));
                
                // Añadimos la fecha de comienzo del proyecto
                this.newProject.setStartDate(this.newRequirement.getStartDate());
                
                // Creamos una lista con los usuarios a guardar
                List<TGpeMUser> usersToSave = new ArrayList<>(); 
                usersToSave.add(this.leader);
                usersToSave.addAll(Arrays.asList(this.managers));
                usersToSave.addAll(Arrays.asList(this.developers));
                
                // Guardamos los datos en BBDD
                this.projectService.createProject(this.newProject, usersToSave);
                
                clear();
                swal("Se ha creado el nuevo proyecto correctamente", null, "success");
            } catch (ServiceException e) {
                NewProjectController.log.error("Se ha producido un error al guardar el proyecto: " + e.getMessage());
                swal("Se ha producido un error al crear el nuevo proyecto", null, "error");
            }
        } else {
            NewProjectController.log.error("Se ha intentado guardar un proyecto nulo");
            swal("Se ha producido un error. Inténtelo de nuevo", null, "error");
        }
    }
    
    /**
     * Método que controla la navegación del asistente para la creación del proyecto
     * 
     * @param event
     *            FlowEvent pasado por JSF
     * @return El siguiente paso
     * @version 1.0
     * @since 1.0
     */
    public String wizardFlow(FlowEvent event) {
        // Comprobamos la dirección del movimiento en el asistente
        String flowDirection = wizardFlowDirection(event);

        if (flowDirection.equals(NewProjectController.FLOW_FORWARD)) {
            // Validamos el formulario antes de pasar a la nueva pestaña
            if (validateEvent(event.getOldStep())) {
                // Ejecutamos los cambios en el paso de pestaña
                executeOnNext(event);
                // Actualizamos la nueva pestaña y indicamos al asistente que la muestre
                update(event.getNewStep());
                return event.getNewStep();
            } else {
                swal("Existe algún error en el formulario", null, "error");
                return event.getOldStep();
            }
        } else if (flowDirection.equals(NewProjectController.FLOW_BACKWARD)) {
            // Si la navegación es hacia atrás, solamente nos tendremos que ocupar de los botones, y devolver el siguiente paso
            executeOnBack(event);
            // Actualizamos la vista
            update(event.getNewStep());
            // Indicamos al asistente que vuelva atrás
            return event.getNewStep();
        } else {
            // Si es la misma pestaña, no validamos nada
            return event.getNewStep();
        }
    }

    /**
     * Método que se ejecuta al cerrar un nodo y que se ocupa de calcular las horas, la fecha de comienzo y la fecha de fin de la tarea padre
     * 
     * @param event
     *            Evento de Primefaces que activa la ejecución de este método
     * @version 1.0
     * @since 1.0
     */
    public void onTaskTreeCollapse(NodeCollapseEvent event) {
        TreeNode actualNode = event.getTreeNode();
        TGpeMTask task = (TGpeMTask) actualNode.getData();
        calculateParentTaskFromChilds(task);
    }

    /**
     * Método que se ejecuta al editar una celda de la tabla.
     * <p>
     * Este método solo realizará procesamiento en el caso de que se edite el campo de las horas, actualizando el porcentaje de horas usadas para el requerimiento.
     * </p>
     * 
     * @param event
     *            Evento que lanza el método
     * @version 1.0
     * @since 1.0
     */
    public void onCellEditing(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        // Primera vez que se rellena el campo
        if (oldValue == null && newValue != null && newValue instanceof Float) {
            this.usedHours += (Float) newValue;
        } else if (oldValue != null && oldValue instanceof Float && newValue != null && newValue instanceof Float) {
            this.usedHours += (Float) newValue - (Float) oldValue;
        }
        
        if (this.usedHours != null) {
            this.hoursPercentage = (this.usedHours / this.newRequirement.getHours()) * 100;
            actualizaProgressBar(this.hoursPercentage);
        }
    }

    private void actualizaProgressBar(Float hoursPercentage) {
        Float successLimit = 70F;
        Float warningLimit = 85F;
        Float dangerLimit = 100F;
        
        // Cambiamos el color de la barra de tareas dependiendo del rango en el que se encuentre
        if (NumericUtils.between(hoursPercentage, 0F, successLimit)) {
            execute("$('#createProjectForm\\\\:usedHoursProgress').removeClass('warning danger critical').addClass('success')");
        } else if (NumericUtils.between(hoursPercentage, successLimit, warningLimit)) {
            execute("$('#createProjectForm\\\\:usedHoursProgress').removeClass('success danger critical').addClass('warning')");
        } else if (NumericUtils.between(hoursPercentage, warningLimit, dangerLimit)) {
            execute("$('#createProjectForm\\\\:usedHoursProgress').removeClass('success warning critical').addClass('danger')");
        } else {
            execute("$('#createProjectForm\\\\:usedHoursProgress').removeClass('success warning danger').addClass('critical')");
        }
    }

    private void calculateParentTaskFromChilds(final TGpeMTask task) {
        Float totalHours = task.getChilds().parallelStream().map(TGpeMTask::getHours).filter(hours -> hours != null).reduce(0F, (a, b) -> a + b);
        LocalDateTime startDate = task.getChilds().parallelStream().map(TGpeMTask::getStartDate).filter(date -> date != null).min(LocalDateTime::compareTo).orElse(null);
        LocalDateTime endDate = task.getChilds().parallelStream().map(TGpeMTask::getEndDate).filter(date -> date != null).max(LocalDateTime::compareTo).orElse(null);
        task.setHours(totalHours);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
    }

    private String wizardFlowDirection(FlowEvent event) {
        String res = null;

        // Comprobamos si la navegación es hacia la misma pestaña
        if (event.getOldStep().equals(event.getNewStep())) {
            res = NewProjectController.FLOW_STAY;
        } else {
            switch (event.getOldStep()) {
                case NewProjectController.WIZARD_INFO_STEP:
                    res = NewProjectController.FLOW_FORWARD;
                    break;
                case NewProjectController.WIZARD_DETAILS_STEP:
                    if (!event.getNewStep().equals(NewProjectController.WIZARD_INFO_STEP)) {
                        res = NewProjectController.FLOW_FORWARD;
                    } else {
                        res = NewProjectController.FLOW_BACKWARD;
                    }
                    break;
                case NewProjectController.WIZARD_OVERVIEW_STEP:
                    res = NewProjectController.FLOW_BACKWARD;
                    break;
                default:
                    res = NewProjectController.FLOW_FORWARD;
                    break;
            }
        }
        return res;
    }

    private void executeOnNext(FlowEvent event) {
        if (event.getOldStep().equals(NewProjectController.WIZARD_INFO_STEP)) {
        	if (this.showMetricaWizard) {
                this.newRequirement.setRequirementCode(this.newProject.getProjectTitle() + "-REQ0001");
        	} else if (this.showScrumWizard) {
                this.newRequirement.setRequirementCode(this.newProject.getProjectTitle() + "-SP0001");
        	}            
            execute("$('#createProjectForm\\\\:wizard_back').prop('disabled', false).removeClass('ui-state-disabled').addClass('waves-effect')");
        } else if (event.getOldStep().equals(NewProjectController.WIZARD_DETAILS_STEP)) {
            if (this.showMetricaWizard) {
            	// Creamos las tareas iniciales del proyecto
                this.newTasks = this.taskService.newMetricaProjectTasks("" + this.newRequirement.getRequirementCode());
            } else if (this.showScrumWizard) {
            	// A partir de la duración del sprint, calculamos su fecha de fin
            	if (this.newSprintPeriod != null) {
            		LocalDate endTime = this.requirementService.calculateEndDate(this.newRequirement, this.newSprintPeriod);
                    this.newRequirement.setEndDate(endTime);
            	} else {
            		LocalDate endTime = this.requirementService.calculateEndDate(this.newRequirement, EnumSprintDuration.ONE_MONTH);
                    this.newRequirement.setEndDate(endTime);
            	}
                this.newTasks = this.taskService.newScrumProjectTasks("" + this.newRequirement.getRequirementCode(), this.newRequirement.getStartDate(), this.newRequirement.getEndDate());
            }        	
            // Pasamos las tareas iniciales a un TreeNode
            this.taskTree = convertTasksToTree();
            // Si navegamos hacia la última pestaña, cambiamos el botón
            execute("$('#createProjectForm\\\\:wizard_next').addClass('ui-helper-hidden')");
            execute("$('#createProjectForm\\\\:wizard_submit').removeClass('ui-helper-hidden')");
            execute("$('#createProjectForm\\\\:wizard_submit .ui-button-text').addClass('save-button')");
        }
    }

    private void executeOnBack(FlowEvent event) {
        if (event.getOldStep().equals(NewProjectController.WIZARD_OVERVIEW_STEP)) {
            execute("$('#createProjectForm\\\\:wizard_submit .ui-button-text').removeClass('save-button')");
            execute("$('#createProjectForm\\\\:wizard_submit').addClass('ui-helper-hidden')");
            execute("$('#createProjectForm\\\\:wizard_next').removeClass('ui-helper-hidden')");
        } else if (event.getOldStep().equals(NewProjectController.WIZARD_DETAILS_STEP)) {
            // Deshabilitamos el botón de atrás
            execute("$('#createProjectForm\\\\:wizard_back').prop('disabled', true).removeClass('waves-effect').addClass('ui-state-disabled')");
        }
    }
    
    /**
     * Método que recalcula las horas y las fechas asignadas a las tareas dependiendo de los pesos 
     * que el usuario haya asociado a las distintas tareas
     * @version 1.0
     * @since 1.0
     */
    public void recalculateProjectTasks() {
        this.newTasks.forEach(t -> recalculateTask(t));

        this.usedHours = this.newTasks.parallelStream().map(t -> calculateTasksHours(t)).reduce((a, b) -> a + b).orElse(0F);
    	
    	if (this.usedHours != null) {
            this.hoursPercentage = (this.usedHours / this.newRequirement.getHours()) * 100;
            actualizaProgressBar(this.hoursPercentage);
        }

        this.taskTree = convertTasksToTree();
    }
    
    private Float calculateTasksHours(TGpeMTask task) {
    	Float res = 0f;
    	if (task.getChilds() != null && !task.getChilds().isEmpty()) {
    		for (TGpeMTask child : task.getChilds()) {
    			res += calculateTasksHours(child);
    		}
    	} else {
    		res = task.getHours();
    	}
    	return res;
    }

    private void recalculateTask(TGpeMTask task) {
		if (task.getTaskType().equals(EnumTaskType.SYSTEM_PLANNING)) {
			recalculateChildTask(task, this.newRequirement.getStartDate().atTime(8, 0), this.psiWeight);
		} else if (task.getTaskType().equals(EnumTaskType.SYSTEM_VIABILITY_REPORT)) {
			LocalDateTime startDate = this.newTasks.stream()
					.filter(t -> t.getTaskType().equals(EnumTaskType.SYSTEM_PLANNING))
					.map(TGpeMTask::getEndDate)
					.max(LocalDateTime::compareTo)
					.orElse(this.newRequirement.getStartDate().atStartOfDay());
			recalculateChildTask(task, startDate, this.evWeight);
		} else if (task.getTaskType().equals(EnumTaskType.INITIAL_PROJECT_ACTIVITY)) {
			LocalDateTime startDate = this.newTasks.stream()
					.filter(t -> t.getTaskType().equals(EnumTaskType.SYSTEM_VIABILITY_REPORT))
					.map(TGpeMTask::getEndDate)
					.max(LocalDateTime::compareTo)
					.orElse(this.newRequirement.getStartDate().atStartOfDay());
			recalculateChildTask(task, startDate, this.gpiWeight);
		} else if (task.getTaskType().equals(EnumTaskType.SYSTEM_ANALYSIS)) {
			LocalDateTime startDate = this.newTasks.stream()
					.filter(t -> t.getTaskType().equals(EnumTaskType.INITIAL_PROJECT_ACTIVITY))
					.map(TGpeMTask::getEndDate)
					.max(LocalDateTime::compareTo)
					.orElse(this.newRequirement.getStartDate().atStartOfDay());
			recalculateChildTask(task, startDate, this.asiWeight);
		} else if (task.getTaskType().equals(EnumTaskType.SYSTEM_DESIGN)) {
			LocalDateTime startDate = this.newTasks.stream()
					.filter(t -> t.getTaskType().equals(EnumTaskType.SYSTEM_ANALYSIS))
					.map(TGpeMTask::getEndDate)
					.max(LocalDateTime::compareTo)
					.orElse(this.newRequirement.getStartDate().atStartOfDay());
			recalculateChildTask(task, startDate, this.dsiWeight);
		} else if (task.getTaskType().equals(EnumTaskType.SYSTEM_CONSTRUCTION)) {
			LocalDateTime startDate = this.newTasks.stream()
					.filter(t -> t.getTaskType().equals(EnumTaskType.SYSTEM_DESIGN))
					.map(TGpeMTask::getEndDate)
					.max(LocalDateTime::compareTo)
					.orElse(this.newRequirement.getStartDate().atStartOfDay());
			recalculateChildTask(task, startDate, this.csiWeight);
		} else if (task.getTaskType().equals(EnumTaskType.SYSTEM_IMPLANTATION)) {
			LocalDateTime startDate = this.newTasks.stream()
					.filter(t -> t.getTaskType().equals(EnumTaskType.SYSTEM_CONSTRUCTION))
					.map(TGpeMTask::getEndDate)
					.max(LocalDateTime::compareTo)
					.orElse(this.newRequirement.getStartDate().atStartOfDay());
			recalculateChildTask(task, startDate, this.iasWeight);
		}
	}
    
    private void recalculateChildTask(TGpeMTask task, LocalDateTime startDate, Integer weight) {
    	Float prct = getNormalizedWeight(weight);
    	
    	// Número de días del requerimiento
    	List<LocalDate> bankHolidaysDates = this.bankHolidays.stream().map(TGpePPublicHolidays::getDay).collect(Collectors.toList());
    	Integer taskDays = DateUtils.businessDaysBetween(this.newRequirement.getStartDate(), this.newRequirement.getEndDate(), bankHolidaysDates);
    	// Número de horas que le corresponderían a la tarea
    	BigDecimal taskHours = new BigDecimal(this.newRequirement.getHours() * prct);
    	// Número de días que le corresponderían a la tarea, con 8h laborables por día
    	BigDecimal numberOfDaysInHours = new BigDecimal(taskDays * prct * 8);
    	// Número de tareas hijas de la tarea
    	BigDecimal numberOfChilds = BigDecimal.valueOf(task.getChilds().size());
    	
    	// Horas que le corresponderían a cada tarea hija
    	Float childHours = taskHours.divide(numberOfChilds, RoundingMode.HALF_EVEN).floatValue();
    	// Tiempo que le correspondería a cada tarea hija
    	BigDecimal[] childHoursAndMinutes = numberOfDaysInHours.divideAndRemainder(numberOfChilds, MathContext.DECIMAL64);
    	
    	int idx = 0;    	
    	for (TGpeMTask c : task.getChilds()) {
    		Integer hours = childHoursAndMinutes[0].intValue();
    		Integer minutes = childHoursAndMinutes[1].multiply(BigDecimal.valueOf(60)).divide(numberOfChilds, RoundingMode.HALF_EVEN).intValue();
    		LocalDateTime childStartDate = startDate.plus(BusinessHours.ofHoursAndMinutes(hours * idx, minutes * idx));
    		LocalDateTime childEndDate = childStartDate.plus(BusinessHours.ofHoursAndMinutes(hours, minutes));
    		c.setStartDate(childStartDate);
    		c.setEndDate(childEndDate);
    		c.setHours(childHours);
    		idx++;
    	}

    	calculateParentTaskFromChilds(task);
    }
    
	private Float getNormalizedWeight(Integer weight) {
		return new Float(weight * 100) / (this.gpiWeight + this.psiWeight + this.evWeight + this.asiWeight + this.dsiWeight + this.csiWeight + this.iasWeight) / 100;
	}

	private TreeNode convertTasksToTree() {
        TreeNode res = new DefaultTreeNode();
        this.newTasks.forEach(t -> createNodeFromTask(t, res));
        return res;
    }

    private void createNodeFromTask(TGpeMTask task, TreeNode parent) {
        TreeNode taskNode = new DefaultTreeNode(task, parent);
        if (!task.getChilds().isEmpty()) {
            task.getChilds().forEach(child -> createNodeFromTask(child, taskNode));
        }
    }

    /**
     * Método que valida el formulario en algún paso
     * 
     * @param step
     *            Paso que se desea validar
     * @return Si ha pasado la validación o no
     * @version 1.0
     * @since 1.0
     */
    private Boolean validateEvent(String step) {
        ValidatorBuilder validator = ValidatorBuilder.newInstance();
        if (step.equals(NewProjectController.WIZARD_INFO_STEP)) {
            // Se validan los datos introducidos para el proyecto
            validator = validator
            	.field(this.newProject.getProjectTitle())
            		.required().maxLength(3).and()
            	.field(this.newProject.getName()).required().and()
            	.field(this.newProject.getProjectCode())
                    .required().and()
                .field(this.newProject.getDescription())
                	.maxLength(4000).and();
        } else if (step.equals(NewProjectController.WIZARD_DETAILS_STEP)) {
            validator = validator.field(this.leader).required().and().field(Arrays.asList(this.managers)).required().and().field(this.newRequirement.getRequirementCode()).required().and()
                    .field(this.newRequirement.getStartDate()).required().after(LocalDate.now()).and().field(this.newRequirement.getEndDate()).required().after(this.newRequirement.getStartDate())
                    .and();
        } else if (step.equals(NewProjectController.WIZARD_OVERVIEW_STEP)) {
            //TODO: Validar horas y esas cosas de las tareas
        }
        return validator.validate();
    }

    private void clear() {
        this.newProject = new TGpeMProject();
        this.newRequirement = new TGpeMRequirement();
        this.newTasks = new ArrayList<>();
        this.leader = null;
        this.managers = new TGpeMUser[this.projectManagers.size()];
        this.developers = new TGpeMUser[this.projectDevelopers.size()];
        this.showMetricaWizard = false;
        this.showScrumWizard = false;
        this.taskTree = null;
        this.usedHours = null;
        this.hoursPercentage = null;
        this.bankHolidays = this.holidaysService.findAll();
    }

    // Getters and setters --------------------------------------------------

    public TGpeMProject getNewProject() {
        return this.newProject;
    }

    public void setNewProject(TGpeMProject newProject) {
        this.newProject = newProject;
    }

    public List<TGpeMUser> getProjectLeaders() {
        return this.projectLeaders;
    }

    public void setProjectLeaders(List<TGpeMUser> projectLeaders) {
        this.projectLeaders = projectLeaders;
    }

    public List<TGpeMUser> getProjectManagers() {
        return this.projectManagers;
    }

    public void setProjectManagers(List<TGpeMUser> projectManagers) {
        this.projectManagers = projectManagers;
    }

    public Boolean getShowMetricaWizard() {
        return this.showMetricaWizard;
    }

    public void setShowMetricaWizard(Boolean showMetricaWizard) {
        this.showMetricaWizard = showMetricaWizard;
    }

    public Boolean getShowScrumWizard() {
        return this.showScrumWizard;
    }

    public void setShowScrumWizard(Boolean showScrumWizard) {
        this.showScrumWizard = showScrumWizard;
    }

    /**
     * @return el atributo newRequirement
     */
    public TGpeMRequirement getNewRequirement() {
        return this.newRequirement;
    }

    /**
     * @param newRequirement
     *            valor con el que se setea el campo newRequirement
     */
    public void setNewRequirement(TGpeMRequirement newRequirement) {
        this.newRequirement = newRequirement;
    }

    /**
     * @return el atributo newTasks
     */
    public List<TGpeMTask> getNewTasks() {
        return this.newTasks;
    }

    /**
     * @param newTasks
     *            valor con el que se setea el campo newTasks
     */
    public void setNewTasks(List<TGpeMTask> newTasks) {
        this.newTasks = newTasks;
    }

    /**
     * @return el atributo leader
     */
    public TGpeMUser getLeader() {
        return this.leader;
    }

    /**
     * @param leader
     *            valor con el que se setea el campo leader
     */
    public void setLeader(TGpeMUser leader) {
        this.leader = leader;
    }

    public TGpeMUser[] getManagers() {
        return this.managers;
    }

    public void setManagers(TGpeMUser[] managers) {
        this.managers = managers;
    }

    public TGpeMUser getScrumMaster() {
        return this.scrumMaster;
    }

    public void setScrumMaster(TGpeMUser scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public List<TGpeMUser> getProjectDevelopers() {
        return this.projectDevelopers;
    }

    public void setProjectDevelopers(List<TGpeMUser> projectDevelopers) {
        this.projectDevelopers = projectDevelopers;
    }

    public TGpeMUser[] getDevelopers() {
        return this.developers;
    }

    public void setDevelopers(TGpeMUser[] developers) {
        this.developers = developers;
    }

    public TreeNode getTaskTree() {
        return this.taskTree;
    }

    public void setTaskTree(TreeNode taskTree) {
        this.taskTree = taskTree;
    }

    public Float getHoursPercentage() {
        return this.hoursPercentage;
    }

    public void setHoursPercentage(Float hoursPercentage) {
        this.hoursPercentage = hoursPercentage;
    }

	public List<EnumSprintDuration> getSprintDurations() {
		return this.sprintDurations;
	}

	public void setSprintDurations(List<EnumSprintDuration> sprintDurations) {
		this.sprintDurations = sprintDurations;
	}

	public EnumSprintDuration getNewSprintPeriod() {
		return this.newSprintPeriod;
	}

	public void setNewSprintPeriod(EnumSprintDuration newSprintPeriod) {
		this.newSprintPeriod = newSprintPeriod;
	}

	public Integer getPsiWeight() {
		return this.psiWeight;
	}

	public void setPsiWeight(Integer psiWeight) {
		this.psiWeight = psiWeight;
	}

	public Integer getEvWeight() {
		return this.evWeight;
	}

	public void setEvWeight(Integer evWeight) {
		this.evWeight = evWeight;
	}

	public Integer getGpiWeight() {
		return this.gpiWeight;
	}

	public void setGpiWeight(Integer gpiWeight) {
		this.gpiWeight = gpiWeight;
	}

	public Integer getAsiWeight() {
		return this.asiWeight;
	}

	public void setAsiWeight(Integer asiWeight) {
		this.asiWeight = asiWeight;
	}

	public Integer getDsiWeight() {
		return this.dsiWeight;
	}

	public void setDsiWeight(Integer dsiWeight) {
		this.dsiWeight = dsiWeight;
	}

	public Integer getCsiWeight() {
		return this.csiWeight;
	}

	public void setCsiWeight(Integer csiWeight) {
		this.csiWeight = csiWeight;
	}

	public Integer getIasWeight() {
		return this.iasWeight;
	}

	public void setIasWeight(Integer iasWeight) {
		this.iasWeight = iasWeight;
	}
}
