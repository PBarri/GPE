package com.awg.gpe.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.xhtml");
    }
    
//    @Bean
//    public Filter sessionOpenInViewFilter() {
//        return new OpenEntityManagerInViewFilter();
//    }

}
