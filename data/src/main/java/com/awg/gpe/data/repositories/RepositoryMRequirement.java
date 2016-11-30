package com.awg.gpe.data.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMProject;
import com.awg.gpe.data.model.TGpeMRequirement;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Repositorio de la entidad {@link TGpeMRequirement}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMRequirement extends BaseRepository<TGpeMRequirement, Long> {
    
    /**
     * Método que devuelve todos los requerimientos asociados a un proyecto determinado
     * @param project El proyecto del que queremos saber sus requerimientos
     * @return La lista de requerimientos del proyecto
     * @version 1.0
     * @since 1.0
     */
    List<TGpeMRequirement> findByProject(TGpeMProject project);
    
    /**
     * Método que devuelve todos los requerimientos asociados a un jefe de proyecto
     * @param user El jefe de proyecto del que queremos conocer los requerimientos asociados
     * @return La lista de requerimientos asociados al jefe de proyecto
     * @version 1.0
     * @since 1.0
     */
    List<TGpeMRequirement> findByProjectLeader(TGpeMUser user);
    
    /**
     * Método que devuelve todos los requerimientos asociados a un responsble de proyecto
     * @param user El responsable de proyecto
     * @return La lista de requerimientos
     * @version 1.0
     * @since 1.0
     */
    List<TGpeMRequirement> findByProjectManagers(TGpeMUser user);

}
