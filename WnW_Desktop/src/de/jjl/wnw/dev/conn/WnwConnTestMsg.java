package de.jjl.wnw.dev.conn;

public class WnwConnTestMsg
{
	private WnWMsg msg;
	private WnWConnection conn;
	private boolean send;
	
	public WnwConnTestMsg(WnWMsg msg, WnWConnection conn, boolean send)
	{
		super();
		this.msg = msg;
		this.conn = conn;
		this.send = send;
	}
	
	public WnWMsg getMsg()
	{
		return msg;
	}
	
	public WnWConnection getConn()
	{
		return conn;
	}
	
	public boolean wasSend()
	{
		return send;
	}
	
	public boolean wasReceived()
	{
		return !wasSend();
	}
}
