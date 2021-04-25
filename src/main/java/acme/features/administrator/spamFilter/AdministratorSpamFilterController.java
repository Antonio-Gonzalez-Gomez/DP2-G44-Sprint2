package acme.features.administrator.spamFilter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.filters.Filter;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/spamfilter/")
public class AdministratorSpamFilterController extends AbstractController<Administrator, Filter>  {
	
	// Internal state ---------------------------------------------------------

		
		@Autowired
		protected AdministratorSpamFilterUpdateService updateService;

		// Constructors -----------------------------------------------------------


		@PostConstruct
		protected void initialise() {
			super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		}

}
