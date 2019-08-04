package de.jjl.wnw.dev.game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.junit.experimental.theories.FromDataPoints;

import de.jjl.wnw.base.msg.ChatMessage;
import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.base.msg.MsgGameState;
import de.jjl.wnw.base.msg.MsgGameState.SpellParams;
import de.jjl.wnw.base.util.WnWMap;
import de.jjl.wnw.dev.spell.Spell;
import de.jjl.wnw.dev.spell.SpellUtil;

public class ClientGameInstance extends GameInstance
{
	private static ClientGameInstance instance;
	
	private List<MsgChatMessage> chat;
	
	private boolean isRunning;
	
	private ClientGameInstance()
	{
		isRunning = true;
		
		chat = new CopyOnWriteArrayList<>();
		objects = new CopyOnWriteArrayList<>();
	}
	
	public void updateState(String gameState)
	{
		WnWMap map = new WnWMap();
		map.fromString(gameState);
		
		MsgGameState msg = new MsgGameState();
		msg.fromMap(map);
		
		if(player1 == null)
		{
			String p1Character = msg.getP1Character();
			player1 = new GamePlayer(5, 400);
			objects.add(player1);
		}

		if(player2 == null)
		{
			String p2Character = msg.getP2Character();
			player2 = new GamePlayer(600, 400);
			player2.faceLeft();
			objects.add(player2);
		}
		
		List<WnWMap> spellMap = msg.getSpells().stream()
				.filter(s -> !s.isEmpty())
				.map(s ->
				{
					WnWMap tempMap = new WnWMap();
					tempMap.fromMapString(s);
					return tempMap;
				})
				.collect(Collectors.toList());
		
		objects.stream()
			.filter(Spell.class::isInstance)
			.map(Spell.class::cast)
			.filter(m -> spellMap.stream()
					.noneMatch(sMap -> m.getId() == Long.valueOf(sMap.get(SpellParams.PARAM_ID))))
			.forEach(s ->
			{
				objects.remove(s);
				int removeIndex = spellMap.indexOf(spellMap.stream()
						.filter(sMap -> s.getId() == Long.valueOf(sMap.get(SpellParams.PARAM_ID)))
						.findFirst()
						.orElse(null));
				
				if(removeIndex != -1)
				{
					spellMap.remove(removeIndex);
				}
			});

		List<WnWMap> updateSpells = spellMap.stream()
			.filter(m -> objects.stream()
				.filter(Spell.class::isInstance)
				.map(Spell.class::cast)
				.anyMatch(spell -> spell.getId() == Long.valueOf(m.get(SpellParams.PARAM_ID))))
			.collect(Collectors.toList());
		
		for(WnWMap updSpell : updateSpells)
		{
			long id = Long.valueOf(updSpell.get(SpellParams.PARAM_ID));
			
			Spell spellToUpdate = objects.stream()
				.filter(Spell.class::isInstance)
				.map(Spell.class::cast)
				.filter(spell -> spell.getId() == id)
				.findFirst()
				.orElse(null);
			
			if(spellToUpdate == null)
			{
				continue;
			}
			
			spellToUpdate.setX(Integer.valueOf(updSpell.get(SpellParams.PARAM_X)));
			spellToUpdate.setY(Integer.valueOf(updSpell.get(SpellParams.PARAM_Y)));
		}
		
		spellMap.stream()
			.filter(m -> objects.stream()
					.filter(Spell.class::isInstance)
					.map(Spell.class::cast)
					.noneMatch(spell -> spell.getId() == Long.valueOf(m.get(SpellParams.PARAM_ID))))
			.forEach(m -> spellFromMap(m));
	}
	
	public Collection<GameObject> getObjects()
	{
		return objects;
	}
	
	public static ClientGameInstance getInstance()
	{
		if(instance == null)
		{
			instance = new ClientGameInstance();
		}
		
		return instance;
	}
	
	public boolean isRunning()
	{
		return isRunning;
	}
	
	public void stop()
	{
		isRunning = false;
	}

	public void addChatMessage(MsgChatMessage msg)
	{
		chat.add(msg);
	}
	
	public List<MsgChatMessage> getChatMessages()
	{
		return chat;
	}
}
