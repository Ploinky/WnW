package de.jjl.wnw.base.rune.parser;

import de.jjl.wnw.base.rune.WnWInputParser;
import de.jjl.wnw.base.input.WnWInput;
import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.dev.rune.*;
import de.jjl.wnw.base.input.WnWKeyboardInput;
import de.jjl.wnw.base.util.path.WnWPath;

public class WnWKeyboardInputParser implements WnWInputParser
{
	@Override
	public WnWRune parseInput(WnWInput input)
	{
		if(input instanceof WnWKeyboardInput)
		{
			System.out.println(((WnWKeyboardInput) input).getInput());
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