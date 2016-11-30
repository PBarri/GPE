package com.awg.gpe.data.dto;

import java.util.List;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpeMVacation;
import com.awg.gpe.data.model.VGpeManagerDashboard;

/**
 * DTO que agrupa los datos que se muestran en la pantalla de responsable de proyecto
 * 
 * @author Pablo
 * @version 1.0
 * @since 1.0
 *
 */
public class ManagerDashboard {

	private TGpeMUser user;
	
	private VGpeManagerDashboard dashboard;
	
	private String projectTasksChartsData;
	
	private List<TGpeMVacation> vacationsToApprove;

	/**
	 * @param user
	 */
	public ManagerDashboard(TGpeMUser user) {
		this.user = user;
	}

	
	public TGpeMUser getUser() {
		return this.user;
	}

	public void setUser(TGpeMUser user) {
		this.user = user;
	}


	public VGpeManagerDashboard getDashboard() {
		return this.dashboard;
	}


	public void setDashboard(VGpeManagerDashboard dashboard) {
		this.dashboard = dashboard;
	}


	public String getProjectTasksChartsData() {
		return this.projectTasksChartsData;
	}


	public void setProjectTasksChartsData(String projectTasksChartsData) {
		this.projectTasksChartsData = projectTasksChartsData;
	}


	public List<TGpeMVacation> getVacationsToApprove() {
		return this.vacationsToApprove;
	}


	public void setVacationsToApprove(List<TGpeMVacation> vacationsToApprove) {
		this.vacationsToApprove = vacationsToApprove;
	}
}
