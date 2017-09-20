package de.jjl.wnw.dev.rune;

import de.jjl.wnw.base.rune.WnWRune;

public interface RuneFactory
{
	public WnWRune cre(String name, long rune, int damage);
}
