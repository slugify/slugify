package com.github.slugify;

import static com.github.slugify.SlugifyTestConstants.ASSERT_EQUALS_MESSAGE_FORMAT;
import static com.github.slugify.SlugifyTestConstants.DEFAULT_LOCALE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests for separator and input handling.
 */
@NoArgsConstructor
class SlugifySeparatorTests {

  @Test
  /* default */ void givenStringWhenHyphenSeparatorUsedThenJoinsWithHyphen() {
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
  /* default */ void givenStringWhenUnderscoreSeparatorUsedThenJoinsWithUnderscore() {
    final Slugify slugify = Slugify.builder()
        .underscoreSeparator(true)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo_bar";
    final String actual = slugify.slugify("foo bar");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  /* default */ void givenNullOrBlankStringWhenSlugifyThenReturnEmpty(final String input) {
    final Slugify slugify = Slugify.builder()
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "";
    final String actual = slugify.slugify(input);

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }

  @ParameterizedTest
  @ValueSource(booleans = {false, true})
  /* default */ void givenStringWithLeadingAndTrailingSpecialCharsWhenSlugifyThenTrimmed(
      final boolean underscoreSeparator) {
    final Slugify slugify = Slugify.builder()
        .underscoreSeparator(underscoreSeparator)
        .locale(DEFAULT_LOCALE)
        .build();

    final String expected = "foo";
    final String actual = slugify.slugify("!foo!");

    assertEquals(expected, actual,
        format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
  }
}
