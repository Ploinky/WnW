package de.jjl.wnw.base.rune;

import de.jjl.wnw.base.input.WnWInput;

public class WnWRunes
{
	public static WnWRune fromInput(WnWInput input)
	{
		WnWInputParser parser = getParserForInput(input);
		return parser.parseInput(input);
	}

	private static WnWInputParser getParserForInput(WnWInput input)
	{
		// TODO
		return null;
	}
}
