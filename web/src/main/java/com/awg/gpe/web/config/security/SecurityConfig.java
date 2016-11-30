package com.awg.gpe.web.config.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Clase en la que se configurará Spring Security 
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService loginService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public GPEAuthenticationSuccessHandler successHandler() {
        return new GPEAuthenticationSuccessHandler();
    }

    @Bean
    public GPEAuthenticationFailureHandler failureHandler() {        
        return new GPEAuthenticationFailureHandler("/login.html?error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/img/**", "/css/**", "/js/**", "/font/**", "/error/**", "/login*", "/console/*").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                    .loginPage("/login.html")
                        .usernameParameter("username")
                        .passwordParameter("password")
                    .defaultSuccessUrl("/index.xhtml")
                    .successHandler(successHandler())
                    .failureHandler(failureHandler())
                .permitAll()
            .and()
                .logout()
                    .permitAll()
                    .invalidateHttpSession(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login.html")
            .and()
                .sessionManagement()
                .maximumSessions(1)
            .and()
                .invalidSessionUrl("/error/sessionError.html")
            .and()
                .addFilterAfter(expiredSessionFilter(), SessionManagementFilter.class)
        ;
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    /**
     * Método que configura el proveedor de la autenticación
     * @param auth AuthenticationManagerBuilder
     * @throws Exception en el caso de que se produzca alguna excepción
     * @version 1.0
     * @since 1.0
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.loginService).passwordEncoder(encoder());
    }

    private Filter expiredSessionFilter() {
        SessionManagementFilter smf = new SessionManagementFilter(new HttpSessionSecurityContextRepository());
        GPEInvalidSessionStrategy invalidSessionStrategy = new GPEInvalidSessionStrategy();
        invalidSessionStrategy.setInvalidSessionUrl("/error/sessionError.html");
        smf.setInvalidSessionStrategy(invalidSessionStrategy);
        return smf;
    }

}
