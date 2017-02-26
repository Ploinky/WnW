package de.jjl.wnw.dev.conn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WnWServer
{
	private final int port;
	
	private ServerThread thread;
	
	private WnWConnectionListener connectionHandler;
	
	public WnWServer(int port)
	{
		this.port = port;
	}
	
	public synchronized void start() throws IOException
	{
		if(isRunning())
		{
			return;
		}
		
		thread = new ServerThread();
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!isRunning())
		{
			return;
		}
		thread.stopThread();
		thread = null;
	}
	
	public boolean isRunning()
	{
		return thread != null;
	}
	
	public WnWConnectionListener getConnectionHandler()
	{
		return connectionHandler;
	}
	
	public void setConnectionHandler(WnWConnectionListener connectionHandler)
	{
		this.connectionHandler = connectionHandler;
	}
	
	private class ServerThread extends Thread
	{
		private ServerSocket server;
		
		public ServerThread() throws IOException
		{
			server = new ServerSocket(port);
			
			setDaemon(true);
		}
		
		public void stopThread()
		{
			interrupt();
		}
		
		@Override
		public void run()
		{
			while(!Thread.interrupted())
			{
				try
				{
					Socket socket = server.accept();
					WnWConnection conn = new WnWConnection(socket);
					connectionHandler.connectionEstablished(conn);
				}
				catch(IOException e)
				{
					WnWServer.this.stop();
				}
			}
		}
	}
}
