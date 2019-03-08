package de.jjl.wnw.dev.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import de.jjl.wnw.dev.game.GameInstance;
import de.jjl.wnw.dev.log.Debug;

public class Server
{
	public static void main(String[] args)
	{
		new Server();
	}
	
	private ServerSocket serverSocket;
	
	private boolean isRunning;
	
	public Server()
	{
		try
		{
			int port = 50002;
			Debug.log("Creating server at port <" + port + ">");
			serverSocket = new ServerSocket(port);
			Debug.log("Server created at port <" + serverSocket.getLocalPort() + ">");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startConnectionThread();
		
		startServerThread();
	}
	
	private void startConnectionThread()
	{
		isRunning = true;
		
		new Thread(() ->
		{
			while(isRunning)
			{
				try
				{
					Debug.log("Waiting for connections...");
					
					Socket s = serverSocket.accept();

					Debug.log("Accepted connection from <" + s.getInetAddress() + ">");
					
					NetPlayerController controller = new NetPlayerController(s);
					
					if(GameInstance.getInstance().getPlayer1Controller() == null)
					{
						Debug.log("Client at <" + s.getInetAddress() + "> is controller for player 1.");
						GameInstance.getInstance().setControllerPlayer1(controller);
					}
					else if(GameInstance.getInstance().getPlayer2Controller() == null)
					{
						Debug.log("Client at <" + s.getInetAddress() + "> is controller for player 2.");
						GameInstance.getInstance().setControllerPlayer2(controller);
					}
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void startServerThread()
	{
		isRunning = true;
		
		new Thread(() ->
		{
			while(isRunning)
			{
				GameInstance.getInstance().handleFrame(System.currentTimeMillis());
			}
		}).start();
	}
}
