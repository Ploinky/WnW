package de.jjl.wnw.dev.net;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.base.player.Player;
import de.jjl.wnw.dev.PlayerController;

public class NetPlayerController implements PlayerController
{
	private boolean connected;
	
	private BufferedReader reader;
	
	private Socket socket;
	
	private BufferedWriter writer;
	
	public NetPlayerController(Socket socket)
	{	
		connected = true;
		this.socket = socket;
		try
		{
			socket.setSoTimeout(5);
		}
		catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			connected = false;
		}
		init();
	}
	
	@Override
	public String getInputString()
	{
		try
		{
			if(reader.ready())
			{
				return reader.readLine();
			}
			
			return null;
			
		}
		catch(SocketTimeoutException e)
		{
			// TODO $Li 26.02.2019 This is expected to happen, unfortunately
			return null;
		}
		catch (IOException e)
		{
			// TODO $Li 26.02.2019 Close connection to controller
			e.printStackTrace();
			connected = false;
			return null;
		}
	}
	
	public boolean isConnected()
	{
		return connected;
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
			connected = false;
		}
	}

	private void init()
	{
		try
		{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			connected = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			connected = false;
		}
	}

	@Override
	public void sendMsg(MsgChatMessage msg)
	{
		try
		{
			writer.write(msg.getMsgMap().toString() + "\n");
			writer.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
