package de.jjl.wnw.base.msg;

public class Msg
{
	private String type;
	
	protected Msg(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
}
