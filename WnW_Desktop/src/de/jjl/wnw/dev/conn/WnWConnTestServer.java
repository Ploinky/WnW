package de.jjl.wnw.dev.conn;

import static de.jjl.wnw.dev.conn.WnwConnTestFrame.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WnWConnTestServer
{
	private WnWServer server;
	
	private BooleanProperty running;
	
	private ObservableList<WnWConnTestConnection> connections;
	
	private ObservableList<WnwConnTestMsg> msgs; 
	
	private WaitThread waitThread;
	
	public WnWConnTestServer(int port)
	{
		server = new WnWServer(port);
		running = new SimpleBooleanProperty(this, "running", server.isRunning());
		msgs = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		waitThread = new WaitThread();
		connections = FXCollections.observableArrayList();
		
		server.setConnectionHandler(this::newConnection);
		
		running.addListener((p, o, n) ->
			{
				if(n)
				{
					waitThread.start();
				}
				else
				{
					waitThread.interrupt();
				}
			});
	}
	
	private void newConnection(WnWConnection conn)
	{
		WnWConnTestConnection c = new WnWConnTestConnection(conn);
		Platform.runLater(() -> connections.add(c));
		conn.addMsgListener(this::msgReceived);
	}

	public void start() throws IOException
	{
		if(server.isRunning())
		{
			updateRunning();
			return;
		}
		
		server.start();
		updateRunning();
	}

	public void stop()
	{
		if(!server.isRunning())
		{
			updateRunning();
		}
		
		server.stop();
		updateRunning();
	}
	
	private void updateRunning()
	{
		Platform.runLater(() -> running.set(server.isRunning()));
	}
	
	private void msgReceived(WnWConnection conn, WnWMsg msg)
	{
		WnwConnTestMsg m = new WnwConnTestMsg(msg, conn, false);
		Platform.runLater(() -> msgs.add(m));
		
		if(msg.getType().equals(MSGTYPE_NAME))
		{
			connections.stream()
				.filter(c -> c.getConn() == conn)
				.findAny()
				.ifPresent(c ->c.setName(msg.getParams().get(MSGPARAM_NAME)));
		}
		else if(msg.getType().equals(MSGTYPE_WAIT))
		{
			waitThread.addWaitMsg(new WaitMsg(m));
		}
	}

	public ReadOnlyBooleanProperty runningProperty()
	{
		return running;
	}
	
	public boolean isRunning()
	{
		return running.get();
	}
	
	public ObservableList<WnWConnTestConnection> getConnections()
	{
		return connections;
	}
	
	public ObservableList<WnwConnTestMsg> getMsgs()
	{
		return msgs;
	}
	
	private class WaitMsg
	{
		private WnWConnection conn;
		private long timeout;
		private String id;
		
		public WaitMsg(WnwConnTestMsg msg)
		{
			conn = msg.getConn();
			id = msg.getMsg().getParams().get(MSGPARAM_ID);
			
			timeout = System.currentTimeMillis() + Long.parseLong(msg.getMsg().getParams().get(MSGPARAM_TIME));
		}

		public void sendResponse()
		{
			WnWMsg msg = new WnWMsg(
				MSGTYPE_WAITRESPONSE,
				Collections.singletonMap(
					MSGPARAM_ID,
					id));
			
			Platform.runLater(() -> msgs.add(new WnwConnTestMsg(msg, conn, true)));
			
			conn.sendMsg(msg);
		}
	}
	
	private static class WaitThread extends Thread
	{
		private PriorityBlockingQueue<WaitMsg> waitMsgs;
		
		public WaitThread()
		{
			waitMsgs = new PriorityBlockingQueue<>(
					10,
					Comparator.comparingLong(msg -> msg.timeout));
		}
		
		public void addWaitMsg(WaitMsg waitMsg)
		{
			waitMsgs.add(waitMsg);
		}

		@Override
		public void run()
		{
			while(!Thread.interrupted())
			{
				try
				{
					WaitMsg m = waitMsgs.take();
					
					if(System.currentTimeMillis() >= m.timeout)
					{
						m.sendResponse();
					}
					else
					{
						addWaitMsg(m);
					}
				}
				catch (InterruptedException e)
				{
				}
			}
		}
	}
}