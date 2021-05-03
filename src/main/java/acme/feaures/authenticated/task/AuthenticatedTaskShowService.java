package acme.feaures.authenticated.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedTaskShowService implements AbstractShowService<Authenticated, Task> {
    // Internal state ---------------------------------------------------------

        @Autowired
        protected AuthenticatedTaskRepository repository;
        @Override
        public boolean authorise(final Request<Task> request) {
            assert request != null;
            boolean result = false;
            int id;
            Task task;
            id = request.getModel().getInteger("id");
            task = this.repository.findOneTaskById(id);
            for(final Task t : this.repository.findtaskspublicfinished()) {
                if (t.getId() == task.getId()) {
                    result = true;
                }
            }
            return result;
        }
        // AbstractShowService<Anonymous, Task> interface --------------------------
        @Override
        public void unbind(final Request<Task> request, final Task entity, final Model model) {
            assert request != null;
            assert entity != null;
            assert model != null;

            request.unbind(entity, model, "title", "description", "link","startDate","endingDate", "workload", "finished", "privacy");
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
}