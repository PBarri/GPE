package com.awg.gpe.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpePDocumentType;
import com.awg.gpe.data.repositories.RepositoryPDocumentType;

/**
 * Servicio de la entidad {@link TGpePDocumentType}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServicePDocumentType extends BaseParametricsService<TGpePDocumentType, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryPDocumentType repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public CrudRepository<TGpePDocumentType, Long> getRepository() {
        return this.repository;
    }

    // Custom methods ------------------------------------------------

}
