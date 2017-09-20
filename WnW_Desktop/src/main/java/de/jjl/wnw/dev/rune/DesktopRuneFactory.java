package de.jjl.wnw.dev.rune;

import de.jjl.wnw.base.rune.WnWRune;

public class DesktopRuneFactory implements RuneFactory
{

	@Override
	public WnWRune cre(String name, long rune, int damage)
	{
		return new DesktopRune(name, rune, damage);
	}

}
