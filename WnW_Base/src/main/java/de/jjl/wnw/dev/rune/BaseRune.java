package de.jjl.wnw.dev.rune;

import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.dev.game.GameObject;

public abstract class BaseRune extends GameObject implements WnWRune
{
	private long rune;
	
	private int damage;
	
	private String name;
	
	public BaseRune(String name, long rune, int damage)
	{
		this.rune = rune;
	}
	
	public int getDamage()
	{
		return damage;
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
