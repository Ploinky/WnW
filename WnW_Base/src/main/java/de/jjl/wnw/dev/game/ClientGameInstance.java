package de.jjl.wnw.dev.game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.jjl.wnw.base.msg.ChatMessage;
import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.base.msg.MsgGameState;
import de.jjl.wnw.base.util.WnWMap;

public class ClientGameInstance
{
	private Collection<GameObject> objects;
	
	private static ClientGameInstance instance;
	
	private Player player1;
	
	private Player player2;
	
	private List<MsgChatMessage> chat;
	
	private boolean isRunning;
	
	private ClientGameInstance()
	{
		isRunning = true;
		
		chat = new ArrayList<>();
		objects = new ArrayList<>();
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

	public void addChatMessage(MsgChatMessage msg)
	{
		chat.add(msg);
	}
	
	public List<MsgChatMessage> getChatMessages()
	{
		return chat;
	}
}
