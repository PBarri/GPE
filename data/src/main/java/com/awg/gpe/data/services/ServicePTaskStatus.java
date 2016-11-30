package com.awg.gpe.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpePTaskStatus;
import com.awg.gpe.data.repositories.RepositoryPTaskStatus;

/**
 * Servicio de la entidad {@link TGpePTaskStatus}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServicePTaskStatus extends BaseParametricsService<TGpePTaskStatus, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryPTaskStatus repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public CrudRepository<TGpePTaskStatus, Long> getRepository() {
        return this.repository;
    }

    // Custom methods ------------------------------------------------

}
