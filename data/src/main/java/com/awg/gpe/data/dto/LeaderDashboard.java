package com.awg.gpe.data.dto;

import java.util.List;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpeMVacation;
import com.awg.gpe.data.model.VGpeLeaderDashboard;

/**
 * DTO que agrupa los datos que se muestran en la pantalla de dashboard del jefe de proyecto
 * 
 * @author Pablo
 * @version 1.0
 * @since 1.0
 *
 */
public class LeaderDashboard {
	
	private TGpeMUser user;
	
	private VGpeLeaderDashboard dashboard;
	
	private String projectTasksChartsData;
	
	private List<TGpeMVacation> vacationsToApprove;
	
	/**
	 * @param user
	 */
	public LeaderDashboard(TGpeMUser user) {
		this.user = user;
	}
	
	public TGpeMUser getUser() {
		return this.user;
	}

	public void setUser(TGpeMUser user) {
		this.user = user;
	}

	public VGpeLeaderDashboard getDashboard() {
		return this.dashboard;
	}

	public void setDashboard(VGpeLeaderDashboard dashboard) {
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
