/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.dev.conn.rune;

import de.jjl.wnw.base.rune.WnWRune;

public class LoadRunes
{
	public LoadRunes()
	{
		WnWRune testRune = new TestRune();
		
		Runes.addRune(1, testRune);
	}
}
