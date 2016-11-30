package com.awg.gpe.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpePPriority;
import com.awg.gpe.data.repositories.RepositoryPPriority;

/**
 * Servicio de la entidad {@link TGpePPriority}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServicePPriority extends BaseParametricsService<TGpePPriority, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryPPriority repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public CrudRepository<TGpePPriority, Long> getRepository() {
        return this.repository;
    }

    // Custom methods ------------------------------------------------

}
