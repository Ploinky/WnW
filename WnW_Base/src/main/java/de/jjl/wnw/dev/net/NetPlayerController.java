package de.jjl.wnw.dev.net;

import java.io.*;
import java.net.Socket;

import de.jjl.wnw.base.player.Player;
import de.jjl.wnw.dev.PlayerController;

public class NetPlayerController implements PlayerController
{
	private Socket socket;
	
	private BufferedReader reader;
	
	private BufferedWriter writer;
	
	@Override
	public String getInputString()
	{
		try
		{
			return reader.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public NetPlayerController(Socket socket)
	{
		this.socket = socket;
		init();
	}

	private void init()
	{
		try
		{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void updateGameState(String state)
	{
		// TODO Auto-generated method stub
		
	}
}
