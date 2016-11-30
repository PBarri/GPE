package com.awg.gpe.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.model.TGpePPublicHolidays;
import com.awg.gpe.data.repositories.RepositoryPPublicHolidays;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServicePPublicHolidays extends BaseParametricsService<TGpePPublicHolidays, Long> {

    @Autowired
    private RepositoryPPublicHolidays repository;
    
    /**
     * @see com.awg.gpe.data.services.BaseParametricsService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public CrudRepository<TGpePPublicHolidays, Long> getRepository() {
        return this.repository;
    }

}
