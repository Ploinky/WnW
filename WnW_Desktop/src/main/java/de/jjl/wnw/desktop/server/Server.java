package de.jjl.wnw.desktop.server;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.log.Log;
import de.jjl.wnw.base.msg.MsgConst;
import de.jjl.wnw.base.player.Player;
import de.jjl.wnw.desktop.gui.ServerGui;
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

		new Thread(() ->
		{
			for (int i = 0; i < 10 && np.getName().equals(Const.DEFAULT_NAME); i++)
			{
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					// TODO
					e.printStackTrace();
				}

				conn.sendMsg(new WnWMsg(MsgConst.TYPE_REQ_NAME));
			}
		}).start();
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{

		gui.setVisible(true);

		players = new CopyOnWriteArrayList<>();

		server = new WnWServer(Const.DEFAULT_PORT);

		try
		{
			server.start();
		}
		catch (IOException e)
		{
			// TODO
			e.printStackTrace();
		}

		server.setConnectionHandler(this);
	}

	@Override
	public void msgReceived(WnWConnection conn, WnWMsg msg)
	{
		switch (msg.getType())
		{
			case MsgConst.TYPE_NAME:
				changePlayerName(conn, msg.getParams().get(MsgConst.PARAM_NAME));
				break;
		}
	}

	private void changePlayerName(WnWConnection conn, String name)
	{
		players.stream().filter(p -> p.getConn() == conn).findAny().ifPresent(p ->
		{
			Log.info("Changing name of " + p.getName() + " to " + name);
			p.setName(name);
			players.remove(p);
			gui.removeClient(p);
			players.add(p);
			gui.addClient(p);
		});
	}
}
