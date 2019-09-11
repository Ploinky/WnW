package de.jjl.wnw.dev.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;

import de.jjl.wnw.dev.game.ServerGameInstance;
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
			serverSocket.setSoTimeout(0);
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
					
					if(ServerGameInstance.getInstance().getPlayer1Controller() == null)
					{
						Debug.log("Client at <" + s.getInetAddress() + "> is controller for player 1.");
						ServerGameInstance.getInstance().setControllerPlayer1(controller);
					}
					else if(ServerGameInstance.getInstance().getPlayer2Controller() == null)
					{
						Debug.log("Client at <" + s.getInetAddress() + "> is controller for player 2.");
						ServerGameInstance.getInstance().setControllerPlayer2(controller);
					}
				}
				catch (SocketTimeoutException e)
				{
					// Shit happens...
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
		
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				if(!isRunning)
				{
					cancel();
				}
				
				ServerGameInstance.getInstance().handleFrame(System.currentTimeMillis());
			}
		};
		
		Timer t = new Timer();
		t.schedule(task, 0, 1000 / 60);
	}
}
