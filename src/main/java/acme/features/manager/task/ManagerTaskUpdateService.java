package acme.features.manager.task;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.filters.SpamFilter;
import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerTaskUpdateService implements AbstractUpdateService<Manager, Task> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerTaskRepository repository;

	private final SpamFilter filter;
	
	public ManagerTaskUpdateService() {
		this.filter = new SpamFilter("spam.txt", 10.0);
	}
	
	// AbstractListService<Employer, Job> -------------------------------------


	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		boolean result;
		int taskId;
		Task task;
		Manager manager;
		Principal principal;

		taskId = request.getModel().getInteger("id");
		task = this.repository.findOneTaskById(taskId);
		manager = task.getManager();
		principal = request.getPrincipal();
		result = manager.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert this.filter.validate(entity.getTitle());
		assert this.filter.validate(entity.getDescription());
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
		if(entity.getExecutionPeriod()==null && entity.getStartDate()!=null) entity.setExecutionPeriod();
		
		request.unbind(entity, model, "title", "description", "link", "startDate");
		request.unbind(entity, model, "endingDate", "workload", "finished", "privacy", "executionPeriod");
		model.setAttribute("readonly", false);
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;

		Task result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneTaskById(id);

		return result;
	}

	@Override
	public void update(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
	
		this.repository.save(entity);
	}
}
