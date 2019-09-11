package de.jjl.wnw.dev.conn;

public interface WnWMsgListener
{
	public void msgReceived(WnWConnection conn, WnWMsg msg);
}