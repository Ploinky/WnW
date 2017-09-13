package de.jjl.wnw.base.rune.parser

import de.jjl.wnw.base.rune.WnWInputParser
import de.jjl.wnw.base.input.WnWInput
import de.jjl.wnw.base.rune.WnWRune
import de.jjl.wnw.dev.conn.rune.*
import de.jjl.wnw.base.input.WnWKeyboardInput
import de.jjl.wnw.base.util.path.WnWPath

class WnWKeyboardInputParser : WnWInputParser
{
	override fun parseInput(input: WnWInput): WnWRune?
	{
		if(input is WnWKeyboardInput)
		{
			System.out.println(input.input);
			return parsePath(input.input)
		}
		return null
	}
	
	fun parsePath(input: String): WnWRune?
	{
		return lookupRune(input)
	}
	
	fun lookupRune(input: String): WnWRune?
	{
		return Runes.getRuneForString(input)
	}
}