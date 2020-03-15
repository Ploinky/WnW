package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public class MsgReqPlayerName extends Msg
{
	public static final String TYPE = "ReqPlayerName";
	
	public void fromMap(WnWMap map)
	{
	}
	
	public WnWMap getMsgMap()
	{
		WnWMap map = new WnWMap();
		
		map.put(MsgConst.TYPE, TYPE);
		
		return map;
	}
}
