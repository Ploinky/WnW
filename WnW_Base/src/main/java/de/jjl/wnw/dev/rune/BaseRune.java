package de.jjl.wnw.dev.rune;

import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.dev.game.GameObject;

public abstract class BaseRune extends GameObject implements WnWRune
{
	private long rune;
	
	private String name;
	
	public BaseRune(String name, long rune)
	{
		this.name = name;
		this.rune = rune;
	}
	
	@Override
	public long getLong()
	{
		return rune;
	}
	
	public String getName()
	{
		return name;
	}
}
