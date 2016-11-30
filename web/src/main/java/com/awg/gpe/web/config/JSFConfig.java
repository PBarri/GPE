package com.awg.gpe.web.config;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.primefaces.renderkit.DataRenderer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.awg.gpe.web.primefaces.renderers.GPEFirstPageLinkRenderer;
import com.awg.gpe.web.primefaces.renderers.GPELastPageLinkRenderer;
import com.awg.gpe.web.primefaces.renderers.GPENextPageLinkRenderer;
import com.awg.gpe.web.primefaces.renderers.GPEPrevPageLinkRenderer;
import com.awg.gpe.web.primefaces.renderers.GPERowsPerPageRendered;
import com.sun.faces.config.ConfigureListener;

/**
 * Configuración de Java Server Faces para la aplicación
 * <p>En esta clase se configurará la integración con Primefaces v5.3</p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Configuration
public class JSFConfig implements ServletContextInitializer {

    /**
     * @see org.springframework.boot.context.embedded.ServletContextInitializer#onStartup(javax.servlet.ServletContext)
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Validación en el lado de cliente de formularios
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "false");
        // Ningún tema por defecto, aplicaremos uno propio
        servletContext.setInitParameter("primefaces.THEME", "none");
        // Subida de ficheros por uploader nativo
        servletContext.setInitParameter("primefaces.UPLOADER", "native");
        // Deshabilitamos el salvado parcial de los ManagedBean ya que usaremos ajax para la carga de los mismos
        servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING", "false");
        // Habilitamos Font Awesome para Primefaces
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        // Introducimos nuevos paginadores
        DataRenderer.addPaginatorElement("{GPEPaginator}", new GPERowsPerPageRendered());
        DataRenderer.addPaginatorElement("{FirstPage}", new GPEFirstPageLinkRenderer());
        DataRenderer.addPaginatorElement("{PreviousPage}", new GPEPrevPageLinkRenderer());
        DataRenderer.addPaginatorElement("{NextPage}", new GPENextPageLinkRenderer());
        DataRenderer.addPaginatorElement("{LastPage}", new GPELastPageLinkRenderer());
    }

    /**
     * Configurador del ServletRegistrationBean
     * @version 1.0
     * @since 1.0
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet facesServlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(facesServlet, "*.xhtml");
        servletRegistrationBean.setName("FacesServlet");
        // Ningún tema por defecto, aplicaremos uno propio
        servletRegistrationBean.addInitParameter("primefaces.THEME", "none");
        // Vamos a usar una validación propia, deshabilitamos la de primefaces
        servletRegistrationBean.addInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "false");
        // Evitamos el parseo de los comentarios en los xhtml
        servletRegistrationBean.addInitParameter("facelets.SKIP_COMMENTS", "true");
        // Subida de ficheros por uploader nativo
        servletRegistrationBean.addInitParameter("primefaces.UPLOADER", "native");
        // Deshabilitamos el salvado parcial de los ManagedBean ya que usaremos ajax para la carga de los mismos
        servletRegistrationBean.addInitParameter("javax.faces.PARTIAL_STATE_SAVING", "false");
        // Habilitamos Font Awesome para Primefaces
        servletRegistrationBean.addInitParameter("primefaces.FONT_AWESOME", "true");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
    }

}
