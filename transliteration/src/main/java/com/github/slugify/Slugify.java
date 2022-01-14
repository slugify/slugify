package com.github.slugify;

import com.ibm.icu.text.Transliterator;

import java.util.Map;

public class Slugify extends SlugifyLite {
	private final static String ASCII = "Cyrillic-Latin; Any-Latin; Latin-ASCII; [^\\p{Print}] Remove; ['\"] Remove; Any-Lower";

	private boolean transliterator = false;

	@Deprecated
	public Slugify(boolean lowerCase) {
		super(lowerCase);
	}

	public Slugify() {
	}

	public Slugify withTransliterator(final boolean transliterator) {
		this.transliterator = transliterator;
		return this;
	}

	@Override
	public Slugify withCustomReplacement(final String from, final String to) {
		return (Slugify) super.withCustomReplacement(from, to);
	}

	@Override
	public Slugify withCustomReplacements(final Map<String, String> customReplacements) {
		return (Slugify) super.withCustomReplacements(customReplacements);
	}

	@Override
	public Slugify withUnderscoreSeparator(final boolean underscoreSeparator) {
		return (Slugify) super.withUnderscoreSeparator(underscoreSeparator);
	}

	@Override
	public Slugify withLowerCase(final boolean lowerCase) {
		return (Slugify) super.withLowerCase(lowerCase);
	}

	@Override
	protected String process(String input) {
		if (transliterator) {
			return transliterate(input);
		} else {
			return normalize(input);
		}
	}

	private String transliterate(final String input) {
		String text = Transliterator.getInstance(ASCII).transliterate(input);
		text = matchAndReplace(text);
		return text;
	}
}
