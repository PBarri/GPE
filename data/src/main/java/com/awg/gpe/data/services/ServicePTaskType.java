package com.awg.gpe.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpePTaskType;
import com.awg.gpe.data.repositories.RepositoryPTaskType;

/**
 * Servicio de la entidad {@link TGpePTaskType}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServicePTaskType extends BaseParametricsService<TGpePTaskType, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryPTaskType repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public CrudRepository<TGpePTaskType, Long> getRepository() {
        return this.repository;
    }

    // Custom methods ------------------------------------------------

}
