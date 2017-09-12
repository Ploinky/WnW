package de.jjl.wnw.dev.conn.rune;

import java.util.ArrayList;
import java.util.List;

public class Runes
{
	private static Runes instance;
	
	private List<BaseRune> runes;
	
	private Runes()
	{
		runes = new ArrayList<>();
		
		runes.add(new TestRune());
	}
	
	public BaseRune getRuneFor(long rune)
	{
		if(instance == null)
		{
			instance = new Runes();
		}
		
		for(BaseRune r : runes)
		{
			if(r.getRuneLong() == rune)
			{
				return r;
			}
		}
	
		return null;
	}
}
