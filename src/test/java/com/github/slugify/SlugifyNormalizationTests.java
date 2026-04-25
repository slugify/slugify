package com.github.slugify;

import static com.github.slugify.SlugifyTestConstants.ASSERT_EQUALS_MESSAGE_FORMAT;
import static com.github.slugify.SlugifyTestConstants.DEFAULT_LOCALE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

/**
 * Tests for normalization, transliteration and case handling.
 */
@NoArgsConstructor
class SlugifyNormalizationTests {

  @Test
  /* default */ void givenStringWhenNormalizerUsedThenNormalizesToAscii() {
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
  /* default */ void givenStringWhenTransliteratorUsedThenTransliteratesToAscii() {
    final Slugify slugify = Slugify.builder()
        .transliterator(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "swieze-mleko";
    final String actual = slugify.slugify("Świeże Mleko");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenStringWhenTransliteratorAndPreserveCaseUsedThenCasePreserved() {
    final Slugify slugify = Slugify.builder()
        .lowerCase(false)
        .transliterator(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "Swieze-Mleko";
    final String actual = slugify.slugify("Świeże Mleko");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @Test
  /* default */ void givenStringWhenPreserveCaseUsedThenCasePreserved() {
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
  /* default */ void givenStringWhenLowerCaseUsedThenConvertsToLowerCase() {
    final Slugify slugify = Slugify.builder()
        .lowerCase(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo";
    final String actual = slugify.slugify("Foo");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }
}
