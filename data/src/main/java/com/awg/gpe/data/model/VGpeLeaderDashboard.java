package com.awg.gpe.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Clase que modela la vista de los datos del dashboard del jefe de proyecto 
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "V_GPE_LEADER_DASHBOARD")
public class VGpeLeaderDashboard extends BaseViewEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase VGpeLeaderDashboard.java
     * 
     * @since 1.0
     */
    public VGpeLeaderDashboard() {
    }
	
    
    // 	Attributes -------------------------------------------------------------
        
    @OneToOne
    @JoinColumn(name = "LEADER", referencedColumnName = "ID")
	private TGpeMUser user;
	
	private Long projects;
	
	private Long requirements;
	
	private Long managers;
	
	private Long developers;
	
	@Column(name = "ACTIVE_TASKS")
	private Long activeTasks;
	
	@Column(name = "BLOCKED_TASKS")
	private Long blockedTasks;
	
	@Column(name = "DELAYED_TASKS")
	private Long delayedTasks;
	
	@Column(name = "STOPPED_TASKS")
	private Long stoppedTasks;

	// Getters and Setters ----------------------------------------------------
	
	public TGpeMUser getUser() {
		return this.user;
	}

	public void setUser(TGpeMUser user) {
		this.user = user;
	}

	public Long getProjects() {
		return this.projects;
	}

	public void setProjects(Long projects) {
		this.projects = projects;
	}

	public Long getRequirements() {
		return this.requirements;
	}

	public void setRequirements(Long requirements) {
		this.requirements = requirements;
	}

	public Long getManagers() {
		return this.managers;
	}

	public void setManagers(Long managers) {
		this.managers = managers;
	}

	public Long getDevelopers() {
		return this.developers;
	}

	public void setDevelopers(Long developers) {
		this.developers = developers;
	}

	public Long getActiveTasks() {
		return this.activeTasks;
	}

	public void setActiveTasks(Long activeTasks) {
		this.activeTasks = activeTasks;
	}

	public Long getBlockedTasks() {
		return this.blockedTasks;
	}

	public void setBlockedTasks(Long blockedTasks) {
		this.blockedTasks = blockedTasks;
	}

	public Long getDelayedTasks() {
		return this.delayedTasks;
	}

	public void setDelayedTasks(Long delayedTasks) {
		this.delayedTasks = delayedTasks;
	}

	public Long getStoppedTasks() {
		return this.stoppedTasks;
	}

	public void setStoppedTasks(Long stoppedTasks) {
		this.stoppedTasks = stoppedTasks;
	}
	
	

}
