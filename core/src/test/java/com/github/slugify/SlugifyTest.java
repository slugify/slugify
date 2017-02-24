package com.github.slugify;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SlugifyTest {
	@Test
	public void shouldReturnSlugifiedString() {
		// given
		String string = "Hello world";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("hello-world", result);
	}

	@Test
	public void shouldReplaceSpacesWithSeparator() {
		// given
		String string = "Hello world ";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("hello-world", result);
	}

	@Test
	public void shouldTrimWhiteSpacesOtherThanSpace() {
		// given
		String string = "\tHello \tworld \r\t";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("hello-world", result);
	}


	@Test
	public void shouldSlugifyAnyPrintableASCIICharacter() {
		// given
		String string = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("0123456789-abcdefghijklmnopqrstuvwxyz-_-abcdefghijklmnopqrstuvwxyz", result);
	}

	@Test
	public void shouldSlugifyExtendedASCIICharacters() {
		// given
		String string = "€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæç"
				+ "èéêëìíîïðñòóôõö÷øùúûüýþÿĄĆĘŁŃÓŚŹŻąćęłńóśźż";

		// when
		String result = new Slugify().slugify(string);

		// the		
		assertEquals("sz-tmszy-a-23-1o141234aaaaaeaaaeceeeeiiiinoooooeoeuuuueyssaaaaaeaaaeceeeeiiiinoooooeoeuuuueyyacelnoszzacelnoszz", result);
	}

	@Test
	public void shouldUseBuiltInReplacements() {
		// given
		String string = "ÅÄÆÖØÜåäæöøüß";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("aaaeaeoeoeueaaaeaeoeoeuess", result);
	}

	@Test
	public void shouldReplaceCharactersAccordingToCustomReplacement() {
		// given
		String string = "Hello leet!";

		// when
		String result = new Slugify().withCustomReplacement("leet", "1337").slugify(string);

		// then
		assertEquals("hello-1337", result);
	}

	@Test
	public void shouldReplaceCharactersAccordingToCustomReplacements() {
		// given
		String string = "this is awesome!";
		HashMap<String, String> customReplacements = new HashMap<String, String>() {{
			put("this", "that");
		}};

		// when
		String result = new Slugify().withCustomReplacements(customReplacements).slugify(string);

		// then
		assertEquals("that-is-awesome", result);
	}

	@Test
	public void shouldReturnDefinedCustomReplacements() {
		// given
		HashMap<String, String> customReplacements = new HashMap<String, String>() {{
			put("this", "that");
		}};

		Slugify slugify = new Slugify().withCustomReplacements(customReplacements);

		// when
		Map<String, String> gotCustomReplacements = slugify.getCustomReplacements();

		// then
		assertEquals(customReplacements, gotCustomReplacements);
	}

	@Test
	public void shouldTransliterateCyrillicProperly() {
		// given
		String string = "Смысловые галлюцинации";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("smyslovye-gallyutsinatsii", result);
	}

	@Test
	public void shouldTransliteratePolishProperly() {
		// given
		String string = "Zażółć gęślą jaźń.";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("zazolc-gesla-jazn", result);
	}

	@Test
	public void shouldSlugifyStringWithoutChangingCase() {
		// given
		String string = "\tHello \tworld \r\t";

		// when
		String result = new Slugify().withLowerCase(false).slugify(string);

		// then
		assertEquals("Hello-world", result);
	}

	@Test
	public void shouldReplacePlusSignToSeparator() {
		// given
		String string = "\tHello+\tworld \r\t";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("hello-world", result);
	}

	@Test
	public void shouldReturnEmptyStringIfNullGiven() {
		// given
		String string = null;

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("", result);
	}

	@Test
	public void shouldSlugifyStringWithUnderscoreSeparator() {
		// given
		String string = "\tHello \tworld \r\t";

		// when
		String result = new Slugify().withUnderscoreSeparator(true).slugify(string);

		// then
		assertEquals("hello_world", result);
	}
}