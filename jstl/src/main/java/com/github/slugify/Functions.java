package com.github.slugify;

public class Functions {
	private static Slugify instance;

	private static Slugify getInstance() {
		if (instance == null) {
			instance = new Slugify();
		}

		return instance;
	}

	public static String slugify(String input) {
		return getInstance().slugify(input);
	}
}