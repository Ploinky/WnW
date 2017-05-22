package de.jjl.wnw.base.input;

public class WnWKeyboardInput implements WnWInput
{
	private String input;
	
	public WnWKeyboardInput(String input)
	{
		this.input = input;
	}

	public String getInput()
	{
		return input;
	}
}
