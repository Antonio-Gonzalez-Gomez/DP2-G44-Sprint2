package acme.features.manager.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected ManagerTaskRepository repository;

}
