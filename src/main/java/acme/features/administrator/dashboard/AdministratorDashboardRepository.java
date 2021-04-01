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

	@Query("select avg(t.executionPeriod) from Task t")
	Double averageTaskExecutionPeriod();

	@Query(value = "select stdev(t.executionPeriod) from Task t", nativeQuery = true)
	Double deviationTaskExecutionPeriod();

	@Query("select min(t.executionPeriod) from Task t")
	Double minimumTaskExecutionPeriod();

	@Query("select max(t.executionPeriod) from Task t")
	Double maximumTaskExecutionPeriod();

	@Query("select avg(t.workload) from Task t")
	Double averageTaskWorkload();

	@Query(value = "select stdev(t.workload) from Task t", nativeQuery = true)
	Double deviationTaskWorkload();
	
	@Query("select min(t.workload) from Task t")
	Double minimumTaskWorkload();

	@Query("select max(t.workload) from Task t")
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
