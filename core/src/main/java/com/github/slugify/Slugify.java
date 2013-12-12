package com.github.slugify;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Slugify {
	private Map<String, String> customReplacements;
	private ResourceBundle bundle;
	private boolean lowerCase;
	private Locale locale;

	public Slugify() {
		this(true, Locale.getDefault());
	}

	public Slugify(final boolean lowerCase) {
		this(lowerCase, Locale.getDefault());
	}

	public Slugify(final Locale locale) {
		this(true, locale);
	}

	public Slugify(final boolean lowerCase, final Locale locale) {
		setLowerCase(lowerCase);
		setLocale(locale);
	}

	public String slugify(String input) {
		if (input == null) {
			return "";
		}

		input = input.trim();

		Map<String, String> customReplacements = getCustomReplacements();
		if (customReplacements != null) {
			for (Map.Entry<String, String> entry : customReplacements.entrySet()) {
				input = input.replace(entry.getKey(), entry.getValue());
			}
		}

		if (getBundle() != null && getLocale().getLanguage().equals(getBundle().getLocale().getLanguage())) {
			for (String key : bundle.keySet()) {
				input = input.replace(key, bundle.getString(key));
			}
		}

		input = Normalizer.normalize(input, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "")
				.replaceAll("[^\\w+]", "-")
				.replaceAll("\\s+", "-")
				.replaceAll("[-]+", "-")
				.replaceAll("^-", "")
				.replaceAll("-$", "");

		if (getLowerCase()) {
			input = input.toLowerCase(getLocale());
		}

		return input;
	}

	public Map<String, String> getCustomReplacements() {
		return customReplacements;
	}

	public void setCustomReplacements(Map<String, String> customReplacements) {
		this.customReplacements = customReplacements;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public boolean getLowerCase() {
		return lowerCase;
	}

	public void setLowerCase(boolean lowerCase) {
		this.lowerCase = lowerCase;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;

		try {
			setBundle(ResourceBundle.getBundle("replacements", locale));
		} catch (MissingResourceException e) {}
	}
}
