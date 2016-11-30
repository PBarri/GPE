package com.awg.gpe.data.services;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.querydsl.core.types.Predicate;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.BaseEntity;
import com.awg.gpe.data.repositories.BaseRepository;

/**
 * Clase que sirve como base para el resto de Servicios de la aplicación
 * <p>
 * En ella se define la transacción de AspectJ
 * </p>
 * <p>
 * También se declararán los métodos comunes a todos los servicios para no tener duplicidad de código y obtener mas reutilización
 * </p>
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Transactional
public abstract class BaseService<T extends BaseEntity, I extends Serializable> {
    
    @Resource(name = "messageSource")
    protected MessageSource messages;
    
    protected String getMessage(String msgCode) {
        return this.messages.getMessage(msgCode, null, Locale.getDefault());
    }

    /**
     * Método a implementar en cada servicio que permita inyectar el repositorio correspondiente
     * 
     * @return el repositorio usado en la clase
     * @since 1.0
     */
    public abstract BaseRepository<T, I> getRepository();

    /**
     * Método para que el LazyDataModel cargue los datos correspondientes
     * 
     * @param pageable {@link Pageable} con la información de la página a buscar
     * @param filters Mapa con los distintos filtros a aplicar en la búsqueda
     * @return La lista con los datos de la búsqueda
     * @version 1.0
     * @since 1.0
     */
    public List<T> loadPaginatedData(Pageable pageable, Map<String, Object> filters) throws ServiceException {
        Predicate predicate = createPredicateFromFilters(filters);
        Page<T> page = findAll(predicate, pageable);
        return page.hasContent() ? page.getContent() : null;
    }

    /**
     * @param filters Mapa con los distintos filtros a aplicar
     * @return El número de registros que existen en la búsqueda con esos filtros
     * @version 1.0
     * @since 1.0
     */
    public int countPaginatedData(Map<String, Object> filters) throws ServiceException {
        Predicate predicate = createPredicateFromFilters(filters);
        Long count = count(predicate);
        return count != null ? count.intValue() : 0;
    }
    
    protected abstract Predicate createPredicateFromFilters(Map<String, Object> filters);

    /**
     * Returns all instances of the type.
     * 
     * @return all entities
     */
    public List<T> findAll() throws ServiceException {
        return getRepository().findAll();
    }

    /**
     * Returns all entities sorted by the given options.
     * 
     * @param sort
     *            Ordenación de la consulta
     * @return all entities sorted by the given options
     */
    public List<T> findAll(Sort sort) throws ServiceException {
        return getRepository().findAll(sort);
    }

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     * 
     * @param pageable
     *            Página de la consulta
     * @return a page of entities
     */
    public Page<T> findAll(Pageable pageable) throws ServiceException {
        return getRepository().findAll(pageable);
    }

    /**
     * 
     * @param predicate {@link Predicate} con la consulta a realizar en la base de datos
     * @param pageable {@link Pageable} con la información de la página que se quiere buscar
     * @return {@link Page} con los datos de la página para la consulta que se quiere realizar
     * @throws ServiceException En el caso de que se produzca un error en el acceso a datos
     * @version 1.0
     * @since 1.0
     */
    public Page<T> findAll(Predicate predicate, Pageable pageable) throws ServiceException {
        return getRepository().findAll(predicate, pageable);
    }

    /**
     * Returns all instances of the type with the given IDs.
     * 
     * @param ids
     *            de los objetos a buscar
     * @return Lista con todos los registros encontrados
     */
    public List<T> findAll(Iterable<I> ids) throws ServiceException {
        return getRepository().findAll(ids);
    }

    /**
     * Returns the number of entities available.
     * 
     * @return the number of entities
     */
    public long count() throws ServiceException {
        return getRepository().count();
    }

    /**
     * @param predicate {@link Predicate} con la consulta para realizar la búsqueda
     * @return El número de registros que existen en bases de datos para esa consulta
     * @throws ServiceException En el caso de que se produzca algún error en el acceso a datos
     * @version 1.0
     * @since 1.0
     */
    public long count(Predicate predicate) throws ServiceException {
        return getRepository().count(predicate);
    }

    /**
     * Deletes the entity with the given id.
     * 
     * @param id
     *            must not be {@literal null}.
     * @throws IllegalArgumentException
     *             in case the given {@code id} is {@literal null}
     */
    public void delete(I id) throws ServiceException {
        getRepository().delete(id);
    }

    /**
     * Deletes a given entity.
     * 
     * @param entity Entidad que se quiere eliminar de la base de datos
     * @throws IllegalArgumentException
     *             in case the given entity is {@literal null}.
     */
    public void delete(T entity) throws ServiceException {
        getRepository().delete(entity);
    }

    /**
     * Deletes the given entities.
     * 
     * @param entities Registros que se quieren eliminar de la base de datos
     * @throws IllegalArgumentException
     *             in case the given {@link Iterable} is {@literal null}.
     */
    public void delete(Iterable<? extends T> entities) throws ServiceException {
        getRepository().delete(entities);
    }

    /**
     * Deletes all entities managed by the repository.
     */
    public void deleteAll() throws ServiceException {
        getRepository().deleteAll();
    }

    /**
     * Retrieves an entity by its id.
     * 
     * @param id
     *            must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    public T findOne(I id) throws ServiceException {
        return getRepository().findOne(id);
    }

    /**
     * Returns whether an entity with the given id exists.
     * 
     * @param id
     *            must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    public boolean exists(I id) throws ServiceException {
        return getRepository().exists(id);
    }

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.
     * 
     * @param entity Entidad que se quiere guardar en base de datos
     * @return the saved entity
     */
    public <S extends T> S save(S entity) throws ServiceException {
        entity.setTimestamp(LocalDateTime.now());
        return getRepository().save(entity);
    }

    /**
     * Saves all given entities.
     * 
     * @param entities Lista de entidades que se quieren guardar en base de datos
     * @return the saved entities
     * @throws IllegalArgumentException
     *             in case the given entity is {@literal null}.
     */
    public <S extends T> List<S> save(Iterable<S> entities) throws ServiceException {
        entities.forEach(entity -> entity.setTimestamp(LocalDateTime.now()));
        return getRepository().save(entities);

    }

    /**
     * Flushes all pending changes to the database.
     */
    public void flush() throws ServiceException {
        getRepository().flush();
    }

    /**
     * Saves an entity and flushes changes instantly.
     * 
     * @param entity Entidad que se quiere guardar
     * @return the saved entity
     */
    public <S extends T> S saveAndFlush(S entity) throws ServiceException {
        entity.setTimestamp(LocalDateTime.now());
        return getRepository().saveAndFlush(entity);
    }

    /**
     * Deletes the given entities in a batch which means it will create a single {@link Query}. Assume that we will clear the {@link javax.persistence.EntityManager} after the
     * call.
     * 
     * @param entities Entidades que se quieren eliminar de la base de datos
     */
    public void deleteInBatch(Iterable<T> entities) throws ServiceException {
        getRepository().deleteInBatch(entities);
    }

    /**
     * Deletes all entites in a batch call.
     */
    public void deleteAllInBatch() throws ServiceException {
        getRepository().deleteAllInBatch();
    }

    /**
     * Returns a reference to the entity with the given identifier.
     * 
     * @param id
     *            must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager#getReference(Class, Object)
     */
    public T getOne(I id) throws ServiceException {
        return getRepository().getOne(id);
    }

}
