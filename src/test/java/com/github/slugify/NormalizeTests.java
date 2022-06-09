package com.github.slugify;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Class for testing normalization.
 *
 * @author Danny Trunk
 */
public class NormalizeTests {
  private final Slugify slugify = Slugify.builder().build();

  @Test
  public void shouldReturnSlugifiedString() {
    // given
    final String text = "Hello, world!";

    // when
    final String result = slugify.slugify(text);

    // then
    assertEquals("hello-world", result);
  }

  @Test
  public void shouldTrimWhiteSpacesOtherThanSpace() {
    // given
    String string = "\tHello \tworld \r\t";

    // when
    String result = slugify.slugify(string);

    // then
    assertEquals("hello-world", result);
  }


  @Test
  public void shouldSlugifyAnyPrintableAsciiCharacter() {
    // given
    String string = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`"
        + "abcdefghijklmnopqrstuvwxyz{|}~";

    // when
    String result = slugify.slugify(string);

    // then
    assertEquals("0123456789-abcdefghijklmnopqrstuvwxyz-_-abcdefghijklmnopqrstuvwxyz", result);
  }

  @Test
  public void shouldReplacePlusSignToSeparator() {
    // given
    String string = "\tHello+\tworld \r\t";

    // when
    String result = slugify.slugify(string);

    // then
    assertEquals("hello-world", result);
  }

  @Test
  public void shouldReturnEmptyStringIfNullGiven() {
    // given
    String string = null;

    // when
    String result = slugify.slugify(string);

    // then
    assertEquals("", result);
  }

  @Test
  public void shouldNormalizeRepeatedHyphensToSingleHyphenWithHyphenSeparator() {
    //given
    String string = "a---b___c";

    //when
    String result = slugify.slugify(string);

    //then
    assertEquals("a-b___c", result);
  }
}
