package de.jjl.wnw.dev.rune;

import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.dev.game.GameObject;

public abstract class BaseRune implements GameObject, WnWRune
{
	private long rune;
	
	private String name;
	
	private RuneType type;
	
	protected BaseRune(String name, long rune, RuneType type)
	{
		this.name = name;
		this.rune = rune;
		this.type = type;
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
	
	public RuneType getType()
	{
		return type;
	}
}
