package com.awg.gpe.data.repositories;

import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.VGpeLeaderDashboard;

/**
 * Repositorio de la vista {@link VGpeLeaderDashboard}
 * 
 * @author Pablo Barrientos
 * @since 1.0
 *
 */
@Repository
public interface RepositoryVLeaderDashboard extends BaseRepository<VGpeLeaderDashboard, Long> {

	/**
	 * Devuelve los datos de la vista para un usuario determinado
	 * 
	 * @param user El usuario del que se quieren conocer los datos
	 * @return
	 * @version 1.0
	 * @since 1.0
	 */
    VGpeLeaderDashboard findByUser(TGpeMUser user);
	
}
