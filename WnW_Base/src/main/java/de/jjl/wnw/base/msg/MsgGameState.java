package de.jjl.wnw.base.msg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.jjl.wnw.base.util.WnWMap;
import de.jjl.wnw.dev.spell.Spell;

public class MsgGameState
{
	public static final String TYPE = "GameState";
	
	private static final String PARAM_GAMETIME = "GameTime";
	
	private static final String PARAM_P1_CHARACTER = "P1Character";
	
	private static final String PARAM_P2_CHARACTER = "P2Character";
	
	private static final String PARAM_SPELLS = "Spells";
	
	public class SpellParams
	{
		public static final String PARAM_PLAYER = "Player";
		
		public static final String PARAM_COMBO = "Combo";
		
		public static final String PARAM_SHIELD = "Shield";
		
		public static final String PARAM_CASTTIME = "CastTime";
		
		public static final String PARAM_X = "X";
		
		public static final String PARAM_Y = "Y";
		
		public static final String PARAM_ID = "Id";
	}
	
	private long gameTime;
	
	private String p1Character;
	
	private String p2Character;
	
	private List<String> spells;
	
	public MsgGameState()
	{
		spells = new ArrayList<>();
	}
	
	public void fromMap(WnWMap map)
	{
		gameTime = Long.parseLong(map.get(PARAM_GAMETIME));
		p1Character = map.get(PARAM_P1_CHARACTER);
		p2Character = map.get(PARAM_P2_CHARACTER);
		
		List<String> list = Arrays.asList(map.get(PARAM_SPELLS).replace("[", "").replace("]", "").split(","));
		
		for(String s : list)
		{
			addSpell(new WnWMap(s));	
		}
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
		map.put(PARAM_SPELLS, spells.toString());
		
		return map;
	}
	
	public List<String> getSpells()
	{
		return spells;
	}

	public void setSpells(List<String> spells)
	{
		this.spells = spells;
	}
	
	public void addSpell(WnWMap spellMap)
	{
		spells.add(spellMap.toMapString());
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
