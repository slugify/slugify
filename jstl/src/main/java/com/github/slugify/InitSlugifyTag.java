package com.github.slugify;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class InitSlugifyTag extends SimpleTagSupport {
	private static Slugify slugify = null;

	public InitSlugifyTag() {
		slugify = getSlugify();
	}

	public static synchronized Slugify getSlugify() {
		if (slugify == null) {
			slugify = new Slugify();
		}

		return slugify;
	}

	public void setLowerCase(Object obj) {
		boolean lowerCase = true;

		if (Boolean.class.isInstance(obj)) {
			lowerCase = (Boolean) obj;
		} else if (String.class.isInstance(obj)) {
			lowerCase = Boolean.valueOf(obj.toString());
		} else {
			throw new RuntimeException("Wrong instance of lowerCase");
		}

		slugify = getSlugify().withLowerCase(lowerCase);
	}

	public void setUnderscoreSeparator(Object obj) {
		boolean underscoreSeparator = false;

		if (Boolean.class.isInstance(obj)) {
			underscoreSeparator = (Boolean) obj;
		} else if (String.class.isInstance(obj)) {
			underscoreSeparator = Boolean.valueOf(obj.toString());
		} else {
			throw new RuntimeException("Wrong instance of underscoreSeparator");
		}

		slugify = getSlugify().withUnderscoreSeparator(underscoreSeparator);
	}
}
