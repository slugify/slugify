package com.github.slugify;

import java.io.IOException;

public class Functions {
	public static String slugify(String input) throws IOException {
		return InitSlugifyTag.getSlugify().slugify(input);
	}
}