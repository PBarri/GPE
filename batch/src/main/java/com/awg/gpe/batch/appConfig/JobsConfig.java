package com.awg.gpe.batch.appConfig;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Clase que configura algunos de los aspectos de Spring Batch
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Configuration
@EnableBatchProcessing
public class JobsConfig {

	@Autowired
	private JobRepository jobRepository;
	
	@Value("${jobs.executorPoolSize:10}")
    private int maxPoolSize;

    @Value("${jobs.executorCoreSize:4}")
    private int corePoolSize;

    @Value("${jobs.executorQueueSize:100}")
    private int queueSize;
    
    /**
     * @return {@link JobLauncher} asíncrono
     * @version 1.0
     * @since 1.0
     */
    @Bean
    public JobLauncher asyncJobLauncher() {
    	SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    	jobLauncher.setTaskExecutor(taskExecutor());
        jobLauncher.setJobRepository(this.jobRepository);
    	return jobLauncher;
    }
    
    @Bean
    public JobsConfig.GPEJobBuilderFactory gpeJobBuilderFactory() {
    	return new JobsConfig.GPEJobBuilderFactory(this.jobRepository, new JobsConfig.JobLoggerListener());
    }

    @Bean
    public TaskExecutor taskExecutor() {
    	ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    	taskExecutor.setCorePoolSize(this.corePoolSize);
    	taskExecutor.setMaxPoolSize(this.maxPoolSize);
        taskExecutor.setQueueCapacity(this.queueSize);
        taskExecutor.afterPropertiesSet();
    	return taskExecutor;
    }

    /*
     * @Bean public ApplicationContextFactory parsearInterfazJob() { return new GenericApplicationContextFactory(ParsearInterfaz.class); }
     *
     */
    /**
     * Validador que comprueba que el job contenga los parámetros necesarios.
     *
     * @param required
     *            Lista con los nombres de los parámetros necesarios
     * @return
     */
    public JobParametersValidator jobValidator(List<String> required) {
        return parameters -> {
            if (!parameters.getParameters().keySet().containsAll(required)) {
                throw new JobParametersInvalidException("Los parámetros del job no son correctos");
            }
        };
    }

    public class GPEJobBuilderFactory extends JobBuilderFactory {

    	private final JobExecutionListener[] listeners;

		public GPEJobBuilderFactory(JobRepository jobRepository, JobExecutionListener... listeners) {
			super(jobRepository);
			this.listeners = listeners;
		}

		@Override
		public JobBuilder get(String name) {
			JobBuilder builder = super.get(name);
			for (JobExecutionListener listener : this.listeners) {
				builder.listener(listener);
			}
			return builder;
		}
    }

    protected class JobLoggerListener implements JobExecutionListener {

    	private final Logger log = Logger.getLogger(JobsConfig.JobLoggerListener.class);

		@Override
		public void beforeJob(JobExecution jobExecution) {
            this.log.info("Se ejecuta el job " + jobExecution.getJobInstance().getJobName() + " con los parámetros " + jobExecution.getJobParameters());
		}

		@Override
		public void afterJob(JobExecution jobExecution) {
			if (!jobExecution.isRunning()) {
	            LocalDateTime start = LocalDateTime.ofInstant(jobExecution.getCreateTime().toInstant(), ZoneId.systemDefault());
	            LocalDateTime end = LocalDateTime.ofInstant(jobExecution.getEndTime().toInstant(), ZoneId.systemDefault());
	            if (jobExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
                    this.log.info("Se ha completado el job " + jobExecution.getJobInstance().getJobName() + " en " + Duration.between(start, end));
	            } else if (jobExecution.getExitStatus().equals(ExitStatus.FAILED)) {
                    this.log.error("Se ha completado el job " + jobExecution.getJobInstance().getJobName() + " con código de error: " + jobExecution.getExitStatus().getExitCode() + ", "
	                        + jobExecution.getExitStatus().getExitDescription() + " en " + Duration.between(start, end));
	            }
	        }
		}	
    }
    
 // Getters y setters

    /**
     * @return el jobRepository
     */
    public JobRepository getJobRepository() {
        return this.jobRepository;
    }

    /**
     * @param jobRepository
     *            Setea el campo jobRepository
     */
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * @return el maxPoolSize
     */
    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    /**
     * @param maxPoolSize
     *            Setea el campo maxPoolSize
     */
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    /**
     * @return el corePoolSize
     */
    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    /**
     * @param corePoolSize
     *            Setea el campo corePoolSize
     */
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    /**
     * @return el queueSize
     */
    public int getQueueSize() {
        return this.queueSize;
    }

    /**
     * @param queueSize
     *            Setea el campo queueSize
     */
    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
	
}
