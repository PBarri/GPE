package com.awg.gpe.data.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpeMToDo;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMToDo;
import com.querydsl.core.types.Predicate;

/**
 * Servicio de la entidad {@link TGpeMToDo}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMToDo extends BaseService<TGpeMToDo, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMToDo repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMToDo, Long> getRepository() {
        return this.repository;
    }

    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
        return null;
    }

    // Custom methods ------------------------------------------------
}
