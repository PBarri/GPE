package com.awg.gpe.data;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Clase de configuración de la parte de datos del proyecto GPE
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Configuration
@EntityScan({"com.awg.gpe.data.converters", "com.awg.gpe.data.model"})
@EnableJpaRepositories("com.awg.gpe.data.repositories")
@EnableTransactionManagement
public class DataConfiguration /*extends JpaBaseConfiguration*/ {

//    @Override
//    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
//        return new EclipseLinkJpaVendorAdapter();
//    }
//
//    @Override
//    protected Map<String, Object> getVendorProperties() {
//        return new HashMap<>();
//    }
    
        

}
