package com.awg.gpe.data.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.VGpeManagerDashboard;
import com.awg.gpe.data.repositories.RepositoryVManagerDashboard;

/**
 * 
 * @author Pablo Barrientos
 * @since 1.0
 *
 */
@Service
@Transactional
public class ServiceVManagerDashboard {
	
	@Autowired
	private RepositoryVManagerDashboard repository;
	
	// Methods ----------------------------------------------------------------------
	
	/**
	 * Método que devuelve los datos de la vista correspondientes al usuario seleccionado
	 * @param user Usuario del que se quieren conocer los datos
	 * @return Los datos de la vista
	 * @throws ServiceException En el caso de que ocurra una excepción
	 * @version 1.0
	 * @since 1.0
	 */
	public VGpeManagerDashboard findByManager(TGpeMUser user) throws ServiceException {
		return this.repository.findByUser(user);
	}
	
	/**
	 * Método que devuelve todos los datos contenidos en la vista
	 * @return Los datos de la vista
	 * @throws ServiceException En el caso de que ocurra una excepción
	 * @version 1.0
	 * @since 1.0
	 */
	public Iterable<VGpeManagerDashboard> findAll() throws ServiceException {
		return this.repository.findAll();
	}

}
