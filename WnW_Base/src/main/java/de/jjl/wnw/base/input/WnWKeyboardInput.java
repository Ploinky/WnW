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
	
	public void addString(String s)
	{
		input += s;
	}

	public void reset()
	{
		input = "";
	}
}
