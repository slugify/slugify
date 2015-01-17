package com.github.slugify;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.Test;

public class SlugifyTest {
	private static Slugify slg1;
	private static Slugify slg2;

	@BeforeClass
	@SuppressWarnings("serial")
	public static void setupSlugify() throws IOException {
		slg1 = new Slugify();
		slg2 = new Slugify(false);

		slg2.setCustomReplacements(new HashMap<String, String>() {{
			put("leet", "1337");
		}});
	}

	@Test
	public void testBasic() {
		String s = "Hello world";
		assertEquals("hello-world", slg1.slugify(s));
	}

	@Test
	public void testSpaces() {
		String s = "\tHello  \t world ";
		assertEquals("hello-world", slg1.slugify(s));
	}

	@Test
	public void testPrintableASCII() {
		String s = " !\"#$%&'()*+,-./0123456789:;<=>?@"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`"
				+ "abcdefghijklmnopqrstuvwxyz{|}~";

		String expected = "+-0123456789-"
				+ "abcdefghijklmnopqrstuvwxyz-_-"
				+ "abcdefghijklmnopqrstuvwxyz";

		assertEquals(expected, slg1.slugify(s));
	}

	@Test
	public void testExtendedASCII() {
		String s = "€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶"
				+ "·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæç"
				+ "èéêëìíîïðñòóôõö÷øùúûüýþÿ";

		String expected = "szszyaaaaaeaceeeeiiiinoooooeuuuueyssaaaaaeaceeeeiiiinoooooeuuuueyy";

		assertEquals(expected, slg1.slugify(s));
	}

	@Test
	public void testReplacements() {
		String s = "ÄÖÜäöüß";
		assertEquals("AeOeUeaeoeuess", slg2.slugify(s));
	}

	@Test
	public void testCustomReplacements() {
		String s = "Hello leet!";
		assertEquals("Hello-1337", slg2.slugify(s));
	}
}
