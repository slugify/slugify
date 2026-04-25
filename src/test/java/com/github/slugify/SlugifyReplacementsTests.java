package com.github.slugify;

import static com.github.slugify.SlugifyTestConstants.ASSERT_EQUALS_MESSAGE_FORMAT;
import static com.github.slugify.SlugifyTestConstants.DEFAULT_LOCALE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests for built-in and custom replacement handling.
 */
@NoArgsConstructor
class SlugifyReplacementsTests {

  @SneakyThrows
  @ParameterizedTest
  @ValueSource(strings = {"ar", "da", "de", "el", "is", "no", "pl", "ru", "sv", "tr", "uk", "vi",
      "wa"})
  /* default */ void givenStringWhenStringContainsReplacementsThenSlugify(
      final String languageTag) {
    final Locale locale = Locale.forLanguageTag(languageTag);

    try (InputStream resourceBundleInputStream = SlugifyReplacementsTests.class
        .getResourceAsStream("/" + Slugify.BUNDLE_BASE_NAME + "_" + locale.getLanguage()
            + ".properties")) {
      if (resourceBundleInputStream == null) {
        throw new FileNotFoundException(Slugify.BUNDLE_BASE_NAME + "_" + locale.getLanguage()
            + ".properties");
      }

      final ResourceBundle replacementsBundle =
          new PropertyResourceBundle(resourceBundleInputStream);

      final Slugify slugify = Slugify.builder()
          .lowerCase(false)
          .locale(locale)
          .build();

      replacementsBundle.keySet().forEach(key -> {
        final String expected = replacementsBundle.getString(key);
        final String actual = slugify.slugify(key);

        assertEquals(expected, actual,
            format(ASSERT_EQUALS_MESSAGE_FORMAT, locale, expected, actual));
      });
    }
  }

  @Test
  /* default */ void givenStringWhenStringContainsCustomReplacementsThenSlugify() {
    final String given = "ä";
    final String expected = "foo";

    final Slugify slugify = Slugify.builder()
        .locale(DEFAULT_LOCALE)
        .customReplacement(given, expected)
        .build();

    final String actual = slugify.slugify(given);

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenStringWithMultipleCustomReplacementsThenAllApplied() {
    final Slugify slugify = Slugify.builder()
        .locale(DEFAULT_LOCALE)
        .customReplacement("ä", "ae")
        .customReplacement("ö", "oe")
        .customReplacement("ü", "ue")
        .build();

    final String expected = "ae-oe-ue";
    final String actual = slugify.slugify("ä ö ü");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenCustomReplacementForBuiltinKeyThenCustomTakesPrecedence() {
    final Locale locale = Locale.GERMAN;

    final Slugify slugify = Slugify.builder()
        .locale(locale)
        .lowerCase(false)
        .customReplacement("ä", "x")
        .build();

    final String actual = slugify.slugify("ä");

    assertEquals("x", actual, format(ASSERT_EQUALS_MESSAGE_FORMAT, locale, "x", actual));
  }

  @Test
  /* default */ void givenCustomReplacementResultMatchingBuiltinKeyThenBuiltinAlsoApplied() {
    final Locale locale = Locale.GERMAN;

    final Slugify slugify = Slugify.builder()
        .locale(locale)
        .customReplacement("X", "ü")
        .build();

    final String actual = slugify.slugify("X");

    assertEquals("ue", actual, format(ASSERT_EQUALS_MESSAGE_FORMAT, locale, "ue", actual));
  }
}
