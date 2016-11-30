package com.awg.gpe.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.awg.gpe.data.enums.EnumRoles;

/**
 * Clase que modela los roles de la aplicación
 * <p>
 * Esta clase implementa la interfaz {@link GrantedAuthority} de Spring Security
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_GPE_M_ROLES")
public class TGpeMRole extends BaseParametricsEntity implements GrantedAuthority {

    /**
     * Serial Version UID -----------------------------------------------------
     */
    private static final long serialVersionUID = 1L;

    // Constructors -----------------------------------------------------------
    /**
     * Constructor vacío de la clase TGpeMRol
     */
    public TGpeMRole() {
    }

    /**
     * Constructor que inicializa un objeto TGpeMRol con los datos correspondientes
     * 
     * @param role El {@link EnumRoles} con el que se quiere crear el rol
     */
    public TGpeMRole(EnumRoles role) {
        id = role.getId();
        code = role.getCode();
        description = role.getDescription();
    }

    /**
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Override
    public String getAuthority() {
        return this.code;
    }

    // Equals -----------------------------------------------------------------
    @Override
    /**
     * Método equals de TGpeMRol
     * <p>
     * Puede evaluar la igualdad con un objeto de clase @see com.awg.data.enums.EnumRoles , con un Long, que corresponda al campo ID del Rol, o con otro objeto TGpeMRol.
     * </p>
     */
    public boolean equals(Object obj) {
        return (obj instanceof EnumRoles) ? id.equals(((EnumRoles) obj).getId()) : super.equals(obj);
    }

}