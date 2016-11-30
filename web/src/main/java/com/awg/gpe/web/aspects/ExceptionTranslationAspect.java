package com.awg.gpe.web.aspects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.awg.gpe.data.exceptions.ServiceException;

/**
 * Clase encargada de envolver las transacciones de los servicios para que devuelvan excepciones de tipo {@link ServiceException}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Order(1)
@Aspect
@Component
public class ExceptionTranslationAspect {

    private final Logger log = Logger.getLogger(ExceptionTranslationAspect.class);

    /**
     * Método que envuelve a todos los métodos que se encuentren en alguna clase dentro del paquete
     * com.awg.gpe.data.services , capturando las excepciones que se puedan producir y lanzando una
     * {@link ServiceException} para que pueda ser capturada en el resto de la aplicación.
     * 
     * @param pjp el enlace al método que se ejecuta
     * @return el resultado de la operación
     * @throws Throwable ServiceException, en el caso de que se produzca alguna excepción
     * @version 1.0
     * @since 1.0
     */
    @Around("execution(* com.awg.gpe.data.services..*(..))")
    public Object translateServiceExceptions(ProceedingJoinPoint pjp) throws Throwable {
        try {
            this.log.debug("Se va a realizar la operacion: " + pjp.getSignature());
            return pjp.proceed();
        } catch (Throwable e) {
            Integer nArgs = pjp.getArgs().length;
            String args = "";
            if (nArgs > 0) {
                args += pjp.getArgs()[0];
                for (int i = 1; i < nArgs; i++) {
                    args += ", " + pjp.getArgs()[i];
                }
            }
            String message = String.format("[ERROR - %s] %s#%s(%s) - %s ", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 
                            pjp.getSignature().getDeclaringType(), pjp.getSignature().getName(), args, e.getLocalizedMessage());
            this.log.error(message);
            throw new ServiceException(e);
        }
    }

}
