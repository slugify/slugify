package com.github.slugify;

public class Functions {
	public static String slugify(String input) {
		return Slugify.getInstance().slugify(input);
	}
}