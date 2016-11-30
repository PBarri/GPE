package com.awg.gpe.data.dto;

import java.util.List;

import com.awg.gpe.data.model.TGpeMReport;

/**
 * DTO que agrupa los datos mostrados en la pantalla de dashboard del administrador del sistema.
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class AdminDashboard {

	public AdminDashboard() {  }
	
	private Integer users;
	
	private Integer projects;
	
	private Integer requirements;
	
	private Integer tasks;
	
	private List<TGpeMReport> lastReports;	

	private String scrumTasks;
	
	private String metrica3Tasks;
	
	public Integer getUsers() {
		return this.users;
	}

	public void setUsers(Integer users) {
		this.users = users;
	}

	public Integer getProjects() {
		return this.projects;
	}

	public void setProjects(Integer projects) {
		this.projects = projects;
	}

	public Integer getRequirements() {
		return this.requirements;
	}

	public void setRequirements(Integer requirements) {
		this.requirements = requirements;
	}

	public Integer getTasks() {
		return this.tasks;
	}

	public void setTasks(Integer tasks) {
		this.tasks = tasks;
	}

	public List<TGpeMReport> getLastReports() {
		return this.lastReports;
	}

	public void setLastReports(List<TGpeMReport> lastReports) {
		this.lastReports = lastReports;
	}

	public String getScrumTasks() {
		return this.scrumTasks;
	}

	public void setScrumTasks(String scrumTasks) {
		this.scrumTasks = scrumTasks;
	}

	public String getMetrica3Tasks() {
		return this.metrica3Tasks;
	}

	public void setMetrica3Tasks(String metrica3Tasks) {
		this.metrica3Tasks = metrica3Tasks;
	}
	
	
	
}
