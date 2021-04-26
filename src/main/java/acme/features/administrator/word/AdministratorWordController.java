package acme.features.administrator.word;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.filters.Word;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/word/")
public class AdministratorWordController extends AbstractController<Administrator, Word> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorWordListService listService;
	
	@Autowired
	protected AdministratorWordShowService showService;
	
	@Autowired
	protected AdministratorWordCreateService createService;
	
	@Autowired
	protected AdministratorWordDeleteService deleteService;
	
	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}

}
