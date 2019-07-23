package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public class MsgGameState
{
	public static final String TYPE = "GameState";
	
	private static final String PARAM_GAMETIME = "GameTime";
	
	private static final String PARAM_P1_CHARACTER = "P1Character";
	
	private static final String PARAM_P2_CHARACTER = "P2Character";
	
	private long gameTime;
	
	private String p1Character;
	
	private String p2Character;
	
	public void fromMap(WnWMap map)
	{
		gameTime = Long.parseLong(map.get(PARAM_GAMETIME));
		p1Character = map.get(PARAM_P1_CHARACTER);
		p2Character = map.get(PARAM_P2_CHARACTER);
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
		map.put(PARAM_P2_CHARACTER, p1Character);
		map.put(PARAM_P1_CHARACTER, p2Character);
		
		return map;
	}
	
	public String getP1Character()
	{
		return p1Character;
	}

	public String getP2Character()
	{
		return p2Character;
	}

	public void setGameTime(long gameTime)
	{
		this.gameTime = gameTime;
	}

	public void setP1Character(String p1Character)
	{
		this.p1Character = p1Character;
	}

	public void setP2Character(String p2Character)
	{
		this.p2Character = p2Character;
	}
}
