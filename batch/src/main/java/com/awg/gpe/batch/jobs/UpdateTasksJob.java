package com.awg.gpe.batch.jobs;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.awg.gpe.batch.appConfig.JobsConfig;
import com.awg.gpe.batch.listeners.GpeStepListener;

/**
 * Clase que configura el proceso batch que actualiza el estado de las tareas.
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
@Import(JobsConfig.class)
public class UpdateTasksJob {

	private static final Logger log = Logger.getLogger(UpdateTasksJob.class);

	@Autowired
	private StepBuilderFactory stepFactory;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobsConfig config;
	
	@Bean
	public Job updateTaskJob() {
		return this.config.gpeJobBuilderFactory().get("updateTaskJob")
			.start(updateTasks())
		.build();
	}
	
	@Bean
	public Step updateTasks() {
		return this.stepFactory.get("updateTasks")
			.listener(new GpeStepListener())
			.tasklet(updateTasksTasklet())
		.build();
	}
	
	@Bean
	@StepScope
	public Tasklet updateTasksTasklet() {
		return new UpdateTasksTasklet();
	}
	
	/**
	 * Método planificado que se ejecuta cada hora.
	 * <p>
	 * Este método lanza el proceso configurado en esta clase.
	 * </p>
	 * 
	 * @version 1.0
	 * @since 1.0
	 */
	@Scheduled(cron = "0 0 0 ? * *")
	public void execute() {
		JobParameters params = new JobParametersBuilder().addLong("date", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)).toJobParameters();
		try {
            UpdateTasksJob.log.info("Se ejecuta el proceso de actualización de errores");
            JobExecution je = this.jobLauncher.run(updateTaskJob(), params);
            if (je.getExitStatus().equals(ExitStatus.FAILED)) {
                UpdateTasksJob.log.error("Se ha producido un error durante la ejecución del proceso de actualización de errores: " + je.getExitStatus().getExitDescription());
            } else {
                UpdateTasksJob.log.info("Se ha terminado la ejecución del proceso de actualización de errores: " + je.getExitStatus().getExitDescription());
            }
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            UpdateTasksJob.log.error("Se ha producido un error al ejecutar el job: " + e.getMessage());
		}
	}
	
	
	
}
