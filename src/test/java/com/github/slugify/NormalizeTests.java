package com.github.slugify;

import static org.junit.Assert.assertEquals;

import lombok.NoArgsConstructor;
import org.junit.Test;

/**
 * Class for testing normalization.
 *
 * @author Danny Trunk
 */
@NoArgsConstructor
public class NormalizeTests {
  private final Slugify slugify = Slugify.builder().build();

  @Test
  public void shouldReturnSlugifiedString() {
    // given
    final String text = "Hello, world!";

    // when
    final String result = slugify.slugify(text);

    // then
    assertEquals("Should equals \"hello-world\"", "hello-world", result);
  }

  @Test
  public void shouldTrimWhiteSpacesOtherThanSpace() {
    // given
    final String string = "\tHello \tworld \r\t";

    // when
    final String result = slugify.slugify(string);

    // then
    assertEquals("Should equals \"hello-world\"", "hello-world", result);
  }


  @Test
  public void shouldSlugifyAnyPrintableAsciiCharacter() {
    // given
    final String string = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`"
        + "abcdefghijklmnopqrstuvwxyz{|}~";

    // when
    final String result = slugify.slugify(string);

    // then
    assertEquals("Should equals \"0123456789-abcdefghijklmnopqrstuvwxyz-_-"
        + "abcdefghijklmnopqrstuvwxyz\"", "0123456789-abcdefghijklmnopqrstuvwxyz-_-"
        + "abcdefghijklmnopqrstuvwxyz", result);
  }

  @Test
  public void shouldReplacePlusSignToSeparator() {
    // given
    final String string = "\tHello+\tworld \r\t";

    // when
    final String result = slugify.slugify(string);

    // then
    assertEquals("Should equals \"hello-world\"", "hello-world", result);
  }

  @Test
  public void shouldReturnEmptyStringIfNullGiven() {
    // given
    final String string = null;

    // when
    final String result = slugify.slugify(string);

    // then
    assertEquals("Should equals \"\"", "", result);
  }

  @Test
  public void shouldNormalizeRepeatedHyphensToSingleHyphenWithHyphenSeparator() {
    //given
    final String string = "a---b___c";

    //when
    final String result = slugify.slugify(string);

    //then
    assertEquals("Should equals \"a-b___c\"", "a-b___c", result);
  }
}
