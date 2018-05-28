package de.jjl.wnw.base.rune;

import de.jjl.wnw.base.input.WnWInput;
import de.jjl.wnw.base.input.WnWPathInput;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser; 

public class WnWRunes
{
	public static WnWRune fromInput(WnWInput input)
	{
		WnWInputParser parser = getParserForInput(input);
		return parser.parseInput(input);
	}

	private static WnWInputParser getParserForInput(WnWInput input)
	{
		if(input instanceof WnWPathInput)
		{
			return new WnWPathInputParser();
		}
		// TODO
		return null;
	}
}
