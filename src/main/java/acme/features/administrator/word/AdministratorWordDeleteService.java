package acme.features.administrator.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.filters.Word;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractDeleteService;

@Service
public class AdministratorWordDeleteService implements AbstractDeleteService<Administrator, Word> {
	
	// Internal state ---------------------------------------------------------

			@Autowired
			protected AdministratorWordRepository repository;

			// AbstractDeleteService<Manager, Task> interface -------------------------


			@Override
			public boolean authorise(final Request<Word> request) {
				assert request != null;

				return true;
			}

			@Override
			public void bind(final Request<Word> request, final Word entity, final Errors errors) {
				assert request != null;
				assert entity != null;
				assert errors != null;

				request.bind(entity, errors);
			}

			@Override
			public void unbind(final Request<Word> request, final Word entity, final Model model) {
				assert request != null;
				assert entity != null;
				assert model != null;

				request.unbind(entity, model, "word", "filter");
			}

			@Override
			public Word findOne(final Request<Word> request) {
				assert request != null;

				Word result;
				int id;

				id = request.getModel().getInteger("id");
				result = this.repository.findOneWordById(id);

				return result;
			}

			@Override
			public void validate(final Request<Word> request, final Word entity, final Errors errors) {
				assert request != null;
				assert entity != null;
				assert errors != null;
			}

			@Override
			public void delete(final Request<Word> request, final Word entity) {
				assert request != null;
				assert entity != null;

				this.repository.delete(entity);
			}

}
