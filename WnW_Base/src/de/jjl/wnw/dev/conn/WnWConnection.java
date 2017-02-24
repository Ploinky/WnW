package de.jjl.wnw.dev.conn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class WnWConnection implements Closeable
{
	private Socket socket;
	private String name;
	
	private RcvThread rcvThread;
	private SendThread sendThread;
	
	private BlockingDeque<WnWMsg> msgs;
	private List<WnWMsgListener> msgListener;
	
	public WnWConnection(Socket socket) throws IOException
	{
		this.socket = socket;
		msgs = new LinkedBlockingDeque<>();
		msgListener = new CopyOnWriteArrayList<>();
		
		startThreads();
		
		setName(socket.getInetAddress().getHostName());
	}
	
	public WnWConnection(String host, int port) throws UnknownHostException, IOException
	{
		this(new Socket(host, port));
	}
	
	public void sendMsg(WnWMsg msg)
	{
		msgs.addFirst(Objects.requireNonNull(msg));
	}
	
	private void startThreads() throws IOException
	{
		rcvThread = new RcvThread(socket.getInputStream());
		sendThread = new SendThread(socket.getOutputStream());
		
		rcvThread.start();
		sendThread.start();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
		
		rcvThread.setName("Receive<" + name + ">");
		sendThread.setName("Send<" + name + ">");
	}
	
	public void addMsgListener(WnWMsgListener l)
	{
		msgListener.add(l);
	}
	
	public void removeMsgListener(WnWMsgListener l)
	{
		msgListener.remove(l);
	}
	
	@Override
	public void close() throws IOException
	{
		rcvThread.interrupt();
		sendThread.interrupt();
		socket.close();
	}
	
	private class RcvThread extends Thread
	{
		private BufferedReader reader;
		
		public RcvThread(InputStream in)
		{
			this(new InputStreamReader(in));
		}
		
		public RcvThread(Reader reader)
		{
			this.reader = new BufferedReader(reader);
		}
		
		@Override
		public void run()
		{
			while(!Thread.interrupted())
			{
				try
				{
					String line = reader.readLine();
					
					try
					{
						WnWMsg msg = WnWMsg.parse(line);
						
						for(WnWMsgListener l : msgListener)
						{
							l.msgReceived(WnWConnection.this, msg);
						}
					}
					catch(IllegalArgumentException e)
					{
						sendMsg(
							new WnWMsg(
								WnWMsg.MSGTYPE_MSGERROR,
								Collections.singletonMap(
									WnWMsg.MSGPARAM_MSG,
									line)));
					}
				}
				catch (IOException e)
				{
					// TODO
					try
					{
						close();
					} catch (IOException e1)
					{
					}
				}
			}
		}
	}
	
	private class SendThread extends Thread
	{
		private BufferedWriter writer;
		
		public SendThread(OutputStream out)
		{
			this(new OutputStreamWriter(out));
		}
		
		public SendThread(Writer writer)
		{
			this.writer = new BufferedWriter(writer);
		}
		
		@Override
		public void run()
		{
			while(!Thread.interrupted())
			{
				try
				{
					WnWMsg msg = msgs.takeFirst();
					writer.write(msg.buildMsgString());
					writer.newLine();
					writer.flush();
				}
				catch(IOException | InterruptedException e)
				{
					// TODO
					try
					{
						close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
