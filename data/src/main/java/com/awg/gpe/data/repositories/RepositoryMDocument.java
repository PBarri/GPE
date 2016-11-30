package com.awg.gpe.data.repositories;

import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMDocument;

/**
 * Repositorio de la entidad {@link TGpeMDocument}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMDocument extends BaseRepository<TGpeMDocument, Long> {

}
