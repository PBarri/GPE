package com.awg.gpe.web.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.services.ServiceMUser;

/**
 * Clase que controla las acciones que se ejecutarán justo después de que el usuario falle al logarse
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class GPEAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger log = Logger.getLogger(GPEAuthenticationFailureHandler.class);

    @Autowired
    private ServiceMUser userService;

    @Value("${gpe.maxAttempts}")
    private Integer maxAttempts;
        
    /**
     * Constructor de la clase GPEAuthenticationFailureHandler.java
     * @param defaultUrl URL a la que redireccionará en el caso de fallo
     * @since 1.0
     */
    public GPEAuthenticationFailureHandler(String defaultUrl) {
        setDefaultFailureUrl(defaultUrl);
    }

    /**
     * Al autenticarse erróneamente, se actualiza la fecha el número de intentos del usuario
     * <p>
     * En el caso de que el número de intentos llegue al máximo permitido (5), el usuario se bloqueará
     * </p>
     * <p>
     * En el caso de que las credenciales del usuario ya hubieran expirado, se le indicará mediante un mensaje
     * </p>
     * 
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {

        String username = request.getParameter("username");

        if (StringUtils.hasText(username)) {
            GPEAuthenticationFailureHandler.log.info("Se ha producido un intento de logueo incorrecto para el usuario: " + username + " desde la IP " + request.getRemoteAddr());
        } else {
            GPEAuthenticationFailureHandler.log.info("Se ha producido un intento de logueo incorrecto desde la IP " + request.getRemoteAddr());
        }

        if (!(ex instanceof InternalAuthenticationServiceException)) {

            TGpeMUser user = null;
            try {
                user = this.userService.findByIdentifier(username);
            } catch (ServiceException e) {
                GPEAuthenticationFailureHandler.log.error("No se ha encontrado al usuario en la base de datos, o se ha producido un error al buscarlo: " + e.getMessage());
            }

            if (user != null) {
                user.setAttempts(user.getAttempts() + 1);
                // Si ha llegado al número máximo de intentos, se bloquea el usuario
                if (user.getAttempts().compareTo(this.maxAttempts) >= 0) {
                    user.setLocked(true);
                }
                try {
                    this.userService.save(user);
                } catch (Exception e) {
                    GPEAuthenticationFailureHandler.log.error("Se ha producido un error al actualizar los datos del usuario " + username);
                }
            }
        }
        
        super.onAuthenticationFailure(request, response, ex);
    }
}
