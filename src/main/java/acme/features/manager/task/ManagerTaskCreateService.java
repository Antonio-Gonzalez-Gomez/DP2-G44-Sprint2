package acme.features.manager.task;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.features.administrator.spamFilter.AdministratorSpamFilterValidateService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;


@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected ManagerTaskRepository repository;
		
		@Autowired
		private AdministratorSpamFilterValidateService filter;
		
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
		
		if (this.filter.validate(entity.getTitle()))
			errors.state(request, !this.filter.validate(entity.getTitle()), "title", "manager.task.form.error.title_spam");
		if (this.filter.validate(entity.getDescription()))
			errors.state(request, !this.filter.validate(entity.getDescription()), "description", "manager.task.form.error.description_spam");
		assert errors != null;
		
		//Asignación de execution period
		if(entity.getStartDate() != null && entity.getEndingDate() != null) entity.setExecutionPeriod();
		
		final boolean finish = entity.getFinished();
		
		//Validación de fechas
		if (!errors.hasErrors("past_task") && entity.getStartDate() != null) {
			errors.state(request, (entity.getStartDate().after(Date.from(Instant.now())) || finish), "startDate", "manager.task.form.error.past_task");
		}
		
		if (!errors.hasErrors("incorrect_finish") && entity.getStartDate() != null && entity.getEndingDate() != null) {
			errors.state(request, entity.getEndingDate().after(entity.getStartDate()), "endingDate", "manager.task.form.error.incorrect_finish");
		}
		
		//Validación de workload previo y asignación de workload real
		final Double false_workload = entity.getFakeWorkload();
		if(false_workload!=null) {
			if (false_workload <= 0) {
				errors.state(request, false_workload > 0, "fakeWorkload", "manager.task.form.error.negative");
			}
			final BigDecimal bd = new BigDecimal(String.valueOf(false_workload));
			final BigDecimal decimals = bd.subtract(new BigDecimal(bd.intValue()));
			if(decimals.precision()>2) {
				errors.state(request, (decimals.precision()<=2), "fakeWorkload", "manager.task.form.error.incorrect_decimals");
			}
			if(!errors.hasErrors("over_60")) {
				final BigDecimal limit = BigDecimal.valueOf(0.6);
				errors.state(request, (decimals.compareTo(limit)<=0), "fakeWorkload", "manager.task.form.error.over_60");
			}
			final Double real_decimals = decimals.doubleValue()/0.6;
			final Double real_workload = false_workload.intValue() + real_decimals;
			entity.setWorkload(real_workload);
			entity.setFakeWorkload(false_workload);
		}
		
		//Validación de workload real
		if (!errors.hasErrors("big_workload") && entity.getWorkload() != null) {
			errors.state(request, (entity.getWorkload()<=99.99), "fakeWorkload", "manager.task.form.error.big_workload");
		}
		
		if (!errors.hasErrors("work_overload") && entity.getExecutionPeriod() != null && entity.getWorkload() != null) {
			errors.state(request, entity.getWorkload() < entity.getExecutionPeriod(), "fakeWorkload", "manager.task.form.error.work_overload");
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
		request.unbind(entity, model, "endingDate", "workload", "fakeWorkload", "finished", "privacy", "executionPeriod");
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
