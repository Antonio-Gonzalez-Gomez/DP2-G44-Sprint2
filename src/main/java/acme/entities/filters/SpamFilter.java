package acme.entities.filters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SpamFilter{

	protected List<String> spamWords;
	
	protected Double threshold;

	public SpamFilter(final String file, final Double threshold) {
		super();
		try {
			this.spamWords = Files.readAllLines(Paths.get(file));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.threshold = threshold;
	}
	
	public boolean validate(final String input) {
		final String text = input.replace(" ", "").toLowerCase();
		final int total = text.length();
		for (final String word : this.spamWords)
			text.replace(word,"");
		final int noSpam = text.length();
		return 100*(total - noSpam)/total <= this.threshold;
	}
	
	
	
	
}
