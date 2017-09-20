package de.jjl.wnw.dev.rune;

import de.jjl.wnw.base.rune.WnWRune;

public class BaseRune implements WnWRune
{
	private long rune;
	
	private int damage;
	
	public BaseRune(long rune, int damage)
	{
		this.rune = rune;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public long getRuneLong()
	{
		return rune;
	}
}
