package com.awg.gpe.batch.listeners;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * Clase que ejecuta un log automático para todos los pasos de Spring Batch.
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class GpeStepListener implements StepExecutionListener {
	
	private static final Logger log = Logger.getLogger(GpeStepListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {
        GpeStepListener.log.info("Se ejecuta el step " + stepExecution.getStepName() + " con los parámetros " + stepExecution.getJobParameters());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (stepExecution.getEndTime() != null) {
            LocalDateTime start = LocalDateTime.ofInstant(stepExecution.getStartTime().toInstant(), ZoneId.systemDefault());
            LocalDateTime end = LocalDateTime.ofInstant(stepExecution.getEndTime().toInstant(), ZoneId.systemDefault());
            if (stepExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
                GpeStepListener.log.info("Se ha completado el step " + stepExecution.getStepName() + " en " + Duration.between(start, end));
            } else if (stepExecution.getExitStatus().equals(ExitStatus.FAILED)) {
                GpeStepListener.log.error("Se ha completado el step " + stepExecution.getStepName() + " con código de error: " + stepExecution.getExitStatus().getExitCode() + ", "
                        + stepExecution.getExitStatus().getExitDescription() + Duration.between(start, end));
            }
        }
        return stepExecution.getExitStatus();
	}

}
