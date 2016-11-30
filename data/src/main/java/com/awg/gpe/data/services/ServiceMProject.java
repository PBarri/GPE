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

import com.awg.gpe.data.enums.EnumMethodology;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMProjectFilter;
import com.awg.gpe.data.model.QTGpeMProject;
import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpePMethodology;
import com.awg.gpe.data.model.TGpeRHistoricalProjects;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMProject;
import com.awg.gpe.data.repositories.RepositoryMUser;
import com.awg.gpe.data.repositories.RepositoryRHistoricalProjects;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * Servicio de la entidad {@link TGpeMProject}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMProject extends BaseService<TGpeMProject, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMProject repository;
    
    @Autowired
    private RepositoryMUser userRepository;
    
    @Autowired
    private RepositoryRHistoricalProjects historicalRepository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMProject, Long> getRepository() {
        return this.repository;
    }
    
    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
        QTGpeMProject project = QTGpeMProject.tGpeMProject;
        BooleanBuilder predicate = new BooleanBuilder();

        filters.forEach((key, value) -> {
            switch (key) {
                case TGpeMProjectFilter.FILTER_CODE:
                    predicate.and(project.projectCode.eq((String) value));
                    break;
                case TGpeMProjectFilter.FILTER_NAME:
                    predicate.and(project.name.eq((String) value));
                    break;
                case TGpeMProjectFilter.FILTER_METHODOLOGY:
                    predicate.and(project.methodology.code.eq(((EnumMethodology) value).getCode()));
                    break;
                case TGpeMProjectFilter.FILTER_PROJECT_LEADER:
                    predicate.and(project.leader.eq((TGpeMUser) value));
                    break;
                case TGpeMProjectFilter.FILTER_PROJECT_MANAGER:
                    predicate.and(project.managers.contains((TGpeMUser) value));
                    break;
                case TGpeMProjectFilter.FILTER_PRODUCT_OWNER:
                    predicate.and(project.productOwner.eq((TGpeMUser) value));
                    break;
                case TGpeMProjectFilter.FILTER_SCRUM_MASTER:
                    predicate.and(project.scrumMaster.eq((TGpeMUser) value));
                    break;
                case TGpeMProjectFilter.FILTER_START_DATE:
                    predicate.and(project.startDate.after((LocalDate) value));
                    break;
                case TGpeMProjectFilter.FILTER_END_DATE:
                    predicate.and(project.endDate.before((LocalDate) value));
                    break;
                default:
                    break;
            }
        });

        predicate.and(project.deleted.isFalse());
        
        return predicate;
    }

    // Custom methods ------------------------------------------------
    
    /**
     * @return El máximo ID asociado a un proyecto en la base de datos
     * @throws ServiceException
     * @version 1.0
     * @since 1.0
     */
    public Long findMaxId() throws ServiceException {
    	Long maxId = this.repository.findMaxId();
    	
    	return (maxId != null) ? maxId : 0l;
    }
    
    /**
     * Método que inicializa todas las colecciones de un determinado proyecto para traer su información completa
     * 
     * @param project Proyecto del que se requiere la información
     * @return El proyecto con todas las colecciones inicializadas
     * @throws ServiceException En el caso de que ocurra algún error
     * @version 1.0
     * @since 1.0
     */
    public TGpeMProject findCompleteProject(TGpeMProject project) throws ServiceException {
        TGpeMProject res = this.repository.findOne(project.getId());
        
        Hibernate.initialize(res.getDocuments());
        Hibernate.initialize(res.getManagers());
        res.getRequeriments().forEach(req -> Hibernate.initialize(req.getTasks()));
        Hibernate.initialize(res.getServers());
        Hibernate.initialize(res.getHistoricalUsers());
        
        return res;
    }
    
    
    /**
     * Método que devuelve una lista de proyectos asociados al usuario, independientemente de su rol
     * 
     * @param user
     *            El usuario
     * @return Una lista con los proyectos asociados al usuario
     * @throws ServiceException En el caso de que se produzca algún error
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMProject> findByUser(TGpeMUser user) throws ServiceException {
        List<TGpeMProject> res = new ArrayList<>();

        if (user != null) {
            if (user.isAdministrator()) {
                res = this.repository.findAll();
            } else if (user.isProjectLeader()) {
                res = this.repository.findByLeader(user);
            } else if (user.isProjectManager()) {
                res = this.repository.findByManagers(user);
            }
        }

        return res;
    }
    
    /**
     * Método que crea un nuevo proyecto. Se inicializarán los siguientes campos:
     * <ul>
     * <li>timestamp - A la fecha actual</li>
     * <li>deleted - A false</li>
     * <li>createdBy - Al identificador del usuario que ha creado el proyecto</li>
     * <li>lastEditionBy - Al identificador del usuario que ha creado el proyecto</li>
     * </ul>
     * @param project Proyecto a crear
     * @throws ServiceException En el caso de que ocurra algún error
     * @version 1.0
     * @since 1.0
     */
    @Transactional
    public void createProject(TGpeMProject project, List<TGpeMUser> usersToSave) throws ServiceException {
        if (project != null) {
            TGpeMUser actualUser = LoginService.getPrincipal();
            
            project.setTimestamp(LocalDateTime.now());
            project.setDeleted(false);
            project.setCreatedBy(actualUser.getIdentifier());
            project.setLastEditionBy(actualUser.getIdentifier());
            
            // Guardamos el proyecto en la base de datos
            save(project);
            
            if (usersToSave != null && !usersToSave.isEmpty()) {
            	List<TGpeRHistoricalProjects> histProjects = new ArrayList<>();
            	usersToSave.forEach(u -> {
            		TGpeRHistoricalProjects h = u.addHistoricalProject(project, project.getRequeriments().iterator().next());
            		h.setTimestamp(LocalDateTime.now());
            		if (h != null) {
            			histProjects.add(h);
            		}
            	});
                this.userRepository.save(usersToSave);
                this.historicalRepository.save(histProjects);
            }            
        }
    }
    
    /**
     * Método que genera un nuevo {@link TGpeMProject} a partir de una metodología
     * 
     * @param methodology metodología a partir de la que se pretende crear el proyecto
     * @return el nuevo objeto {@link TGpeMProject}
     * @version 1.0
     * @since 1.0
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TGpeMProject newProject(EnumMethodology methodology) {
        TGpeMProject project = new TGpeMProject();
        TGpeMUser user = LoginService.getPrincipal();
        
        project.setArchived(false);
        project.setDeleted(false);
        project.setCreatedBy(user.getIdentifier());
        project.setLastEditionBy(user.getIdentifier());
        project.setMethodology(new TGpePMethodology(methodology));
        
        return project;
    }
}