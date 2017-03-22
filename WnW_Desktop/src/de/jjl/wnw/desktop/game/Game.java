package de.jjl.wnw.desktop.game;

import java.io.IOException;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.desktop.gui.fx.FXConnectMenu;
import de.jjl.wnw.desktop.gui.fx.FXMainMenu;
import de.jjl.wnw.desktop.gui.fx.FXOptionMenu;
import de.jjl.wnw.dev.conn.WnWConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is class.
 *
 * @author johannes.litger
 */
public class Game extends Application implements FrameListener
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
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
