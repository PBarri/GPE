package com.awg.gpe.data.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.awg.gpe.data.enums.EnumRoles;
import com.awg.gpe.data.enums.EnumUserPosition;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMUserFilters;
import com.awg.gpe.data.model.QTGpeMUser;
import com.awg.gpe.data.model.TGpeMRole;
import com.awg.gpe.data.model.TGpeMTask;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * Servicio de la entidad {@link TGpeMUser}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMUser extends BaseService<TGpeMUser, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMUser repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMUser, Long> getRepository() {
        return this.repository;
    }
    
    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
        QTGpeMUser user = QTGpeMUser.tGpeMUser;
        BooleanBuilder predicate = new BooleanBuilder();

        filters.forEach((key, value) -> {
            switch (key) {
                case TGpeMUserFilters.FILTER_IDENTIFIER:
                    predicate.and(user.identifier.eq((String) value));
                    break;
                case TGpeMUserFilters.FILTER_EMAIL:
                    predicate.and(user.email.eq((String) value));
                    break;
                case TGpeMUserFilters.FILTER_NAME:
                    predicate.and(user.name.eq((String) value));
                    break;
                case TGpeMUserFilters.FILTER_ACTIVE:
                    predicate.and(user.isEnabled.eq((Boolean) value));
                    break;
                case TGpeMUserFilters.FILTER_ROLE:
                    predicate.and(user.role.code.eq(((EnumRoles) value).getCode()));
                    break;
                case TGpeMUserFilters.FILTER_USER_POSITION:
                    predicate.and(user.userPosition.code.eq(((EnumUserPosition) value).getCode()));
                    break;
                default:
                    break;
            }
        });
        return predicate;
    }

    // Custom methods ------------------------------------------------
    /**
     * Método que será usado por el Login Service de Spring Security para autenticarse en la aplicación y guardar el usuario en el contexto de seguridad de Spring
     * 
     * @param identifier Identificador del usuario para realizar la búsqueda
     * @return El usuario en el caso de que exista, o nulo
     * @version 1.0
     * @throws ServiceException en el caso de que se produzca un error 
     * @since 1.0
     */
    @Transactional
    public TGpeMUser findByIdentifier(String identifier) throws ServiceException {
        TGpeMUser user = this.repository.findByIdentifier(identifier);

        if (user != null) {
            // Nos traemos las colecciones marcadas como Lazy Loading dependiendo del perfil del usuario
            if (user.getRole().equals(EnumRoles.PROJECT_LEADER)) {
                Hibernate.initialize(user.getProjectList());
            } else if (user.getRole().equals(EnumRoles.PROJECT_MANAGER)) {
                Hibernate.initialize(user.getProjectsManaged());
            } else if (user.getRole().equals(EnumRoles.USER)) {
                Hibernate.initialize(user.getHistoricalProjects());
            }

            // Nos traemos el resto de colecciones disponibles del usuario
            Hibernate.initialize(user.getTasksAssigned());
            Hibernate.initialize(user.getToDoList());
            Hibernate.initialize(user.getTimesheets());
            Hibernate.initialize(user.getIncurreds());
            Hibernate.initialize(user.getVacations());
            Hibernate.initialize(user.getAbsences());
            Hibernate.initialize(user.getRequirementsDeveloped());
            Hibernate.initialize(user.getHistoricalProjects());
        }
        

        return user;
    }
    
    /**
     * Este método devuelve un usuario con todas las colecciones requeridas para poder ver su perfil por un administrador o un jefe de proyecto
     * @param identifier El identificador del usuario que se quiere buscar
     * @return El usuario en el caso de que exista o nulo
     * @throws ServiceException En el caso de que se produzca alguna excepción
     * @version 1.0
     * @since 1.0
     */
    public TGpeMUser findProfileByIdentifier(String identifier) throws ServiceException {
        TGpeMUser user = this.repository.findByIdentifier(identifier);
        
        if (user != null) {
            // Nos traemos las colecciones que se usarán para el perfil
            Hibernate.initialize(user.getTasksAssigned());
            Hibernate.initialize(user.getHistoricalProjects());
            Hibernate.initialize(user.getTimesheets());
            Hibernate.initialize(user.getVacations());
            Hibernate.initialize(user.getAbsences());
        }
        
        return user;
    }
    
    /**
     * Devuelve el usuario pasado por parámetro con los proyectos, requerimientos y tareas asociados inicializados.
     * @param user Usuario del que conseguir información
     * @return El usuario con las colecciones inicializadas
     * @throws ServiceException En el caso de que ocurra algún error
     */
    public TGpeMUser findLeaderDashboardUser(TGpeMUser user) throws ServiceException {
    	TGpeMUser res = this.repository.findOne(user.getId());
    	
    	res.getProjectList().forEach(p -> {
    		p.getRequeriments().forEach(r -> Hibernate.initialize(r.getTasks()));
    	});
    	
    	return res;
    }
    
    /**
     * Devuelve el usuario pasado por parámetro con los proyectos, requerimientos y tareas asociados inicializados.
     * @param user Usuario del que conseguir información
     * @return El usuario con las colecciones inicializadas
     * @throws ServiceException En el caso de que ocurra algún error
     */
    public TGpeMUser findManagerDashboardUser(TGpeMUser user) throws ServiceException {
    	TGpeMUser res = this.repository.findOne(user.getId());
    	
    	res.getProjectsManaged().forEach(p -> {
    		p.getRequeriments().forEach(r -> Hibernate.initialize(r.getTasks()));
    	});
    	
    	return res;
    }
    
    /**
     * Método que devuelve todos los usuarios que no sean administradores de la aplicación
     * @return Lista con los usuarios que no son administradores
     * @throws ServiceException En el caso de que se produzca una excepción
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMUser> findApplicationUsers() throws ServiceException {
        return this.repository.findByRoleNot(new TGpeMRole(EnumRoles.ADMINISTRATOR));
    }
    
    /**
     * Método que busca en la base de datos todos los usuarios que sean administradores
     * @return Lista de usuarios administradores
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMUser> findAdministrators() {
        return this.repository.findByRole(new TGpeMRole(EnumRoles.ADMINISTRATOR));
    }
    
    /**
     * Método que busca en la base de datos todos los usuarios que sean jefes de proyecto o product owners
     * @return Lista de usuarios Jefes de Proyecto o Product Owners 
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMUser> findProjectLeaders() {
        List<TGpeMUser> leaders = this.repository.findByRole(new TGpeMRole(EnumRoles.PROJECT_LEADER));
        
        leaders.forEach(l -> {
            Hibernate.initialize(l.getHistoricalProjects());
            Hibernate.initialize(l.getProjectList());
        });
        
        return leaders;
    }

    /**
     * Método que busca en la base de datos todos los usuarios que sean responsables de proyecto o scrum masters
     * @return Lista de usuarios Responsables de Proyecto o Scrum Master
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMUser> findProjectManagers() {
        List<TGpeMUser> managers = this.repository.findByRole(new TGpeMRole(EnumRoles.PROJECT_MANAGER));
        
        managers.forEach(m -> {
            Hibernate.initialize(m.getHistoricalProjects());
            Hibernate.initialize(m.getProjectsManaged());
        });
        
        return managers;
    }
    
    /**
     * Método que busca en la base de datos todos los usuarios que no tengan privilegios especiales
     * @return Lista de usuarios desarrolladores
     * @version 1.0
     * @since 1.0
     */
    public List<TGpeMUser> findDevelopers() {
        List<TGpeMUser> users = this.repository.findByRole(new TGpeMRole(EnumRoles.USER));
        
        users.forEach(u -> {
            Hibernate.initialize(u.getHistoricalProjects());
            Hibernate.initialize(u.getRequirementsDeveloped());             
        });
        
        return users;
    }

    /**
     * Método que, recibiendo un objeto de {@link TGpeMUser}, inserta los valores por defecto y lo guarda en base de datos.
     * <p>
     * Dichos valores son:
     * <ul>
     * <li><b>Timestamp: </b> A la fecha y hora actuales
     * <li><b>Attempts </b> A 0
     * <li><b>Locked: </b> A falso. El usuario por defecto estará sin desactivar.
     * <li><b>Expiring Date: </b> Por defecto, la validez de las credenciales será de dos meses.
     * </ul>
     * </p>
     * Además, se encripta la contraseña insertada por el usuario para que se almacene ya hasheada en la base de datos
     * 
     * @param newUser
     *            El usuario con los datos cargados
     * @version 1.0
     * @throws ServiceException
     *             si se ha producido algún error al guardar el usuario
     * @since 1.0
     */
    public void createNewUser(TGpeMUser newUser) throws ServiceException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        String encodedPassword = encoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setTimestamp(LocalDateTime.now());
        newUser.setAttempts(0);
        newUser.setLocked(false);
        newUser.setExpiringDate(LocalDateTime.now().plusMonths(2));
        save(newUser);
    }
    
    /**
     * Método que permite cambiar la contraseña de un usuario.
     * @param user Usuario que se desea cambiar, con la nueva contraseña en texto plano insertada en el campo password
     * @throws ServiceException En el caso de que se produzca algún error
     * @version 1.0
     * @since 1.0
     */
    public void changePassword(TGpeMUser user) throws ServiceException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        save(user);
    }
    
    /**
     * Método que devuelve una lista con todos los usuarios disponibles para asignar.
     * Esta lista dependerá de si la tarea es de gestión o de desarrollo.
     * 
     * @param task Tarea de la que se quieren conocer los usuarios disponibles para asignar
     * @return La lista con los usuarios disponibles
     * @version 1.0
     * @since 1.0
     */
    @Transactional
    public List<TGpeMUser> findAvailableUsersForTask(TGpeMTask task) {
    	QTGpeMUser user = QTGpeMUser.tGpeMUser;
    	BooleanBuilder predicate = new BooleanBuilder();
    	
    	if (task.getManagement()) {
    		predicate.and(user.role.id.in(2, 3));
    	} else {
    		predicate.and(user.role.id.eq(4L));
    	}
    	
    	predicate.andNot(user.tasksAssigned.contains(task));
    	
    	List<TGpeMUser> users = this.repository.findAll(predicate);
    	
    	users.forEach(u -> Hibernate.initialize(u.getTasksAssigned()));
    	
    	return users;
    }
    
}