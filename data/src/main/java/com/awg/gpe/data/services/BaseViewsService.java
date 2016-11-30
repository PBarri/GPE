package com.awg.gpe.data.services;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.awg.gpe.data.model.BaseViewEntity;

/**
 * Clase que sirve como base para el resto de Servicios relacionados con las vistas de la aplicación
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Transactional
public abstract class BaseViewsService<T extends BaseViewEntity, I extends Serializable> {
	
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
    public Iterable<T> findAll() {
        return getRepository().findAll();
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
