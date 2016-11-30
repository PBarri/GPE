package com.awg.gpe.data.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMRole;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Repositorio de la entidad {@link TGpeMUser}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMUser extends BaseRepository<TGpeMUser, Long> {

    /**
     * Método usado por Spring Security para encontrar un usuario a partir de su username
     * 
     * @param identifier
     *            El identificador por el que se quiere buscar
     * @return El usuario con dicho identificador
     * @since 1.0
     */
    TGpeMUser findByIdentifier(String identifier);
    
    /**
     * Método que devuelve todos los usuarios que no sean de un rol específico
     * @param role El rol que queremos omitir
     * @return La lista de usuarios que no tienen el rol asignado
     * @version 1.0
     * @since 1.0
     */
    List<TGpeMUser> findByRoleNot(TGpeMRole role);
    
    
    /**
     * Método que devuelve todos los usuarios que pertenezcan a un rol
     * @param role Rol del que se quieren buscar los usuarios
     * @return La lista de usuarios
     * @version 1.0
     * @since 1.0
     */
    List<TGpeMUser> findByRole(TGpeMRole role);
    
}
