package acme.features.manager.task;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;


@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected ManagerTaskRepository repository;
		
	// AbstractCreateService<Manager, Task> interface -------------------------


	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		if(entity.getStartDate() != null && entity.getEndingDate() != null) entity.setExecutionPeriod();
		
		if (!errors.hasErrors("past_task") && entity.getStartDate() != null) {
			errors.state(request, (entity.getStartDate().after(Date.from(Instant.now())) || entity.getFinished()==true), "startDate", "manager.task.form.error.past_task");
		}
		
		if (!errors.hasErrors("incorrect_finish") && entity.getStartDate() != null && entity.getEndingDate() != null) {
			errors.state(request, entity.getEndingDate().after(entity.getStartDate()), "endingDate", "manager.task.form.error.incorrect_finish");
		}
		
		if (!errors.hasErrors("execution_period_null") && entity.getExecutionPeriod() != null) {
			errors.state(request, entity.getExecutionPeriod() != null , "executionPeriod", "manager.task.form.error.execution_period_null");
		}
		
		if (!errors.hasErrors("work_overload") && entity.getExecutionPeriod() != null && entity.getWorkload() != null) {
			final Double workloadMin = entity.getWorkload()*60;
			errors.state(request, workloadMin < entity.getExecutionPeriod(), "workload", "manager.task.form.error.work_overload");
		}
	}
		
	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors);
	}
		
	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title", "description", "link", "startDate");
		request.unbind(entity, model, "endingDate", "workload", "finished", "privacy", "executionPeriod");
	}
		
	@Override
	public final Task instantiate(final Request<Task> request) {
		assert request != null;
		
		Task result;
		Manager manager;

		manager = this.repository.findOneManagerById(request.getPrincipal().getActiveRoleId());
		result = new Task();
		result.setManager(manager);

		return result;
	}
		
	@Override
	public void create(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}

}
