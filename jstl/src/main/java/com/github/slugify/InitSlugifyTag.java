package com.github.slugify;

import java.util.Locale;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport;

public class InitSlugifyTag extends SimpleTagSupport {
	private static Slugify slugify;

	public InitSlugifyTag() {
		slugify = getSlugify();
	}

	public static Slugify getSlugify() {
		if (slugify == null) {
			slugify = new Slugify();
		}

		return slugify;
	}

	public void setLowerCase(Object o) {
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

	public void setLocale(Object o) {
		Locale locale = null;
		if (o instanceof Locale) {
			locale = (Locale) o;
		} else if (o instanceof String) {
			locale = SetLocaleSupport.parseLocale((String) o);
		} else {
			throw new RuntimeException("Wrong instance of locale");
		}

		getSlugify().setLocale(locale);
	}
}