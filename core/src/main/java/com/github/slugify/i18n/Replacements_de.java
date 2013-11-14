package com.github.slugify.i18n;

import java.util.ListResourceBundle;

public class Replacements_de extends ListResourceBundle {
	@Override
	protected Object[][] getContents() {
		return new Object[][] {
				{ "\u00C4", "Ae" }, { "\u00E4", "ae" },
				{ "\u00D6", "Oe" }, { "\u00F6", "oe" },
				{ "\u00DC", "Ue" }, { "\u00FC", "ue" },
				{ "\u00DF", "ss" }
		};
	}
}