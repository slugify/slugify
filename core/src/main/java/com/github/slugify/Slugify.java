package com.github.slugify;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Slugify {
	private Map<String, String> customReplacements;
	private ResourceBundle bundle;
	private Locale locale;

	public Slugify() {
		this(Locale.getDefault());
	}

	public Slugify(final Locale locale) {
		setBundle(ResourceBundle.getBundle("replacements", locale));
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

		if (getLocale().equals(getBundle().getLocale())) {
			for (String key : bundle.keySet()) {
				input = input.replace(key, bundle.getString(key));
			}
		}

		return Normalizer.normalize(input, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "")
				.replaceAll("[^a-zA-Z0-9 ]", "")
				.replaceAll("\\s+", "-");
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}