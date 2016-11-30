package com.awg.gpe.web.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * Clase que configura la respuesta de la aplicación a un timeout de la sesión
 * <p>Esta clase es necesaria ya que la navegación se hará mediante Ajax, ya que la respuesta normal es una petición de redirección que Ajax no entiende, quedándose la aplicación
 * colgada</p> 
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class GPEInvalidSessionStrategy implements InvalidSessionStrategy {

    private static final String PARTIAL_RESPONSE_PREFIX = "<partial-response><redirect url=\"";
    private static final String PARTIAL_RESPONSE_SUFFIX = "\"/></partial-response>";

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private boolean createNewSession = true;
    private String invalidSessionUrl;

    /**
     * @see org.springframework.security.web.session.InvalidSessionStrategy#onInvalidSessionDetected(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (this.createNewSession) {
            request.getSession();
        }

        if (isAjaxRequest(request)) {
            String contextPath = request.getContextPath();
            String redirectUrl = contextPath + this.invalidSessionUrl;

            String ajaxRedirectXml = createAjaxRedirectXml(redirectUrl);

            response.setContentType("text/xml");
            response.getWriter().write(ajaxRedirectXml);
        } else {
            this.redirectStrategy.sendRedirect(request, response, this.invalidSessionUrl);
        }

    }

    // Helper methods ---------------------------------------------------------
    /**
     * Método que crea la respuesta en un formato que Ajax entienda la redirección
     * 
     * @since 1.0
     * @param redirectUrl URL a la que se redirigirá
     * @return el xml conteniendo la orden de redirección
     */
    private String createAjaxRedirectXml(String redirectUrl) {
        return new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(GPEInvalidSessionStrategy.PARTIAL_RESPONSE_PREFIX + redirectUrl + GPEInvalidSessionStrategy.PARTIAL_RESPONSE_SUFFIX).toString();
    }

    /**
     * Copied from org.springframework.faces.webflow.JsfAjaxHandler.isAjaxRequestInternal
     * 
     * @param request El request de la petición
     * @return si es una petición ajax o no
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("Faces-Request");
        String param = request.getParameter("javax.faces.partial.ajax");
        return ("partial/ajax".equals(header) || "true".equals(param));
    }
    
    // Getters and Setters ----------------------------------------------------

	/**
	 * @param createNewSession the createNewSession to set
	 */
	public void setCreateNewSession(boolean createNewSession) {
		this.createNewSession = createNewSession;
	}

	/**
	 * @param invalidSessionUrl the invalidSessionUrl to set
	 */
	public void setInvalidSessionUrl(String invalidSessionUrl) {
		this.invalidSessionUrl = invalidSessionUrl;
	}
}
