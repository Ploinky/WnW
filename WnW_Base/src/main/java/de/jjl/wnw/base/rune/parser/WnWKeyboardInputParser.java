package de.jjl.wnw.base.rune.parser;

import de.jjl.wnw.base.input.WnWInput;
import de.jjl.wnw.base.input.WnWKeyboardInput;
import de.jjl.wnw.base.rune.WnWInputParser;
import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.dev.rune.Runes;

public class WnWKeyboardInputParser implements WnWInputParser
{
	@Override
	public WnWRune parseInput(WnWInput input)
	{
		if(input instanceof WnWKeyboardInput)
		{
			return parsePath(((WnWKeyboardInput) input).getInput());
		}
		return null;
	}
	
	public WnWRune parsePath(String input)
	{
		return lookupRune(input);
	}
	
	public WnWRune lookupRune(String input)
	{
		return Runes.getRuneForString(input);
		
	}
}