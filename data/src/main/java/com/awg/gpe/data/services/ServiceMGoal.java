package com.awg.gpe.data.services;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.awg.gpe.data.enums.EnumPriority;
import com.awg.gpe.data.model.TGpeMGoal;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMGoal;
import com.querydsl.core.types.Predicate;

/**
 * Servicio correspondiente a la entidad {@link TGpeMGoal}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMGoal extends BaseService<TGpeMGoal, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMGoal repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMGoal, Long> getRepository() {
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
     * Método que crea el objetivo inicial del proyecto
     * @return El objetivo inicial
     * @version 1.0
     * @since 1.0
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TGpeMGoal createInitGoal() {
        TGpeMGoal goal = new TGpeMGoal();
        goal.setAchieved(false);
        goal.setDescription("Construcción inicial del proyecto");
        goal.setPriority(EnumPriority.CRITICAL);
        goal.setTimestamp(LocalDateTime.now());
        goal.setTitle("Construcción");
        return goal;
    }
}
