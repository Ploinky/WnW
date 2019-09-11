package de.jjl.wnw.dev.rune;

import de.jjl.wnw.base.rune.WnWRune;

public class BaseRuneFactory implements RuneFactory
{
	private final String name;

	private final long rune;

	public BaseRuneFactory(String name, long rune)
	{
		this.name = name;
		this.rune = rune;
	}

	@Override
	public WnWRune cre()
	{
		return new ValueRune(name, rune, 1);
	}
}
