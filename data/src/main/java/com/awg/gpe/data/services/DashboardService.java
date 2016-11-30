package com.awg.gpe.data.services;

import com.awg.gpe.data.dto.AdminDashboard;
import com.awg.gpe.data.dto.DeveloperDashboard;
import com.awg.gpe.data.dto.LeaderDashboard;
import com.awg.gpe.data.dto.ManagerDashboard;
import com.awg.gpe.data.enums.EnumReportType;
import com.awg.gpe.data.enums.EnumTaskStatus;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.*;
import com.awg.gpe.data.repositories.*;
import com.google.gson.Gson;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase que se encarga de cargar los datos mostrados en los distintos dashboards
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Transactional
@Service
public class DashboardService {

	@Resource(name = "messageSource")
    protected MessageSource messages;
    
    protected String getMessage(String msgCode) {
        return this.messages.getMessage(msgCode, null, Locale.getDefault());
    }
    
    // Repositories needed ------------------------------------------------
    @Autowired
    private RepositoryMUser userRepository;
    
    @Autowired
    private RepositoryMProject projectRepository;
    
    @Autowired
    private RepositoryMTask taskRepository;
    
    @Autowired
    private RepositoryMRequirement requirementRepository;
    
    @Autowired
    private RepositoryMReport reportRepository;
    
    @Autowired
    private ServiceVLeaderDashboard leaderDashboardService;
    
    @Autowired
    private ServiceVManagerDashboard managerDashboardService;
    
    @Autowired
    private ServiceMVacation vacationService;
    
    // Methods ------------------------------------------------------------
    /**
     * Método que carga los datos para la pantalla del administrador del sistema
     * 
     * @return Un objeto {@link AdminDashboard} con los datos de la pantalla principal
     * @version 1.0
     * @since 1.0
     */
    public AdminDashboard adminDashboard() {
    	AdminDashboard res = new AdminDashboard();
    	res.setUsers(nUsers());
    	res.setProjects(nProjects());
    	res.setRequirements(nRequirements());
    	res.setTasks(nTasks());
    	res.setLastReports(reportList());
    	res.setScrumTasks(scrumTasks());
    	res.setMetrica3Tasks(metricaTasks());
    	return res;
    }
    
