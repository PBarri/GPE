package com.awg.gpe.web.config.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.repositories.RepositoryMUser;

/**
 * Clase que controla las acciones que se ejecutarán justo después de que el usuario se logue
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class GPEAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger log = Logger.getLogger(GPEAuthenticationSuccessHandler.class);

    @Autowired
    private RepositoryMUser userRepository;

    /**
     * Al autenticarse, se actualiza la fecha de último acceso y la fecha de expiración del usuario
     * <p>La fecha de expiración se setea a 2 meses a partir del instante actual</p>
     * 
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        GPEAuthenticationSuccessHandler.log.info("Usuario logado con éxito");

        TGpeMUser user = (TGpeMUser) authentication.getPrincipal();

        try {
            if (user != null) {
                user.setLastLoginDate(LocalDateTime.now());
                user.setExpiringDate(LocalDateTime.now().plusMonths(2));
                user.setAttempts(0);
                this.userRepository.save(user);
            }
        } catch (Exception e) {
            GPEAuthenticationSuccessHandler.log.error("Se ha producido un error al actualizar los datos del usuario: " + e.getMessage());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
