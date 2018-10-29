package de.jjl.wnw.dev.rune;

import java.util.function.Supplier;

public class DesktopRuneFactory implements Supplier<DesktopRune>
{
	private final String name;

	private final long rune;

	public DesktopRuneFactory(String name, long rune)
	{
		this.name = name;
		this.rune = rune;
	}

	@Override
	public DesktopRune get()
	{
		return new DesktopRune(name, rune);
	}
}
