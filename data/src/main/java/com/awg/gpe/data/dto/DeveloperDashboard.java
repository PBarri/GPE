package com.awg.gpe.data.dto;

import com.awg.gpe.data.model.TGpeMUser;

/**
 * DTO que agrupa los datos que se muestran en la pantalla principal de un desarrollador.
 * 
 * @author Pablo
 * @version 1.0
 * @since 1.0
 *
 */
public class DeveloperDashboard {
	
	private TGpeMUser user;
	
	/**
	 * @param user
	 */
	public DeveloperDashboard(TGpeMUser user) {
		this.user = user;
	}
	
	
	public TGpeMUser getUser() {
		return this.user;
	}

	public void setUser(TGpeMUser user) {
		this.user = user;
	}

}
