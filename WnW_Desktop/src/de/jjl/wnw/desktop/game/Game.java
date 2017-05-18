package de.jjl.wnw.desktop.game;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.base.msg.MsgConst;
import de.jjl.wnw.desktop.gui.fx.*;
import de.jjl.wnw.dev.conn.*;
import javafx.application.Application;
import javafx.scene.*;
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

	private String name;
	
	private Map<String, Function<Game, Parent>> map;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		name = MsgConst.DEFAULT_NAME;
		stage = primaryStage;
		stage.setTitle(Translator.get().translate(Const.TITLE));
		stage.setScene(new Scene(new FXMainMenu(this)));
		stage.show();
		
		map = new HashMap<>();
		
		addFrame(Const.MENU_SETTINGS, FXOptionMenu::new);
		addFrame(Const.MENU_CONNECT, FXConnectMenu::new);
		addFrame(Const.MENU_MAIN, FXMainMenu::new);
		addFrame(Const.MENU_PRACTICE, FXPractice::new);
	}
	
	public void addFrame(String name, Function<Game, Parent> func)
	{
		map.put(name, func);
	}

	@Override
	public void requestSceneChange(String newFrame)
	{
		stage.getScene().setRoot(map.get(newFrame).apply(this));
	}

	@Override
	public void requestConnect(String host, String name)
	{
		this.name = name;

		try
		{
			conn = new WnWConnection(host, Const.DEFAULT_PORT);
			conn.addMsgListener(this);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void msgReceived(WnWConnection conn, WnWMsg msg)
	{
		switch (msg.getType())
		{
			case MsgConst.TYPE_REQ_NAME:
				Map<String, String> info = new HashMap<>();
				info.put(MsgConst.PARAM_NAME, name);
				conn.sendMsg(new WnWMsg(MsgConst.TYPE_NAME, info));
				break;
		}
	}
}
