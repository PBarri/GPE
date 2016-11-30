package com.awg.gpe.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.awg.gpe.data.model.TGpePTaskType;

/**
 * Repositorio de la entidad {@link TGpePTaskType}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface RepositoryPTaskType extends CrudRepository<TGpePTaskType, Long> {

}
