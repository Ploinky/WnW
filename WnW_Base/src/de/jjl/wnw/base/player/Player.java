package de.jjl.wnw.base.player;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.dev.conn.WnWConnection;

public class Player
{
	private WnWConnection conn;

	private String name;

	public Player(WnWConnection conn)
	{
		name = Const.DEFAULT_NAME;
		this.setConn(conn);
	}

	public WnWConnection getConn()
	{
		return conn;
	}

	public void setConn(WnWConnection conn)
	{
		this.conn = conn;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
