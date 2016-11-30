package com.awg.gpe.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpePDocumentType;

/**
 * Repositorio de la entidad {@link TGpePDocumentType}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryPDocumentType extends CrudRepository<TGpePDocumentType, Long> {

}