    /**
     * Método que devuelve los datos para la pantalla de jefe de proyecto
     * 
     * @param user Usuario para el que se quieren cargar los datos
     * @return Un objeto {@link LeaderDashboard} con los datos de la pantalla
     * @version 1.0
     * @since 1.0
     */
    public LeaderDashboard leaderDashboard(TGpeMUser user) {
    	LeaderDashboard res = new LeaderDashboard(user);
    	res.setProjectTasksChartsData(projectTasksChartsByUser(user));
    	res.setVacationsToApprove(getVacationsToApprove(user));
    	try {
			VGpeLeaderDashboard dashboard = this.leaderDashboardService.findByLeader(user);
			res.setDashboard(dashboard);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return res;
    }
    
    /**
     * Método que carga los datos para la pantalla de dashboard de un responsable de proyecto
     * 
     * @param user Usuario para el que se quieren cargar los datos
     * @return Un objeto {@link ManagerDashboard} con los datos del dashboard cargados
     * @version 1.0
     * @since 1.0
     */
    public ManagerDashboard managerDashboard(TGpeMUser user) {
    	ManagerDashboard res = new ManagerDashboard(user);
    	res.setProjectTasksChartsData(projectTasksChartsByUser(user));
    	res.setVacationsToApprove(getVacationsToApprove(user));
    	try {
			VGpeManagerDashboard dashboard = this.managerDashboardService.findByManager(user);
			res.setDashboard(dashboard);
		} catch (ServiceException e) {
			e.printStackTrace();
		}    	
    	return res;
    }
    
    public DeveloperDashboard developerDashboard(TGpeMUser user) {
    	DeveloperDashboard res = new DeveloperDashboard(user);
    	
    	return res;
    }
    
    /**
     * Método que ejecuta una acción sobre un determinado periodo de vacaciones
     * <p>
     * Este método solamente puede ser ejecutado por jefes de proyecto
     * </p>
     * 
     * @param vacation Periodo de vacaciones sobre el que se quiere ejecutar la acción
     * @param approved {@link Boolean} indicando si el responsable aprueba o no el periodo de vacaciones
     * @throws ServiceException
     * @version 1.0
     * @since 1.0
     */
    public void manageVacation(TGpeMVacation vacation, Boolean approved) throws ServiceException {
        this.vacationService.manageVacation(vacation, approved);
    }
    
    private Integer nUsers() {
    	QTGpeMUser user = QTGpeMUser.tGpeMUser;
    	BooleanBuilder predicate = new BooleanBuilder();
    	predicate.and(user.isEnabled.isTrue());
    	predicate.and(user.locked.isFalse());
    	return Long.valueOf(this.userRepository.count(predicate)).intValue();
    }
    
    private Integer nProjects() {
    	QTGpeMProject project = QTGpeMProject.tGpeMProject;
    	BooleanBuilder predicate = new BooleanBuilder();
    	predicate.and(project.deleted.isFalse());
    	predicate.and(project.archived.isFalse());
    	predicate.and(project.endDate.isNull());
    	return Long.valueOf(this.projectRepository.count(predicate)).intValue();
    }
    
    private Integer nRequirements() {
    	QTGpeMRequirement req = QTGpeMRequirement.tGpeMRequirement;
    	BooleanBuilder predicate = new BooleanBuilder();
    	predicate.and(req.archived.isFalse());
    	predicate.and(req.startDate.before(LocalDate.now()));
    	predicate.and(req.endDate.after(LocalDate.now()));
    	return Long.valueOf(this.requirementRepository.count(predicate)).intValue();
    }
    
    private Integer nTasks() {
    	QTGpeMTask task = QTGpeMTask.tGpeMTask;
    	BooleanBuilder predicate = new BooleanBuilder();
    	Long[] activeTasks = {3l, 8l, 12l, 13l};
    	predicate.and(task.taskStatus.id.in(Arrays.asList(activeTasks)));
    	return Long.valueOf(this.taskRepository.count(predicate)).intValue();
    }
    
    private List<TGpeMReport> reportList() {
    	List<TGpeMReport> res = new ArrayList<>();
    	Arrays.asList(EnumReportType.values()).forEach(t -> res.add(this.reportRepository.findFirstByTypeOrderByTimestamp(new TGpePReportType(t))));
    	return res;
    }
    
    public String scrumTasks() {
    	Collection<Long> statusIds = EnumTaskStatus.getScrum().stream().map(TGpePTaskStatus::getId).collect(Collectors.toList());
    	List<Object[]> tasks = this.taskRepository.findTaskCountByStatus(statusIds);

    	return getJsonForTaskChart(tasks);
    }
    
    public String metricaTasks() {
    	Collection<Long> statusIds = EnumTaskStatus.getMetrica3().stream().map(TGpePTaskStatus::getId).collect(Collectors.toList());
    	List<Object[]> tasks = this.taskRepository.findTaskCountByStatus(statusIds);

    	return getJsonForTaskChart(tasks);
    }
    
    /**
     * Método que devuelve un {@link String} en formato JSON con los datos de los gráficos del dashboard
     * 
     * @param user Usuario para el que se cargan los datos
     * @return Los datos de los gráficos
     * @version 1.0
     * @since 1.0
     */
    public String projectTasksChartsByUser(TGpeMUser user) {
    	Gson gson = new Gson();
    	List<Object[]> res = new ArrayList<>();
    	Collection<TGpeMProject> projects = new ArrayList<>();
    	if (user.isProjectLeader()) {
    		if (!user.getProjectList().isEmpty()) {
    			projects = user.getProjectList();
    		}
    	} else if (user.isProjectManager()) {
    		if (!user.getProjectsManaged().isEmpty()) {
    			projects = user.getProjectsManaged();
    		}
    	}
    	
    	if (!projects.isEmpty()) {
    		Collection<Long> scrumStatusIds = EnumTaskStatus.getScrum().stream().map(TGpePTaskStatus::getId).collect(Collectors.toList());
    		Collection<Long> metrica3StatusIds = EnumTaskStatus.getMetrica3().stream().map(TGpePTaskStatus::getId).collect(Collectors.toList());
    		projects.forEach(p -> {
    			List<Object[]> tasks = null;
    			
    			if (p.isMetricaV3()) {
    				tasks = this.taskRepository.findTaskCountByStatusAndProject(p.getId(), metrica3StatusIds);
    			} else if (p.isScrum()) {
    				tasks = this.taskRepository.findTaskCountByStatusAndProject(p.getId(), scrumStatusIds);
    			}
    			Object[] pChart = { p.getId().toString(), 
    					gson.fromJson(getJsonForTaskChart(tasks), Object[].class), 
    					gson.fromJson(getJsonForGanttChartByProject(p), Object[].class) };
    			
    			res.add(pChart);
    		});
    	}
    	return gson.toJson(res);
    }
    
    /**
     * 
     * @param tasks [Task Status Description, Number of tasks, Color]
     * @return
     */
    private String getJsonForTaskChart(List<Object[]> tasks) {
    	if (tasks != null) {
    		List<List<Object>> res = new ArrayList<>();
        	List<Object> colors = new ArrayList<>();
        	Gson gson = new Gson();
        	
        	tasks.stream().map(o -> {
        		List<Object> list = new ArrayList<>();
        		list.add(this.messages.getMessage((String)o[0], null, Locale.getDefault()));
        		list.add(o[1]);
        		colors.add(o[2]);
        		return list;
        	}).forEach(l -> res.add(l));
        	
        	res.add(colors);
        	return gson.toJson(res);
    	} else {
    		return "";
    	}
    	
    }
    
    private String getJsonForGanttChartByProject(TGpeMProject project) {
    	if (project != null) {
    		List<List<Object>> res = new ArrayList<>();
    		Gson gson = new Gson();
    		
    		List<Object[]> tasks = this.taskRepository.findGanttDataByProject(project.getId());
    		if (tasks != null && !tasks.isEmpty()) {
    			tasks.stream().map(o -> {
    				List<Object> list = new ArrayList<>();
    				Calendar startDate = Calendar.getInstance();
    				startDate.setTimeInMillis(((Timestamp)o[3]).getTime());
    				Calendar endDate = Calendar.getInstance();
    				endDate.setTimeInMillis(((Timestamp)o[4]).getTime());
    				list.add(String.valueOf(o[0]));
    				list.add(o[1]);
    				list.add(o[2]);
    				list.add(startDate);
    				list.add(endDate);
    				list.add(endDate.getTimeInMillis() - startDate.getTimeInMillis());
    				list.add(null);
    				list.add(o[5]);
    				return list;
    			}).forEach(l -> res.add(l));
    		}
    		
    		return gson.toJson(res);
    	} else {
    		return "";
    	}
    }
    
    private List<TGpeMVacation> getVacationsToApprove(TGpeMUser user) {
    	List<TGpeMVacation> res = new ArrayList<>();
    	try {
    		res = this.vacationService.getVacationsToApprove(user);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    	return res;
    }
	
}
