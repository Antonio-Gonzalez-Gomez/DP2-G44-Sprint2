/*
 * AdministratorDashboardRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("avg(select t.execution_period from Task t)")
	Double averageTaskExecutionPeriod();

	@Query("stdev(select t.execution_period from Task t)")
	Double deviationTaskExecutionPeriod();

	@Query("min(select t.execution_period from Task t)")
	Double minimumTaskExecutionPeriod();

	@Query("max(select t.execution_period from Task t)")
	Double maximumTaskExecutionPeriod();

	@Query("avg(select t.workload from Task t)")
	Double averageTaskWorkload();

	@Query("stdev(select t.workload from Task t)")
	Double deviationTaskWorkload();
	
	@Query("min(select t.workload from Task t)")
	Double minimumTaskWorkload();

	@Query("max(select t.workload from Task t)")
	Double maximumTaskWorkload();

	@Query("select 1.0 * count(a) / (select count(b) from Task b) from Task a where a.privacy = false")
	Double ratioOfPublicTasks();

	@Query("select 1.0 * count(a) / (select count(b) from Task b) from Task a where a.privacy = true")
	Double ratioOfPrivateTasks();

	@Query("select 1.0 * count(a) / (select count(b) from Task b) from Task a where a.finished = true")
	Double ratioOfFinishedTasks();

	@Query("select 1.0 * count(a) / (select count(b) from Task b) from Task a where a.finished = false")
	Double ratioOfUnfinishedTasks();

}
