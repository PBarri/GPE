package com.awg.gpe.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpePMethodology;
import com.awg.gpe.data.repositories.RepositoryPMethodology;

/**
 * Servicio de la entidad {@link TGpePMethodology}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServicePMethodology extends BaseParametricsService<TGpePMethodology, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryPMethodology repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public CrudRepository<TGpePMethodology, Long> getRepository() {
        return this.repository;
    }

    // Custom methods ------------------------------------------------

}
