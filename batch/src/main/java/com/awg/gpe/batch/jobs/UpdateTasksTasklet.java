package com.awg.gpe.batch.jobs;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.awg.gpe.batch.appConfig.BaseStep;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMTask;
import com.awg.gpe.data.services.ServiceMTask;

/**
 * Clase que implementa el proceso de actualización de tareas.
 * <p>
 * Este proceso busca todas las tareas activas en el sistema y comprueba si tiene que cambiar de estado
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class UpdateTasksTasklet extends BaseStep implements Tasklet, InitializingBean {

	private final Logger log = Logger.getLogger(UpdateTasksTasklet.class);
	
	@Autowired
	private ServiceMTask taskService;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ctx, "El contexto no puede ser nulo");
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Long tasksForUpdate = this.taskService.countTasksForUpdate();
		
		if (tasksForUpdate > 0) {
			List<TGpeMTask> tasks = this.taskService.findTasksForUpdate();
			tasks.forEach(t -> updateTask(t));
		}
		return RepeatStatus.FINISHED;
	}

	private void updateTask(TGpeMTask t) {
		try {
            this.taskService.updateTask(t);
		} catch (ServiceException e) {
            this.log.error("Se ha producido un error al actualizar la tarea: " + t);
		}
	}
	
	

}
