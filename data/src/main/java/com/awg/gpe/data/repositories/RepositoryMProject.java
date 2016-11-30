package com.awg.gpe.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Repositorio de la entidad {@link TGpeMProject}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMProject extends BaseRepository<TGpeMProject, Long> {
    
    List<TGpeMProject> findByLeader(TGpeMUser leader);
    
    List<TGpeMProject> findByManagers(TGpeMUser leader);
    
    @Query(value = "select max(p.id) from TGpeMProject p")
    Long findMaxId();
    
}
