package com.awg.gpe.data.repositories;

import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMToDo;

/**
 * Repositorio de la entidad {@link TGpeMToDo}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMToDo extends BaseRepository<TGpeMToDo, Long> {

}
