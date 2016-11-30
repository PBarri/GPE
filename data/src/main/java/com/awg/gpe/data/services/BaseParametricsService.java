package com.awg.gpe.data.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.awg.gpe.data.model.BaseParametricsEntity;

/**
 * Clase que sirve como base para todos los servicios de las entidades paramétricas de la aplicación
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Transactional
public abstract class BaseParametricsService<T extends BaseParametricsEntity, I extends Serializable> {

    /**
     * Metodo abstracto que se tendrá que implementar en todas las clases que extiendan de esta.
     * <p>
     * Esto es necesario para la reutilización de los distintos métodos
     * </p>
     * @return el repositorio usado en la clase
     * @since 1.0
     */
    public abstract CrudRepository<T, I> getRepository();

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.
     * 
     * @param entity
     *            Objeto que se quiere guardar en base de datos
     * @return el objeto guardado
     */
    public <S extends T> S save(S entity) {
        return getRepository().save(entity);
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
    public T findOne(I id) {
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
    public boolean exists(I id) {
        return getRepository().exists(id);
    }

    /**
     * Returns all instances of the type.
     * 
     * @return all entities
     */
    public List<T> findAll() {
    	ArrayList<T> res = new ArrayList<>();
    	Iterable<T> it = getRepository().findAll();
    	if (it != null) {
    		it.forEach(res::add);
    	}
        return res;
    }

    /**
     * Returns the number of entities available.
     * 
     * @return the number of entities
     */
    public long count() {
        return getRepository().count();
    }
}
