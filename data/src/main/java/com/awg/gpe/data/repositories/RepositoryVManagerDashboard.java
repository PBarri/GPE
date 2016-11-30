package com.awg.gpe.data.repositories;

import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.VGpeManagerDashboard;

/**
 * Repositorio de la vista {@link VGpeManagerDashboard}
 * 
 * @author Pablo Barrientos
 * @since 1.0
 *
 */
@Repository
public interface RepositoryVManagerDashboard extends BaseRepository<VGpeManagerDashboard, Long> {

	/**
	 * Método que devuelve los datos de la vista para un determinado responsable de proyecto
	 * 
	 * @param user El responsable del que se quieren conocer los datos
	 * @return
	 * @version 1.0
	 * @since 1.0
	 */
    VGpeManagerDashboard findByUser(TGpeMUser user);
	
}
