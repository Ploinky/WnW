package de.jjl.wnw.base.msg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.jjl.wnw.base.util.WnWMap;

public class MsgGameState
{
	public class SpellParams
	{
		public static final String PARAM_CASTTIME = "CastTime";

		public static final String PARAM_COMBO = "Combo";

		public static final String PARAM_ID = "Id";

		public static final String PARAM_PLAYER = "Player";

		public static final String PARAM_SHIELD = "Shield";

		public static final String PARAM_X = "X";

		public static final String PARAM_Y = "Y";
	}

	public static final String TYPE = "GameState";

	private static final String PARAM_GAMETIME = "GameTime";

	private static final String PARAM_P1_CHARACTER = "P1Character";

	private static final String PARAM_P1_LIVES = "P1Lives";

	private static final String PARAM_P2_CHARACTER = "P2Character";

	private static final String PARAM_P2_LIVES = "P2Lives";

	private static final String PARAM_SPELLS = "Spells";

	private long gameTime;

	private String p1Character;

	private Integer p1Lives;

	private String p2Character;

	private Integer p2Lives;

	private List<String> spells;

	public MsgGameState()
	{
		spells = new ArrayList<>();
	}

	public void addSpell(WnWMap spellMap)
	{
		spells.add(spellMap.toMapString());
	}

	public void fromMap(WnWMap map)
	{
		gameTime = Long.parseLong(map.get(PARAM_GAMETIME));
		p1Character = map.get(PARAM_P1_CHARACTER);
		p2Character = map.get(PARAM_P2_CHARACTER);
		p1Lives = Integer.parseInt(map.get(PARAM_P1_LIVES));
		p2Lives = Integer.parseInt(map.get(PARAM_P2_LIVES));

		List<String> list = Arrays.asList(map.get(PARAM_SPELLS).replace("[", "").replace("]", "").split(","));

		for (String s : list)
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
		map.put(PARAM_P1_LIVES, "" + p1Lives);
		map.put(PARAM_P2_LIVES, "" + p2Lives);

		return map;
	}

	public String getP1Character()
	{
		return p1Character;
	}

	public Integer getP1Lives()
	{
		return p1Lives;
	}

	public String getP2Character()
	{
		return p2Character;
	}

	public Integer getP2Lives()
	{
		return p2Lives;
	}

	public List<String> getSpells()
	{
		return spells;
	}

	public void setGameTime(long gameTime)
	{
		this.gameTime = gameTime;
	}

	public void setP1Character(String p1Character)
	{
		this.p1Character = p1Character;
	}

	public void setP1Lives(Integer p1Lives)
	{
		this.p1Lives = p1Lives;
	}

	public void setP2Character(String p2Character)
	{
		this.p2Character = p2Character;
	}

	public void setP2Lives(Integer p2Lives)
	{
		this.p2Lives = p2Lives;
	}

	public void setSpells(List<String> spells)
	{
		this.spells = spells;
	}
}
