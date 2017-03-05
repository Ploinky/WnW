package de.jjl.wnw.desktop.server;

import java.io.IOException;

import de.jjl.wnw.base.player.Player;
import de.jjl.wnw.desktop.game.GameState;
import de.jjl.wnw.dev.conn.WnWConnection;
import de.jjl.wnw.dev.conn.WnWConnectionListener;
import de.jjl.wnw.dev.conn.WnWServer;

public class Server implements WnWConnectionListener
{
	public Server()
	{
		WnWServer server = new WnWServer(2556);
		try
		{
			server.start();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		server.setConnectionHandler(this);
	}

	public static void main(String[] args)
	{
		new Server();
	}

	@Override
	public void connectionEstablished(WnWConnection conn)
	{
		GameState.getInstance().addConnectedPlayer(new Player(conn));
	}
}
