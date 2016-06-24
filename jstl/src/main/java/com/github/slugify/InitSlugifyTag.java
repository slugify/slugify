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

	public void setLowerCase(Object obj) {
		boolean lowerCase = true;
		if (Boolean.class.isInstance(obj)) {
			lowerCase = (Boolean) obj;
		} else if (String.class.isInstance(obj)) {
			lowerCase = Boolean.valueOf(obj.toString());
		} else {
			throw new RuntimeException("Wrong instance of lowerCase");
		}

		getSlugify().setLowerCase(lowerCase);
	}
}
