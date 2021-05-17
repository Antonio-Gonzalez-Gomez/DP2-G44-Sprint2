package acme.features.administrator.spamFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.filters.Filter;

@Service
public class AdministratorSpamFilterValidateService {

	@Autowired
	protected AdministratorSpamFilterRepository repository;
	
	private boolean findSpamExpression(final String[] spamWord, final List<String> inputWords, int i, final int j) {
		if (i == spamWord.length)
			return true;
		else if (inputWords.size() <= i + j)
			return false;
		else {
			return spamWord[i].equals(inputWords.get(i + j)) && this.findSpamExpression(spamWord, inputWords, ++i, j);
		}
	}
	
	public boolean validate(final String input) {
		List<String> inputWords;
		inputWords = new ArrayList<String>(Arrays.asList(input.toLowerCase().trim().split("\\s+")));
		int i = 0, j = 0, k;
		final int total = inputWords.size();
		if (total == 0)
			return false;
		
		final Filter filter = this.repository.findFilters().get(0);
		final List<String[]> spamWords = this.repository.findSpamWords().stream()
			.map(x -> x.getWord().toLowerCase().trim().split("\\s+"))
			.collect(Collectors.toList());
		
		while (i < spamWords.size()) {
			
			final String[] spamWord = spamWords.get(i);
			j = 0;
			
			while (j<inputWords.size()) {
				if (this.findSpamExpression(spamWord, inputWords, 0, j))
					for (k = spamWord.length - 1 ; k>=0 ; k--)
						inputWords.remove(k + j);
				else
					j++;
			}
			
			i++;
		}
		
		final int noSpam = inputWords.size();
        return 100*(total - noSpam)/total >= filter.getThreshold();
	}
}
