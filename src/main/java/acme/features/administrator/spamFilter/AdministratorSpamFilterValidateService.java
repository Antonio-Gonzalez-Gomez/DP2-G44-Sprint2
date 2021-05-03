package acme.features.administrator.spamFilter;

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
	
	private boolean compoundWordEquals(final String[] words, final List<String> text, int i, final int j) {
		if (i == words.length)
			return true;
		if (text.size() <= i + j)
			return false;
		else {
			return words[i].equals(text.get(i + j)) && this.compoundWordEquals(words, text, ++i, j);
		}
	}
	
	public boolean validate2(final String input) {
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
        System.out.println("THRESHOLD: " + filter.getThreshold());
        System.out.println("CARACTERES TOTALES: " + total);
        System.out.println("CARACTERES NO SPAM: " + noSpam);
        return 100*(total - noSpam)/total >= filter.getThreshold();
	}
	
	public boolean validate(final String input) {
		List<String> text;
		text = Arrays.asList(input.toLowerCase().trim().split("\\s+"));
		System.out.println("INPUT: " + text);
		int i = 0, j = 0, k;
		final int total = text.size();
		
		final Filter filter = this.repository.findFilters().get(0);
		final List<String[]> words = this.repository.findSpamWords().stream()
			.map(x -> x.getWord().toLowerCase().trim().split("\\s+"))
			.collect(Collectors.toList());
		
		while (i < words.size()) {
			final String[] word = words.get(0);
			System.out.println("PALABRA SPAM: " + word[0]);
			j = 0;
			
			if (word.length == 1) {
				while (j<text.size())
					if (text.get(j).equals(word[0]))
						text.remove(j);
					else
						j++;
			}
			
			else
			{
				while (j<text.size()) {
					if (this.compoundWordEquals(word, text, i, j)) {
						for (k=0 ; k<word.length ; k++)
							text.remove(k + j);
					}
					else
						j++;
				}
			}
			
			i++;
		}
		
		final int noSpam = text.size();
        System.out.println("THRESHOLD: " + filter.getThreshold());
        System.out.println("PALABRAS TOTALES: " + total);
        System.out.println("PALABRAS NO SPAM: " + noSpam);
        return 100*(total - noSpam)/total >= filter.getThreshold();
	}
}
