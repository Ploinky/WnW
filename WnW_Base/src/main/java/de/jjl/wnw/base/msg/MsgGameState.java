package de.jjl.wnw.base.msg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import de.jjl.wnw.base.util.WnWMap;
import de.jjl.wnw.dev.game.GamePlayer;

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

	private static final String PARAM_P1_NAME = "P1Name";
	
	private static final String PARAM_P1_LIVES = "P1Lives";

	private static final String PARAM_P2_CHARACTER = "P2Character";
	
	private static final String PARAM_P2_NAME = "P2Name";

	private static final String PARAM_P2_LIVES = "P2Lives";

	private static final String PARAM_SPELLS = "Spells";

	private long gameTime;

	private GamePlayer p1Character;
	
	private String p1Name;

	private Integer p1Lives;

	private GamePlayer p2Character;
	
	private String p2Name;

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

	public void fromMap(WnWMap map) throws IOException, ClassNotFoundException
	{
		gameTime = Long.parseLong(map.get(PARAM_GAMETIME));
		
		ByteArrayInputStream bo = new ByteArrayInputStream(Base64.getDecoder().decode(map.get(PARAM_P1_CHARACTER).getBytes()));
		ObjectInputStream so = new ObjectInputStream(bo);
		p1Character = (GamePlayer) so.readObject();
		
		bo = new ByteArrayInputStream(Base64.getDecoder().decode(map.get(PARAM_P2_CHARACTER).getBytes()));
		so = new ObjectInputStream(bo);
		p2Character = (GamePlayer) so.readObject();
		
		p1Name = map.get(PARAM_P1_NAME);
		p2Name = map.get(PARAM_P2_NAME);
		
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

	public WnWMap getMsgMap() throws IOException
	{
		WnWMap map = new WnWMap();

		map.put(MsgConst.TYPE, TYPE);
		map.put(PARAM_GAMETIME, "" + gameTime);
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream so = new ObjectOutputStream(bo);
		so.writeObject(p1Character);
		so.flush();
		map.put(PARAM_P1_CHARACTER, new String(Base64.getEncoder().encode(bo.toByteArray())));

		bo = new ByteArrayOutputStream();
		so = new ObjectOutputStream(bo);
		so.writeObject(p2Character);
		so.flush();
		map.put(PARAM_P2_CHARACTER, new String(Base64.getEncoder().encode(bo.toByteArray())));
		
		map.put(PARAM_SPELLS, spells.toString());
		
		map.put(PARAM_P1_NAME, p1Name);
		map.put(PARAM_P2_NAME, p2Name);
		
		map.put(PARAM_P1_LIVES, "" + p1Lives);
		map.put(PARAM_P2_LIVES, "" + p2Lives);

		return map;
	}

	public GamePlayer getP1Character()
	{
		return p1Character;
	}
	
	public String getP1Name()
	{
		return p1Name;
	}

	public Integer getP1Lives()
	{
		return p1Lives;
	}

	public GamePlayer getP2Character()
	{
		return p2Character;
	}

	public String getP2Name()
	{
		return p2Name;
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

	public void setP1Character(GamePlayer p1Character)
	{
		this.p1Character = p1Character;
	}
	
	public void setP1Name(String name)
	{
		this.p1Name = name;
	}

	public void setP1Lives(Integer p1Lives)
	{
		this.p1Lives = p1Lives;
	}

	public void setP2Character(GamePlayer p2Character)
	{
		this.p2Character = p2Character;
	}
	
	public void setP2Name(String name)
	{
		this.p2Name = name;
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
