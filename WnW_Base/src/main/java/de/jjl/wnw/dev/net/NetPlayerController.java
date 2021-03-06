package de.jjl.wnw.dev.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import de.jjl.wnw.base.msg.Msg;
import de.jjl.wnw.base.msg.MsgChatMessage;
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
			String s = reader.readLine();
			
			if(s != null)
			{
				System.out.println("Received <" + s + ">");
				return s;
			}
		}
		catch(SocketTimeoutException e)
		{
			// TODO $Li 26.02.2019 This is expected to happen, unfortunately
			return null;
		}
		catch (IOException e)
		{
			// TODO $Li 26.02.2019 Close connection to controller
			System.out.println("Lost connection to player");
			connected = false;
			return null;
		}
		
		return null;
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
			writer.write(state + "\n");
			writer.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.err.println("Lost connection to <" + socket.getInetAddress() + ">");
			connected = false;
			throw new RuntimeException("Player connection lost");
		}
	}

	private void init()
	{
		try
		{
			connected = true;
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			connected = false;
		}
	}

	@Override
	public void sendMsg(Msg msg)
	{
		try
		{
			writer.write(msg.getMsgMap().toString() + "\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
