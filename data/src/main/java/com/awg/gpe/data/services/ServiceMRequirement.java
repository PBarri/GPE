package com.awg.gpe.data.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.awg.gpe.data.enums.EnumSprintDuration;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMRequirementFilters;
import com.awg.gpe.data.model.QTGpeMRequirement;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMRequirement;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMRequirement;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * Servicio de la entidad {@link TGpeMRequirement}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMRequirement extends BaseService<TGpeMRequirement, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMRequirement repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMRequirement, Long> getRepository() {
        return this.repository;
    }
    
    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
        QTGpeMRequirement req = QTGpeMRequirement.tGpeMRequirement;
        BooleanBuilder predicate = new BooleanBuilder();

        filters.forEach((key, value) -> {
            switch (key) {
                case TGpeMRequirementFilters.FILTER_CODE:
                    predicate.and(req.requirementCode.eq((String) value));
                    break;
                case TGpeMRequirementFilters.FILTER_NAME:
                    predicate.and(req.name.eq((String) value));
                    break;
                case TGpeMRequirementFilters.FILTER_PROJECT:
                    predicate.and(req.project.eq((TGpeMProject) value));
                    break;
                case TGpeMRequirementFilters.FILTER_ARCHIVED:
                    predicate.and(req.archived.eq((Boolean) value));
                    break;
                case TGpeMRequirementFilters.FILTER_START_DATE:
                    predicate.and(req.startDate.after((LocalDate) value));
                    break;
                case TGpeMRequirementFilters.FILTER_END_DATE:
                    predicate.and(req.endDate.before((LocalDate) value));
                    break;
                default:
                    break;
            }
        });
        return predicate;
    }

    // Custom methods ------------------------------------------------    
    
    /**
     * Método que busca un requerimiento e inicializa todas las colecciones con los datos de la base de datos
     * 
     * @param requirement El requerimiento que se quiere buscar
     * @return El requerimiento con todos los datos
     * @throws ServiceException En el caso de que se produzca un error
     * @version 1.0
     * @since 1.0
     */
    public TGpeMRequirement findCompleteRequirement(TGpeMRequirement requirement) throws ServiceException {
        TGpeMRequirement req = this.repository.findOne(requirement.getId());
        
        Hibernate.initialize(req.getDevelopers());
        Hibernate.initialize(req.getDocuments());
        Hibernate.initialize(req.getGoals());
        Hibernate.initialize(req.getTasks());
        
        return req;
    }
    
    /**
     * Método que devuelve todos los requerimientos asociados a un proyecto determinado
     * @param project El proyecto del que queremos saber sus requerimientos
     * @return La lista de requerimientos del proyecto
     * @throws ServiceException En el caso de que se produzca una excepción
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMRequirement> findByProject(TGpeMProject project) throws ServiceException {
        return this.repository.findByProject(project);
    }
    
    /**
     * Método que devuelve un listado de los requerimientos a los que el usuario puede acceder
     * 
     * @param user Usuario
     * @return La lista de los requerimientos
     * @throws ServiceException En el caso de que ocurra un error
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMRequirement> findByUser(TGpeMUser user) throws ServiceException {
        List<TGpeMRequirement> res = new ArrayList<>();
        if (user.isAdministrator()) {
            res = this.repository.findAll();
        } else if (user.isProjectLeader()) {
            res = this.repository.findByProjectLeader(user);
        } else if (user.isProjectManager()) {
            res = this.repository.findByProjectManagers(user);
        }
        
        return res;
    }
    
    
    /**
     * Método que crea el requerimiento inicial para los proyectos
     * 
     * @param project Proyecto al que va dirigido el requerimiento
     * @return El requerimiento inicial
     * @version 1.0
     * @since 1.0
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TGpeMRequirement createNewRequirement(TGpeMProject project) {
        TGpeMRequirement req = new TGpeMRequirement();
        
        TGpeMUser user = LoginService.getPrincipal();
        req.setCreatedBy(user);
        req.setLastEditionBy(user);
        req.setTimestamp(LocalDateTime.now());
        req.setArchived(false);
        req.setName(getMessage("requirement.initial.name"));
        
        return req;
    }
    
    /**
     * Método que crea el sprint inicial para los proyectos de tipo scrum
     * 
     * @param project Proyecto al que va dirigido el requerimiento
     * @return El sprint inicial
     * @version 1.0
     * @since 1.0
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TGpeMRequirement createNewSprint(TGpeMProject project) {
        TGpeMRequirement req = new TGpeMRequirement();
        
        TGpeMUser user = LoginService.getPrincipal();
        req.setCreatedBy(user);
        req.setLastEditionBy(user);
        req.setTimestamp(LocalDateTime.now());
        req.setArchived(false);
        req.setName(getMessage("sprint.initial.name"));
        
        return req;
    }
    
    /**
     * Método que calcula la fecha de fin en los proyectos de Scrum, basado en la duración de la iteración
     * 
     * @param requirement Requerimiento del que se quiere calcular la fecha de fin
     * @param sprintDuration Duración del sprint
     * @return La fecha de fin de la iteración
     * @version 1.0
     * @since 1.0
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public LocalDate calculateEndDate(TGpeMRequirement requirement, EnumSprintDuration sprintDuration) {
    	LocalDate endTime = null;
    	
    	if (sprintDuration.equals(EnumSprintDuration.ONE_WEEK)) {
    		endTime = requirement.getStartDate().plusWeeks(1);
    	} else if (sprintDuration.equals(EnumSprintDuration.TWO_WEEKS)) {
    		endTime = requirement.getStartDate().plusWeeks(2);
    	} else if (sprintDuration.equals(EnumSprintDuration.THREE_WEEKS)) {
    		endTime = requirement.getStartDate().plusWeeks(3);
    	} else if (sprintDuration.equals(EnumSprintDuration.ONE_MONTH)) {
    		endTime = requirement.getStartDate().plusMonths(1);
    	}
    	
    	return endTime;
    }
    
}
