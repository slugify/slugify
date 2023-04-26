package com.github.slugify;

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
 * Class for unit testing.
 */
@NoArgsConstructor
class SlugifyTests {
  private static final String ASSERT_EQUALS_MESSAGE_FORMAT = "[%s] \"%s\" equals \"%s\"";
  private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

  @Test
  /* default */ void givenStringWhenNormalizerIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .transliterator(false)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "a";
    final String actual = slugify.slugify("ä");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenStringWhenTransliteratorIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .transliterator(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "b";
    final String actual = slugify.slugify("Б");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenStringWhenHyphenIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .underscoreSeparator(false)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo-bar";
    final String actual = slugify.slugify("foo bar");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenStringWhenUnderscoreIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .underscoreSeparator(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo_bar";
    final String actual = slugify.slugify("foo bar");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenStringWhenLetterCaseIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .lowerCase(false)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "Foo";
    final String actual = slugify.slugify("Foo");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenStringWhenLowerCaseIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .lowerCase(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo";
    final String actual = slugify.slugify("Foo");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @SneakyThrows
  @ParameterizedTest
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  @ValueSource(strings = {"ar", "da", "de", "el", "is", "no", "pl", "ru", "sv", "tr", "uk", "vi",
      "wa"})
  /* default */ void givenStringWhenStringContainsReplacementsThenSlugify(
      final String languageTag) {
    final Locale locale = Locale.forLanguageTag(languageTag);

    try (InputStream resourceBundleInputStream = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(Slugify.BUNDLE_BASE_NAME + "_" + locale.getLanguage()
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
}
