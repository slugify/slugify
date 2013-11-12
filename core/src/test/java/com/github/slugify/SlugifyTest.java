package com.github.slugify;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

public class SlugifyTest {
	private static Slugify slg1;
	private static Slugify slg2;

	@BeforeClass
	@SuppressWarnings("serial")
	public static void setupSlugify() {
		slg1 = new Slugify();
		slg2 = new Slugify();

		slg2.setCustomReplacements(new HashMap<String, String>() {{
			put("ß", "ss");
		}});
	}

	@Test
	public void testBasic() {
		String s = "Hello world";
		assertEquals("Hello-world", slg1.slugify(s));
	}

	@Test
	public void testSpaces() {
		String s = "\tHello  \t world ";
		assertEquals("Hello-world", slg1.slugify(s));
	}

	@Test
	public void testPrintableASCII() {
		String s = " !\"#$%&'()*+,-./0123456789:;<=>?@"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`"
				+ "abcdefghijklmnopqrstuvwxyz{|}~";

		String expected = "0123456789"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "abcdefghijklmnopqrstuvwxyz";

		assertEquals(expected, slg1.slugify(s));
	}

	@Test
	public void testExtendedASCII() {
		String s = "€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶"
				+ "·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæç"
				+ "èéêëìíîïðñòóôõö÷øùúûüýþÿ";

		String expected = "SZszYAAAAAACEEEEIIIINOOOOOUUUUYaaaaaaceeeeiiiinooooouuuuyy";

		assertEquals(expected, slg1.slugify(s));
	}

	@Test
	public void testCustomReplacements() {
		String s = "Daß";
		assertEquals("Dass", slg2.slugify(s));
	}
}