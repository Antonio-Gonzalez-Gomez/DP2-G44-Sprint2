package acme.features.administrator.spamFilter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.filters.Filter;

@Service
public class AdministratorSpamFilterValidateService {

	@Autowired
	protected AdministratorSpamFilterRepository repository;
	
	public boolean validate(final String input) {
		String text = input.replace(" ", "").toLowerCase();
        final int total = text.length();
        if (total == 0)
        	return false;
		
		final Filter filter = this.repository.findFilters().get(0);
		final List<String> words = this.repository.findSpamWords().stream()
			.map(x -> x.getWord()).collect(Collectors.toList());
        for (final String word : words)
            text = text.replace(word,"");
        final int noSpam = text.length();
        
        return 100*(total - noSpam)/total >= filter.getThreshold();
	}
}
