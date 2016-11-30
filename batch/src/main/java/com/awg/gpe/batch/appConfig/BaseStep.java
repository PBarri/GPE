package com.awg.gpe.batch.appConfig;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;

/**
 * Clase que sirve para inyectar el contexto de la ejecución del paso.
 * <p>
 * Esta clase debe ser extendida por todas las clases que implementen un paso de Spring Batch
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class BaseStep {

	@Value("#{stepExecution}")
	protected StepExecution ctx;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
        ctx = stepExecution;
	}
	
	protected ExecutionContext getEC() {
        return this.ctx.getExecutionContext();
    }
	
}
