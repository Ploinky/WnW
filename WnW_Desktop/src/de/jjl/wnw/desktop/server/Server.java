package de.jjl.wnw.desktop.server;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.player.Player;
import de.jjl.wnw.desktop.gui.ServerGui;
import de.jjl.wnw.desktop.gui.fx.FxServerGui;
import de.jjl.wnw.dev.conn.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Server extends Application implements WnWConnectionListener, WnWMsgListener
{
	private WnWServer server;
	
	private List<Player> players;
	
	private ServerGui gui;
	
	public static void main(String[] args)
	{
		launch();
	}

	@Override
	public void connectionEstablished(WnWConnection conn)
	{	
		conn.addMsgListener(this);
		Player np = new Player(conn);
		players.add(np);
		gui.addClient(np);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		gui = new FxServerGui(primaryStage);
		
		gui.setVisible(true);
		
		players = new CopyOnWriteArrayList<Player>();
		
		server = new WnWServer(Const.DEFAULT_PORT);
				
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

	@Override
	public void msgReceived(WnWConnection conn, WnWMsg msg)
	{
		switch(msg.getType())
		{
			case "NAME":
					changePlayerName(conn, msg.getParams().get("VALUE"));
				break;
		}
	}

	private void changePlayerName(WnWConnection conn, String name)
	{
		players.forEach(p ->
		{
			if(p.getConn() == conn)
			{
				p.setName(name);
				players.remove(p);
				players.add(p);
			}
		});
	}
}
