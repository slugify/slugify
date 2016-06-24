package com.github.slugify;

public class Functions {
	public static String slugify(String input) {
		return InitSlugifyTag.getSlugify().slugify(input);
	}
}
