package de.jjl.wnw.dev.game;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.base.msg.MsgGameState;
import de.jjl.wnw.base.msg.MsgGameState.SpellParams;
import de.jjl.wnw.base.util.WnWMap;
import de.jjl.wnw.dev.spell.Spell;

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
		try
		{
			msg.fromMap(map);
		}
		catch (ClassNotFoundException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(player1 == null)
		{
			player1 = msg.getP1Character();
			objects.add(player1);
		}
		
		if(msg.getP1Name() != null && !msg.getP1Name().equals(player1.getName()))
		{
			player1.setName(msg.getP1Name());
		}
		
		player1.setLives(msg.getP1Lives());

		if(player2 == null)
		{
			player2 = msg.getP2Character();
			player2.faceLeft();
			objects.add(player2);
		}
		
		if(msg.getP2Name() != null && !msg.getP2Name().equals(player2.getName()))
		{
			player2.setName(msg.getP2Name());
		}

		player2.setLives(msg.getP2Lives());
		
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
