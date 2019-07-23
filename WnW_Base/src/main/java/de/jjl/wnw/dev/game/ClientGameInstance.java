package de.jjl.wnw.dev.game;

import java.util.ArrayList;
import java.util.Collection;

import de.jjl.wnw.base.util.WnWMap;

public class ClientGameInstance
{
	private Collection<GameObject> objects;
	
	private static ClientGameInstance instance;
	
	private boolean isRunning;
	
	private ClientGameInstance()
	{
		isRunning = true;
		
		objects = new ArrayList<>();
	}
	
	public void updateState(String gameState)
	{
		WnWMap map = new WnWMap();
		map.fromString(gameState);
		
		System.out.println(map);
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
}
