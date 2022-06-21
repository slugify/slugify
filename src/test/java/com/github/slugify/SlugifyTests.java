package com.github.slugify;

import static java.lang.String.format;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class for unit testing.
 */
@NoArgsConstructor
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SlugifyTests {
  private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

  private static final List<Locale> LOCALES = Stream.of("ar", "da", "de", "el", "no", "pl", "ru",
          "sv", "tr", "vi", "wa")
      .map(Locale::forLanguageTag)
      .collect(Collectors.toList());

  @Test
  @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
  public void givenStringWhenNormalizerIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .transliterator(false)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "a";
    final String actual = slugify.slugify("ä");

    assertEquals(expected, actual);
  }

  @Test
  @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
  public void givenStringWhenTransliteratorIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .transliterator(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "b";
    final String actual = slugify.slugify("Б");

    assertEquals(expected, actual);
  }

  @Test
  @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
  public void givenStringWhenHyphenIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .underscoreSeparator(false)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo-bar";
    final String actual = slugify.slugify("foo bar");

    assertEquals(expected, actual);
  }

  @Test
  @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
  public void givenStringWhenUnderscoreIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .underscoreSeparator(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo_bar";
    final String actual = slugify.slugify("foo bar");

    assertEquals(expected, actual);
  }

  @Test
  @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
  public void givenStringWhenLetterCaseIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .lowerCase(false)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "Foo";
    final String actual = slugify.slugify("Foo");

    assertEquals(expected, actual);
  }

  @Test
  @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
  public void givenStringWhenLowerCaseIsUsedThenSlugify() {
    final Slugify slugify = Slugify.builder()
        .lowerCase(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo";
    final String actual = slugify.slugify("Foo");

    assertEquals(expected, actual);
  }

  @Test
  @SneakyThrows
  @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops",
      "PMD.JUnitAssertionsShouldIncludeMessage", "PMD.JUnitTestsShouldIncludeAssert"})
  public void givenStringWhenStringContainsReplacementsThenSlugify() {
    for (final Locale locale : LOCALES) {
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

          assertEquals(expected, actual, locale.toString());
        });
      }
    }
  }

  @Test
  @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
  public void givenStringWhenStringContainsCustomReplacementsThenSlugify() {
    final String given = "ä";
    final String expected = "foo";

    final Slugify slugify = Slugify.builder()
        .locale(DEFAULT_LOCALE)
        .customReplacement(given, expected)
        .build();

    final String actual = slugify.slugify(given);

    assertEquals(expected, actual);
  }

  private void assertEquals(final String expected, final String actual) {
    assertEquals(expected, actual, null);
  }

  private void assertEquals(final String expected, final String actual, final String comment) {
    assertEquals("\"%s\" equals \"%s\"" + Optional.ofNullable(comment).map(s -> " (%s)")
        .orElse(""), expected, actual, comment);
  }

  private void assertEquals(final String format, final String expected, final String actual,
                            final String comment) {
    Assert.assertEquals(format(format, expected, actual, comment), expected, actual);
  }
}
