package de.jjl.wnw.dev.rune;

import java.util.function.Supplier;

public class BaseRuneFactory implements Supplier<BaseRune>
{
	private final String name;

	private final long rune;

	public BaseRuneFactory(String name, long rune)
	{
		this.name = name;
		this.rune = rune;
	}

	@Override
	public BaseRune get()
	{
		return new DesktopRune(name, rune);
	}
}
