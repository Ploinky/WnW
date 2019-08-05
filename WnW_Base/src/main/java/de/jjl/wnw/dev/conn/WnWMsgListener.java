package de.jjl.wnw.dev.conn;

import java.util.List;

import de.jjl.wnw.dev.PlayerController;

public interface WnWMsgListener
{
	public void msgReceived(WnWConnection conn, WnWMsg msg);
}