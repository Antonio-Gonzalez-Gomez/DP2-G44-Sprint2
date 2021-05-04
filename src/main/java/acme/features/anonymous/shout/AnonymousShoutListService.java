package acme.features.anonymous.shout;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.shouts.Shout;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractListService;

@Service
public class AnonymousShoutListService implements AbstractListService<Anonymous, Shout> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnonymousShoutRepository repository;


	// AbstractListService<Administrator, Shout> interface --------------

	@Override
	public boolean authorise(final Request<Shout> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Shout> request, final Shout entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "author", "text", "moment");
	}

	@Override
	public Collection<Shout> findMany(final Request<Shout> request) {
		assert request != null;

		final Collection<Shout> result = new ArrayList<Shout>();
		Collection<Shout> allshouts;

		allshouts = this.repository.findMany();
		final Date monthAgo = Date.from(Instant.now());
		final long monthMiliseconds = (long) 2592000000.;
		monthAgo.setTime(monthAgo.getTime() - monthMiliseconds);
		
		for(final Shout s: allshouts) {
			if(s.getMoment().getTime()<monthAgo.getTime()) continue;
			result.add(s);
		}

		return result;
	}

}
