package com.awg.gpe.data.services;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMIncurred;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMIncurred;
import com.querydsl.core.types.Predicate;

/**
 * Servicio correspondiente a la entidad {@link TGpeMIncurred}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMIncurred extends BaseService<TGpeMIncurred, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMIncurred repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMIncurred, Long> getRepository() {
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
    
    /**
     * Método que crea una imputación en la base de datos
     * 
     * @param incurred Imputación a guardar
     * @throws ServiceException En el caso de que exista algún problema en la transacción
     * @version 1.0
     * @since 1.0
     */
    public void createIncurred(TGpeMIncurred incurred) throws ServiceException {
    	incurred.setTimestamp(LocalDateTime.now());
    	save(incurred);
    }
}
