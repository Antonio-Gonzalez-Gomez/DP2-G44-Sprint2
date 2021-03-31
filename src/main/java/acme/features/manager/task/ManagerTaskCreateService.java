package acme.features.manager.task;

import java.time.LocalDateTime;

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
		
		if (!errors.hasErrors("past_task")) {
			errors.state(request, entity.getStartDate().isBefore(LocalDateTime.now()), "past_task", "manager.task.form.error.past_task");
		}
		
		if (!errors.hasErrors("incorrect_finish")) {
			errors.state(request, entity.getEndingDate().isBefore(entity.getStartDate()), "incorrect_finish", "manager.task.form.error.incorrect_finish");
		}
		
		if (!errors.hasErrors("work_overload")) {
			final Double workloadMin = entity.getWorkload()*60;
			final LocalDateTime start = entity.getStartDate();
			final LocalDateTime end = entity.getEndingDate();
			final Double datesMin = (end.getYear()*525600-525600 + end.getMonthValue()*43200-43200 + 
				end.getDayOfMonth()*1440-1440 + end.getHour()*60-60 + end.getMinute()-1 + 
				end.getSecond()/60-0.016) - (start.getYear()*525600-525600 + 
				start.getMonthValue()*43200-43200 + start.getDayOfMonth()*1440-1440 + 
				start.getHour()*60-60 + start.getMinute()-1 + start.getSecond()/60-0.1);
			errors.state(request, workloadMin > datesMin, "work_overload", "manager.task.form.error.work_overload");
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
		request.unbind(entity, model, "endingDate", "workload", "finished", "privacy");
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
