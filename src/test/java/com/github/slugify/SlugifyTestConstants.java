package com.github.slugify;

import java.util.Locale;
import lombok.experimental.UtilityClass;

/**
 * Shared constants for Slugify test classes.
 */
@UtilityClass
class SlugifyTestConstants {
  /* default */ static final String ASSERT_EQUALS_MESSAGE_FORMAT = "[%s] \"%s\" equals \"%s\"";
  /* default */ static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
}
