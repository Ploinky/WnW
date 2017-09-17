/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.dev.conn.rune;

import de.jjl.wnw.base.rune.WnWRune;

public class LoadRunes
{
	private LoadRunes()
	{
		
	}
	
	public static void load()
	{
		WnWRune testRune = new TestRune();
		
		Runes.addRuneLong(159, testRune);
		Runes.addRuneString("ABF", testRune);
	}
}
