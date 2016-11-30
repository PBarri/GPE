package com.awg.gpe.web.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Order(2)
@Aspect
@Component
public class LoggingAspect {

    private final Logger log = Logger.getLogger(LoggingAspect.class);
    
    /**
     * Método que crea una traza cuando se ejecuta en debug de los métodos definidos en los controladores
     * 
     * @param pjp {@link ProceedingJoinPoint}
     * @return la ejecución del método
     * @throws Throwable en el caso de que se produzca alguna excepción
     * @version 1.0
     * @since 1.0
     */
    @Around("execution(* com.awg.gpe.web.controllers..*(..))")
    public Object loggingMethod(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        Long end = System.currentTimeMillis();
        String message = String.format("%s#%s(%s) se ha completado en %d[msec]",
                pjp.getClass(),
                pjp.getSignature().getName(),
                pjp.getArgs(),
                end - start);
        this.log.debug(message);
        
        return result;
    }
    
}
