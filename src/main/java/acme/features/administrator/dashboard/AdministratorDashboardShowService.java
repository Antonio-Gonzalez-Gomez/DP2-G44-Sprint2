/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, //
			"averageTaskExecutionPeriod", "deviationTaskExecutionPeriod", // 
			"minimumTaskExecutionPeriod", "maximumTaskExecutionPeriod", //
			"averageTaskWorkload", "deviationTaskWorkload", //
			"minimumTaskWorkload", "maximumTaskWorkload", //
			"ratioOfPublicTasks", "ratioOfPrivateTasks", //
			"ratioOfFinishedTasks", "ratioOfUnfinishedTasks");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result;
		final Double averageTaskExecutionPeriod;
		final Double deviationTaskExecutionPeriod;
		final Double minimumTaskExecutionPeriod;
		final Double maximumTaskExecutionPeriod;
		final Double averageTaskWorkload;
		final Double deviationTaskWorkload;
		final Double minimumTaskWorkload;
		final Double maximumTaskWorkload;
		final Double ratioOfPublicTasks;
		final Double ratioOfPrivateTasks;
		final Double ratioOfFinishedTasks;
		final Double ratioOfUnfinishedTasks;

		averageTaskExecutionPeriod = this.repository.averageTaskExecutionPeriod();
		deviationTaskExecutionPeriod = this.repository.deviationTaskExecutionPeriod();
		minimumTaskExecutionPeriod = this.repository.minimumTaskExecutionPeriod();
		maximumTaskExecutionPeriod = this.repository.maximumTaskExecutionPeriod();
		averageTaskWorkload = this.repository.averageTaskWorkload();
		deviationTaskWorkload = this.repository.deviationTaskWorkload();
		minimumTaskWorkload = this.repository.minimumTaskWorkload();
		maximumTaskWorkload = this.repository.maximumTaskWorkload();
		ratioOfPublicTasks = this.repository.ratioOfPublicTasks();
		ratioOfPrivateTasks = this.repository.ratioOfPrivateTasks();
		ratioOfFinishedTasks = this.repository.ratioOfFinishedTasks();
		ratioOfUnfinishedTasks = this.repository.ratioOfUnfinishedTasks();

		result = new Dashboard();
		result.setAverageTaskExecutionPeriod(averageTaskExecutionPeriod);
		result.setDeviationTaskExecutionPeriod(deviationTaskExecutionPeriod);
		result.setMinimumTaskExecutionPeriod(minimumTaskExecutionPeriod);
		result.setMaximumTaskExecutionPeriod(maximumTaskExecutionPeriod);
		result.setAverageTaskWorkload(averageTaskWorkload);
		result.setDeviationTaskWorkload(deviationTaskWorkload);
		result.setMinimumTaskWorkload(minimumTaskWorkload);
		result.setMaximumTaskWorkload(maximumTaskWorkload);
		result.setRatioOfPublicTasks(ratioOfPublicTasks);
		result.setRatioOfPrivateTasks(ratioOfPrivateTasks);
		result.setRatioOfFinishedTasks(ratioOfFinishedTasks);
		result.setRatioOfUnfinishedTasks(ratioOfUnfinishedTasks);

		return result;
	}

}
