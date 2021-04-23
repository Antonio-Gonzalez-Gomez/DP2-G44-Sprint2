package acme.features.administrator.spamFilter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.filters.Filter;
import acme.entities.filters.Word;

@Service
public class AdministratorSpamFilterValidateService {

	@Autowired
	protected AdministratorSpamFilterRepository repository;
	
	public List<Word> findSpamWords(final int id) {
		return this.repository.findSpamWords(id);
	}
	
	public boolean validate(final String input, final int filterId) {
		final Filter filter = this.repository.findFilters().get(0);
		final List<String> words = this.findSpamWords(filterId).stream()
			.map(x -> x.getWord()).collect(Collectors.toList());
		
		final String text = input.replace(" ", "").toLowerCase();
        final int total = text.length();
        for (final String word : words)
            text.replace(word,"");
        final int noSpam = text.length();
        
        return 100*(total - noSpam)/total <= filter.getThreshold();
	}
}
