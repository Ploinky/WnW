package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public class MsgPlayerName extends Msg
{
	public static final String TYPE = "PlayerName";
	
	private static final String PARAM_NAME = "NAME";
	
	private String name;
	
	public void fromMap(WnWMap map)
	{
		name = map.get(PARAM_NAME);
	}
	
	public String getName()
	{
		return name;
	}
	
	public WnWMap getMsgMap()
	{
		WnWMap map = new WnWMap();
		
		map.put(MsgConst.TYPE, TYPE);
		map.put(PARAM_NAME, name);
		
		return map;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
