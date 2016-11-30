package com.awg.gpe.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMRole;

/**
 * Repositorio de la entidad {@link TGpeMRole}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMRole extends CrudRepository<TGpeMRole, Long> {

}
