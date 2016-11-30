package com.awg.gpe.data.services;

import java.util.Map;

import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.filters.TGpeMAbsenceFilters;
import com.awg.gpe.data.model.QTGpeMAbsence;
import com.awg.gpe.data.model.TGpeMAbsence;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMAbsence;
import com.querydsl.core.types.Predicate;

/**
 * Servicio de la entidad {@link TGpeMAbsence}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMAbsence extends BaseService<TGpeMAbsence, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMAbsence repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMAbsence, Long> getRepository() {
        return this.repository;
    }

    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
       	QTGpeMAbsence absence = QTGpeMAbsence.tGpeMAbsence;
       	BooleanBuilder predicate = new BooleanBuilder();
       	
       	filters.forEach((key, value) -> {
       		switch(key) {
       		case TGpeMAbsenceFilters.USER_FILTER:
       			predicate.and(absence.user.eq((TGpeMUser) value));
       			break;
       		case TGpeMAbsenceFilters.YEAR_FILTER:
       			predicate.and(absence.startDate.year().eq((Integer) value));
       			break;
       		case TGpeMAbsenceFilters.HIST_YEAR_FILTER:
       			predicate.and(absence.startDate.year().lt((Integer) value));
       			break;
   			default:
   				break;
       		}
       	});
       	
       	return predicate;
    }

    // Custom methods ------------------------------------------------

}
