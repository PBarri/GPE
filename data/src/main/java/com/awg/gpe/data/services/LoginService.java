package com.awg.gpe.data.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import com.awg.gpe.data.model.TGpeMUser;

/**
 * Clase que implementa la clase {@link UserDetailsService} de Spring Security
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class LoginService {

    /**
     * Método usado para obtener el usuario autenticado en la aplicación desde cualquier lugar.
     * 
     * @return el usuario autenticado en la aplicación
     * @version 1.0
     * @since 1.0
     */
    public static TGpeMUser getPrincipal() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication auth = securityContext.getAuthentication();
            Object principal = auth.getPrincipal();

            Assert.isTrue(principal instanceof TGpeMUser);

            return (TGpeMUser) principal;
        } catch (Exception e) {
            return null;
        }
    }

}
