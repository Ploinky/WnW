package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public class MsgGameState
{
	private static final String PARAM_GAMETIME = "GameTime";
	
	private static final String TYPE = "GameState";
	
	private long gameTime;
	
	public void setGameTime(long gameTime)
	{
		this.gameTime = gameTime;
	}
	
	public long getGameTime()
	{
		return gameTime;
	}
	
	public WnWMap getMsgMap()
	{
		WnWMap map = new WnWMap();
		
		map.put(MsgConst.TYPE, TYPE);
		map.put(PARAM_GAMETIME, "" + gameTime);
		
		return map;
	}
}
