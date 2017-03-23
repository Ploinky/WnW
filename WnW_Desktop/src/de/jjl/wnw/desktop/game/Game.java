package de.jjl.wnw.desktop.game;

import java.io.IOException;
import java.util.*;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.desktop.gui.fx.*;
import de.jjl.wnw.dev.conn.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is class.
 *
 * @author johannes.litger
 */
public class Game extends Application implements FrameListener, WnWMsgListener
{
	private Stage stage;

	private WnWConnection conn;

	public static void main(String[] args)
	{
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		stage = primaryStage;
		stage.setTitle(Translator.get().translate(Const.TITLE));
		stage.setScene(new Scene(new FXMainMenu(this)));
		stage.show();
	}

	@Override
	public void requestSceneChange(String newFrame)
	{
		switch (newFrame)
		{
			case Const.MENU_SETTINGS:
				stage.getScene().setRoot(new FXOptionMenu(this));
				break;
			case Const.MENU_CONNECT:
				stage.getScene().setRoot(new FXConnectMenu(this));
				break;
			case Const.MENU_MAIN:
				stage.getScene().setRoot(new FXMainMenu(this));
				break;

		}
	}

	@Override
	public void requestConnect(String host, String name)
	{
		try
		{
			conn = new WnWConnection(host, Const.DEFAULT_PORT);
			conn.addMsgListener(this);
			
			Map<String, String> info = new HashMap<>();
			info.put("VALUE", name);
			conn.sendMsg(new WnWMsg("NAME", info));
			
			Thread.sleep(1000);

			Map<String, String> info2 = new HashMap<>();
			info2.put("VALUE", "TESTING123");
			conn.sendMsg(new WnWMsg("NAME", info2));
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void msgReceived(WnWConnection conn, WnWMsg msg)
	{
		// TODO Auto-generated method stub
		
	}
}
