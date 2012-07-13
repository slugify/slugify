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

import java.text.Normalizer;

import org.apache.commons.lang.StringUtils;

public class Slugify
{
	public static String slugify( String input )
	{
		String ret = StringUtils.trim( input );
		if ( StringUtils.isBlank( input ) )
		{
			return "";
		}

		ret = normalize( ret );
		ret = removeDuplicateWhiteSpaces( ret );
		return ret.replace( " ", "-" ).toLowerCase();
	}

	private static String normalize( String input )
	{
		String ret = StringUtils.trim( input );
		if ( StringUtils.isBlank( ret ) )
		{
			return "";
		}

		ret = ret.replace( "ß", "ss" );
		return Normalizer.normalize( ret, Normalizer.Form.NFD )
				.replaceAll( "[^\\p{ASCII}]", "")
				.replaceAll( "[^a-zA-Z0-9 ]", "" );
	}

	private static String removeDuplicateWhiteSpaces( String input )
	{
		String ret = StringUtils.trim( input );
		if ( StringUtils.isBlank( ret ) )
		{
			return "";
		}

		return ret.replaceAll( "\\s+", " " );
	}
}