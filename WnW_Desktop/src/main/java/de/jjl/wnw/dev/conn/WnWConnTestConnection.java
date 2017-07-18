package de.jjl.wnw.dev.conn;

import static de.jjl.wnw.dev.conn.WnwConnTestFrame.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WnWConnTestConnection
{
	private volatile int id = 0;
	
	private WnWConnection conn;
	
	private ObservableList<WnWMsg> msgs;
	
	private Map<String, CountDownLatch> waitMsgLatches;
	
	private StringProperty name;
	
	public WnWConnTestConnection(WnWConnection conn)
	{
		this.conn = conn;
		
		msgs = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		waitMsgLatches = new HashMap<>();
		name = new SimpleStringProperty(this, "name", conn.getName());
		
		conn.addMsgListener(this::msgReceived);
	}
	
	public void sendMsg(String msg)
	{
		WnWMsg m = new WnWMsg(
			MSGTYPE_MSG,
			Collections.singletonMap(
				MSGPARAM_MSG,
				msg));
		
		sendMsg(m);
	}
	
	public void sendWait(String msg, long time) throws TimeoutException
	{
		final String msgId = String.valueOf(++id);
		Map<String, String> params = new HashMap<>();
		params.put(MSGPARAM_MSG, msg);
		params.put(MSGPARAM_TIME, String.valueOf(time));
		params.put(MSGPARAM_ID, msgId);
		WnWMsg m = new WnWMsg(MSGTYPE_WAIT,
				params);
		
		CountDownLatch latch = new CountDownLatch(1);
		
		synchronized (waitMsgLatches)
		{
			waitMsgLatches.put(msgId, latch);
		}
		
		sendMsg(m);
		
		try
		{
			if(!latch.await(5, TimeUnit.SECONDS))
			{
				throw new TimeoutException();
			}
		}
		catch (InterruptedException e)
		{
			Thread.interrupted();
			System.err.println("Exception on wait for response");
			e.printStackTrace();
		}
		finally
		{
			synchronized (waitMsgLatches)
			{
				waitMsgLatches.remove(msgId);
			}
		}
	}

	public void sendMsg(WnWMsg msg)
	{
		Platform.runLater(() -> msgs.add(msg));
		conn.sendMsg(msg);
	}

	private void msgReceived(WnWConnection conn, WnWMsg msg)
	{
		Platform.runLater(() -> msgs.add(msg));
		
		if(msg.getType().equals(MSGTYPE_WAITRESPONSE))
		{
			String msgId = msg.getParams().get(MSGPARAM_ID);
			
			CountDownLatch latch;
			synchronized (waitMsgLatches)
			{
				latch = waitMsgLatches.get(msgId);
			}
			
			if(latch != null)
			{
				latch.countDown();
			}
		}
	}

	public ReadOnlyStringProperty nameProperty()
	{
		return name;
	}
	
	public String getName()
	{
		return name.get();
	}
	
	public void setName(String name)
	{
		conn.setName(name);
		
		Platform.runLater(() -> this.name.set(conn.getName()));
		
		sendMsg(
			new WnWMsg(
				MSGTYPE_NAME,
				Collections.singletonMap(
					MSGPARAM_NAME,
					name)));
	}
	
	public WnWConnection getConn()
	{
		return conn;
	}
	
	public ObservableList<WnWMsg> getMsgs()
	{
		return msgs;
	}
	
	@Override
	public String toString()
	{
		return "dhjklö";
	}
}
