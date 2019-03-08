package de.jjl.wnw.dev.net;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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
		catch(SocketTimeoutException e)
		{
			// TODO $Li 26.02.2019 This is expected to happen, unfortunately
			return null;
		}
		catch (IOException e)
		{
			// TODO $Li 26.02.2019 Close connection to controller
			return null;
		}
	}
	
	public NetPlayerController(Socket socket)
	{
		this.socket = socket;
		try
		{
			socket.setSoTimeout(5);
		}
		catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init();
	}

	private void init()
	{
		try
		{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void updateGameState(String state)
	{
		try
		{
			writer.write(state);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
