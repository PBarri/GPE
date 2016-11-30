package com.awg.gpe.data.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMVacationFilters;
import com.awg.gpe.data.model.QTGpeMVacation;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpeMVacation;
import com.awg.gpe.data.model.TGpePPublicHolidays;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMVacation;
import com.awg.gpe.data.repositories.RepositoryPPublicHolidays;
import com.awg.gpe.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * Servicio de la entidad {@link TGpeMVacation}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMVacation extends BaseService<TGpeMVacation, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMVacation repository;
    
    @Autowired
    private RepositoryPPublicHolidays holidaysRepository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMVacation, Long> getRepository() {
        return this.repository;
    }

    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
        QTGpeMVacation vacation = QTGpeMVacation.tGpeMVacation;
        BooleanBuilder predicate = new BooleanBuilder();
        
        filters.forEach((key, value) -> {
        	switch (key) {
        	case TGpeMVacationFilters.USER_FILTER:
        		predicate.and(vacation.user.eq((TGpeMUser) value));
        		break;
        	case TGpeMVacationFilters.YEAR_FILTER:
        		predicate.and(vacation.year.eq((Integer) value));
        		break;
        	case TGpeMVacationFilters.HIST_YEAR_FILTER:
        		predicate.and(vacation.year.lt((Integer) value));
        		break;
        	default:
        		break;
        	}
        });
        
        return predicate;
    }

    // Custom methods ------------------------------------------------
    /**
     * Método que crea un nuevo periodo vacacional. Se le informan las marcas de aprobada y gestionada a falso.
     * 
     * @param vacation Periodo de vacaciones
     * @throws ServiceException En el caso de que ocurra algún error
     * @version 1.0
     * @since 1.0
     */
    public void createVacation(TGpeMVacation vacation) throws ServiceException {
        vacation.setApproved(false);
        vacation.setManaged(false);
        save(vacation);
    }
    
    /**
     * Método que devuelve el número de días que un usuario ha dispuesto en un año.
     * Este método cuenta los días naturales.
     * 
     * @param user Usuario del que se quieren contar los días de vacaciones
     * @return El número de días usado
     * @throws ServiceException En el caso de que ocurra algún error.
     * @version 1.0
     * @since 1.0
     */
    public Integer countActualVacations(TGpeMUser user) throws ServiceException {
    	List<TGpeMVacation> actualVacations = getActualVacations(user).stream()
    			.filter(v -> (v.getStartDate().isBefore(LocalDate.now()) && v.getApproved() == true)
    					||
                        v.getStartDate().isAfter(LocalDate.now()))
    			.collect(Collectors.toList());
    	if (!actualVacations.isEmpty()) {
    		List<LocalDate> bankHolidays = this.holidaysRepository.findAll().stream().map(TGpePPublicHolidays::getDay).collect(Collectors.toList());
        	return actualVacations.stream().mapToInt(v -> DateUtils.businessDaysBetween(v.getStartDate(), v.getEndDate(), bankHolidays)).sum();
    	} else {
    		return 0;
    	}    	
    }
    
    /**
     * @param vacation
     * @return
     * @throws ServiceException
     * @version 1.0
     * @since 1.0
     */
    public Integer countVacationDays(TGpeMVacation vacation) throws ServiceException {
    	List<LocalDate> bankHolidays = this.holidaysRepository.findAll().stream().map(TGpePPublicHolidays::getDay).collect(Collectors.toList());
    	return DateUtils.businessDaysBetween(vacation.getStartDate(), vacation.getEndDate(), bankHolidays);
    }
    
    /**
     * @param user
     * @return
     * @throws ServiceException
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMVacation> getActualVacations(TGpeMUser user) throws ServiceException {
    	QTGpeMVacation vacation = QTGpeMVacation.tGpeMVacation;
        BooleanBuilder predicate = new BooleanBuilder();
        
        predicate.and(vacation.user.eq(user));
        predicate.and(vacation.year.eq(LocalDate.now().getYear()));
        
        return this.repository.findAll(predicate);
    }
    
    /**
     * @param user
     * @return
     * @throws ServiceException
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMVacation> getVacationsToApprove(TGpeMUser user) throws ServiceException {
    	List<TGpeMVacation> res = new ArrayList<>();
    	
    	if (user.isProjectLeader()) {
    		res = this.repository.getVacationsToApproveByLeader(user.getId());
    	} else if (user.isProjectManager()) {
    		res = this.repository.getVacationsToApproveByManager(user.getId());
    	}
    	
    	return res;
    }
    
    /**
     * @param vacation
     * @param approved
     * @throws ServiceException
     * @version 1.0
     * @since 1.0
     */
    public void manageVacation(TGpeMVacation vacation, Boolean approved) throws ServiceException {
    	vacation.setManaged(true);
    	vacation.setApproved(approved);
    	save(vacation);
    }
}
