package com.awg.gpe.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpePPriority;

/**
 * Repositorio de la entidad {@link TGpePPriority}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryPPriority extends CrudRepository<TGpePPriority, Long> {

}
