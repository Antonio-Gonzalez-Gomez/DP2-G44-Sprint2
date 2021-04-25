package acme.features.administrator.spamFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.filters.Filter;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorSpamFilterUpdateService implements AbstractUpdateService<Administrator, Filter> {

	// Internal state ---------------------------------------------------------

		@Autowired
		protected AdministratorSpamFilterRepository repository;
		
		// AbstractListService<Employer, Job> -------------------------------------


		@Override
		public boolean authorise(final Request<Filter> request) {
			assert request != null;

			return true;
		}

		@Override
		public void validate(final Request<Filter> request, final Filter entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			
			assert errors != null;
			
			if (!errors.hasErrors("incorrect_threshold") && entity.getThreshold() != null) {
				errors.state(request, entity.getThreshold()>= 0.0 && entity.getThreshold()<=100.0, "threshold", "administrator.spamfilter.form.title.incorrect_threshold");
			}
			
			
		}

		@Override
		public void bind(final Request<Filter> request, final Filter entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;


			request.bind(entity, errors);
		}

		@Override
		public void unbind(final Request<Filter> request, final Filter entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;
			
			request.unbind(entity, model, "threshold");
			model.setAttribute("readonly", false);
		}

		@Override
		public Filter findOne(final Request<Filter> request) {
			assert request != null;

			Filter result;
			
			result = this.repository.findFilters().get(0);

			return result;
		}

		@Override
		public void update(final Request<Filter> request, final Filter entity) {
			assert request != null;
			assert entity != null;
		
			this.repository.save(entity);
		}
	
}
