package com.github.slugify;

import java.io.IOException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class InitSlugifyTag extends SimpleTagSupport {
	private static Slugify slugify = null;

	public InitSlugifyTag() throws IOException {
		slugify = getSlugify();
	}

	public static synchronized Slugify getSlugify() throws IOException {
		if (slugify == null) {
			slugify = new Slugify();
		}

		return slugify;
	}

	public void setLowerCase(Object o) throws IOException {
		boolean lowerCase = true;
		if (o instanceof Boolean) {
			lowerCase = (Boolean) o;
		} else if (o instanceof String) {
			lowerCase = Boolean.valueOf((String) o);
		} else {
			throw new RuntimeException("Wrong instance of lowerCase");
		}

		getSlugify().setLowerCase(lowerCase);
	}
}
