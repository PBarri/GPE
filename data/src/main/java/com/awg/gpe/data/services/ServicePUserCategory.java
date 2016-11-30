package com.awg.gpe.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpePUserCategory;
import com.awg.gpe.data.repositories.RepositoryPUserCategory;

/**
 * Servicio de la entidad {@link TGpePUserCategory}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServicePUserCategory extends BaseParametricsService<TGpePUserCategory, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryPUserCategory repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public CrudRepository<TGpePUserCategory, Long> getRepository() {
        return this.repository;
    }

    // Custom methods ------------------------------------------------

}
