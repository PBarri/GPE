package com.awg.gpe.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpePUserCategory;

/**
 * Repositorio de la entidad {@link TGpePUserCategory}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryPUserCategory extends CrudRepository<TGpePUserCategory, Long> {

}
