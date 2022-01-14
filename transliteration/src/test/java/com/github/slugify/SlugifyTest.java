package com.github.slugify;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

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
	public void shouldSlugifyGreekCharacters() {
		// given
		String capital = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";
		String small = "αβγδεζηθικλμνξοπρστυφχψω";
		String tonosCapital = "ΆΈΉΊΌΎΏ";
		String smallTonos = "άέήίόύώ";
		String dialytika = "ΪΫϊϋΰΐ";

		// when
		String result1 = new Slugify().slugify(capital);
		String result2 = new Slugify().slugify(small);
		String result3 = new Slugify().slugify(tonosCapital);
		String result4 = new Slugify().slugify(smallTonos);
		String result5 = new Slugify().slugify(dialytika);

		// the
		assertEquals("abgdezhthiklmnksoprstyfxpsw", result1);
		assertEquals("abgdezhthiklmnksoprstyfxpsw", result2);
		assertEquals("aehioyw", result3);
		assertEquals("aehioyw", result4);
		assertEquals("iyiuui", result5);
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
	public void shouldTransliterateArabicProperly() {
		// given
		String string = "هذه هي اللغة العربية";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals("hthh-hy-llgh-laarby", result);
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

	@Test
	public void shouldSlugifyWithTransliterator() {
		// given
		String string = "健康管理";

		// when
		String result = new Slugify().withTransliterator(true).slugify(string);

		// then
		assertEquals("jian-kang-guan-li", result);
	}

	@Test
	public void shouldNormalizeRepeatedHyphensToSingleHyphenWithHyphenSeparator() {
		//given
		String string = "a---b___c";

		//when
		String result = new Slugify().slugify(string);

		//then
		assertEquals("a-b___c", result);
	}

	@Test
	public void shouldNormalizeRepeatedUnderscoresToSingleUnderscoreWithUnderscoreSeparator() {
		//given
		String string = "a---b___c";

		//when
		String result = new Slugify().withUnderscoreSeparator(true).slugify(string);

		//then
		assertEquals("a---b_c", result);
	}

	@Test
	public void shouldRemoveAllVietnameseAccents() {
		// given
		String string = "aáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệghiíìỉĩịklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvxyýỳỷỹỵ";

		// expect
		String expected = "aaaaaaaaaaaaaaaaaabcddeeeeeeeeeeeeghiiiiiiklmnoooooooooooooooooopqrstuuuuuuuuuuuuvxyyyyyy";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals(expected, result);
	}

	@Test
	public void shouldRemoveAllVietnameseUppercaseAccents() {
		// given
		String string = "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆGHIÍÌỈĨỊKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVXYÝỲỶỸỴ";

		// expect
		String expected = "aaaaaaaaaaaaaaaaaabcddeeeeeeeeeeeeghiiiiiiklmnoooooooooooooooooopqrstuuuuuuuuuuuuvxyyyyyy";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals(expected, result);
	}

	@Test
	public void shouldSlugifyVietnameseCharacters() {
		// given
		String string = "Con Đường xưa em đi, vàng lên mái tóc thề, ngõ hồn dâng tái tê.";

		// expect
		String expected = "con-duong-xua-em-di-vang-len-mai-toc-the-ngo-hon-dang-tai-te";

		// when
		String result = new Slugify().slugify(string);

		// then
		assertEquals(expected, result);
	}
}
