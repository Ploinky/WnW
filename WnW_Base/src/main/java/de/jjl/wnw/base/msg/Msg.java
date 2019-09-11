package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public abstract class Msg
{
	public Msg()
	{
		
	}
	
	public Msg(WnWMap map)
	{
		fromMap(map);
	}
	
	public abstract void fromMap(WnWMap map);

	public abstract WnWMap getMsgMap();
}
