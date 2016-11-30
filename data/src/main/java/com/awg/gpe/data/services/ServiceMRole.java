package com.awg.gpe.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpeMRole;
import com.awg.gpe.data.repositories.RepositoryMRole;

/**
 * Servicio de la entidad {@link TGpeMRole}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMRole extends BaseParametricsService<TGpeMRole, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMRole repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public CrudRepository<TGpeMRole, Long> getRepository() {
        return this.repository;
    }

    // Custom methods ------------------------------------------------

}
