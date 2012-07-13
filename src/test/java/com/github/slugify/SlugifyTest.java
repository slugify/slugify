package com.github.slugify;

/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class SlugifyTest
{
	@Test
	public void testBasic() throws UnsupportedEncodingException
	{
		String s = "Hello world";
		assertEquals( "hello-world", Slugify.slugify( s ) );
	}

	@Test
	public void testSpaces() throws UnsupportedEncodingException
	{
		String s = "\tHello  \t world ";
		assertEquals( "hello-world", Slugify.slugify( s ) );
	}

	@Test
	public void testPrintableASCII() throws UnsupportedEncodingException
	{
		String s = " !\"#$%&'()*+,-./0123456789:;<=>?@"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`"
				+ "abcdefghijklmnopqrstuvwxyz{|}~";

		String expected = "0123456789"
				+ "abcdefghijklmnopqrstuvwxyz"
				+ "abcdefghijklmnopqrstuvwxyz";

		assertEquals( expected, Slugify.slugify( s ) );
	}

	@Test
	public void testExtendedASCII() throws UnsupportedEncodingException
	{
		String s = "€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶"
				+ "·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæç"
				+ "èéêëìíîïðñòóôõö÷øùúûüýþÿ";

		String expected = "szszyaaaaaaceeeeiiiinooooouuuuyssaaaaaace"
				+ "eeeiiiinooooouuuuyy";

		assertEquals( expected, Slugify.slugify( s ) );
	}
}