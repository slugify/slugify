package com.github.slugify;

import java.text.Normalizer;
import java.util.Map;

public class Slugify {
	private static Slugify instance;
	private Map<String, String> customReplacements;

	public static Slugify getInstance() {
		if (instance == null) {
			instance = new Slugify();
		}

		return instance;
	}

	public Map<String, String> getCustomReplacements() {
		return customReplacements;
	}

	public void setCustomReplacements(Map<String, String> customReplacements) {
		this.customReplacements = customReplacements;
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

		return Normalizer.normalize(input, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "")
				.replaceAll("[^a-zA-Z0-9 ]", "")
				.replaceAll("\\s+", "-");
	}
}