package com.awg.gpe.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ServletContextAware;

import com.awg.gpe.data.DataConfiguration;
import com.awg.gpe.web.config.JSFConfig;

/**
 * Clase principal de la parte web del proyecto GP
 * <p>
 * En esta clase se configuran los beans necesarios para que la aplicación arranque de forma correcta
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.awg.gpe.web", "com.awg.gpe.data" })
@EnableTransactionManagement
@Import(value = DataConfiguration.class)
public class WebApplication extends SpringBootServletInitializer implements ServletContextAware {
    
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    /**
     * @see org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class, JSFConfig.class);
    }

    /**
     * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        ServletRegistration servlet = servletContext.getServletRegistration("FacesServlet");
        if (servlet != null) {
            servlet.addMapping("*.xhtml");
        }
    }
    
    /**
     * Crea un @Bean conteniendo los mensajes de la aplicación
     * @return {@link MessageSource} con los mensajes de la aplicación
     * @version 1.0
     * @since 1.0
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:messages");
        ms.setDefaultEncoding("ISO-8859-1");
        ms.setUseCodeAsDefaultMessage(true);
        return ms;
    }
}
