package com.github.slugify;

public class Functions {
	public static String slugify(String input) throws IOException {
		return InitSlugifyTag.getSlugify().slugify(input);
	}
}
