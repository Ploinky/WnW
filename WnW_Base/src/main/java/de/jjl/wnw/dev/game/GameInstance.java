package de.jjl.wnw.dev.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import de.jjl.wnw.base.msg.MsgGameState.SpellParams;
import de.jjl.wnw.base.util.WnWMap;
import de.jjl.wnw.dev.spell.Spell;
import de.jjl.wnw.dev.spell.SpellUtil;

public abstract class GameInstance
{
	protected GamePlayer player1;
	
	protected GamePlayer player2;
	
	protected Collection<GameObject> objects;
	
	public WnWMap toMap()
	{
		return new WnWMap();
	}
	
	public void spellFromMap(WnWMap map)
	{
		if(map.isEmpty())
		{
			return;
		}

		String playerName = map.get(SpellParams.PARAM_PLAYER);
		Player player = null;
		
		if(playerName != null && playerName.equals("Player1"))
		{
			player = player1;
		}
		else
		{
			player = player2;
		}
		
		List<Long> combo = new ArrayList<>();
		String comboString = map.get(SpellParams.PARAM_COMBO);
		String[] comboArray = comboString.split("\\�");
		
		for(String s : comboArray)
		{
			combo.add(Long.valueOf(s));
		}
		
		boolean shield = Boolean.parseBoolean(map.get(SpellParams.PARAM_SHIELD));
		
		Spell spell = SpellUtil.getSpell(player, combo, shield);
		
		spell.setCastTime(Long.valueOf(map.get(SpellParams.PARAM_CASTTIME)));
		spell.setPos(Integer.valueOf(map.get(SpellParams.PARAM_X)), Integer.valueOf(map.get(SpellParams.PARAM_Y)));
		spell.setId(Long.valueOf(map.get(SpellParams.PARAM_ID)));
		objects.add(spell);
	}
	
	public WnWMap spellToMap(Spell spell)
	{
		WnWMap spellMap = new WnWMap();
		
		if(spell.getCaster().equals(player1))
		{
			spellMap.put(SpellParams.PARAM_PLAYER, "Player1");
		}
		else
		{
			spellMap.put(SpellParams.PARAM_PLAYER, "Player2");
		}
		
		StringJoiner spellCombo = new StringJoiner("�");
		
		for(long s : spell.getSpellCombo())
		{
			spellCombo.add("" + s);
		}
		
		spellMap.put(SpellParams.PARAM_COMBO, spellCombo.toString());
		spellMap.put(SpellParams.PARAM_SHIELD, Boolean.toString(spell.isShield()));
		spellMap.put(SpellParams.PARAM_CASTTIME, "" + spell.getCastTime());
		spellMap.put(SpellParams.PARAM_X, "" + spell.getX());
		spellMap.put(SpellParams.PARAM_Y, "" + spell.getY());
		spellMap.put(SpellParams.PARAM_ID, "" + spell.getId());
		
		return spellMap;
	}
}
