package com.github.slugify;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class Slugify {
	private static final String BUILTIN_REPLACEMENTS_FILENAME = "replacements.properties";
	private static final Properties replacements = new Properties();

	private final Map<String, String> customReplacements = new HashMap<String, String>();

	private boolean underscoreSeparator = false;
	private boolean lowerCase = true;

	@Deprecated
	public Slugify(boolean lowerCase) {
		this();

		withLowerCase(lowerCase);
	}

	public Slugify() {
		loadReplacements(BUILTIN_REPLACEMENTS_FILENAME);
	}

	public Slugify withCustomReplacement(String from, String to) {
		customReplacements.put(from, to);
		return this;
	}

	public Slugify withCustomReplacements(Map<String, String> customReplacements) {
		this.customReplacements.putAll(customReplacements);
		return this;
	}

	public Slugify withUnderscoreSeparator(boolean underscoreSeparator) {
		this.underscoreSeparator = underscoreSeparator;
		return this;
	}

	public Slugify withLowerCase(boolean lowerCase) {
		this.lowerCase = lowerCase;
		return this;
	}

	public String slugify(String input) {
		if (isNullOrBlank(input)) {
			return "";
		}

		input = input.trim();
		input = customReplacements(input);
		input = builtInReplacements(input);
		input = normalize(input);

		if (lowerCase) {
			input = input.toLowerCase();
		}

		return input;
	}

	public Map<String, String> getCustomReplacements() {
		return customReplacements;
	}

	private String customReplacements(String input) {
		Map<String, String> customReplacements = getCustomReplacements();
		for (Entry<String, String> entry : customReplacements.entrySet()) {
			input = input.replace(entry.getKey(), entry.getValue());
		}

		return input;
	}

	private String builtInReplacements(String input) {
		for (Entry<Object, Object> e : replacements.entrySet()) {
			input = input.replace(e.getKey().toString(), e.getValue().toString());
		}

		return input;
	}

	private Slugify loadReplacements(final String resourceFileName) {
		if (replacements.size() > 0) {
			return this;
		}

		try {
			InputStream replacementsStream = getClass().getClassLoader().getResourceAsStream(resourceFileName);
			replacements.load(replacementsStream);
			replacementsStream.close();
			return this;
		} catch (Exception e) {
			throw new RuntimeException("replacements.properties not loaded!", e);
		}
	}

	private boolean isNullOrBlank(final String string) {
		return string == null || string.trim().length() == 0;
	}

	private String normalize(String input) {
		input = Normalizer.normalize(input, Normalizer.Form.NFKD)
				.replaceAll("[^\\p{ASCII}]+", "")
				.replaceAll("(?:[^\\w+]|\\s|\\+)+", underscoreSeparator ? "_" : "-")
				.replaceAll("^-|-$", "");
		return input;
	}
}
