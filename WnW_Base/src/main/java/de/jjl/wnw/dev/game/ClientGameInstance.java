package de.jjl.wnw.dev.game;

import java.util.ArrayList;
import java.util.Collection;

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
		System.out.println(gameState);
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
