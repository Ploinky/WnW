package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public class MsgPlayerInput
{
	public static final String TYPE = "PlayerInput";
	
	private static final String PARAM_INPUT = "Input";
	
	private String input;
	
	public void fromMap(WnWMap map)
	{
		input = map.get(PARAM_INPUT);
	}
	
	public String getInput()
	{
		return input;
	}
	
	public WnWMap getMsgMap()
	{
		WnWMap map = new WnWMap();
		
		map.put(MsgConst.TYPE, TYPE);
		map.put(PARAM_INPUT, input);
		
		return map;
	}
	
	public void setInput(String input)
	{
		this.input = input;
	}
}
