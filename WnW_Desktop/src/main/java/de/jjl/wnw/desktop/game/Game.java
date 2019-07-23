package de.jjl.wnw.desktop.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.base.msg.MsgConst;
import de.jjl.wnw.desktop.consts.Frames;
import de.jjl.wnw.desktop.gui.Frame;
import de.jjl.wnw.desktop.gui.frames.MainFrame;
import de.jjl.wnw.desktop.gui.frames.PracticeDummyFrame;
import de.jjl.wnw.desktop.gui.frames.PracticeFrame;
import de.jjl.wnw.desktop.gui.frames.SettingsFrame;
import de.jjl.wnw.dev.conn.WnWConnection;
import de.jjl.wnw.dev.conn.WnWMsg;
import de.jjl.wnw.dev.conn.WnWMsgListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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

	private Map<String, Supplier<Frame>> frames;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		frames = new HashMap<>();
		name = MsgConst.DEFAULT_NAME;
		stage = primaryStage;

		stage.setScene(new Scene(new Pane()));

		stage.getScene().getStylesheets().add("css/main.css");

		frames.put(Frames.MAIN, () -> new MainFrame(this));
		frames.put(Frames.SETTINGS, () -> new SettingsFrame(this));
		frames.put(Frames.PRACTICE, () -> new PracticeFrame(this));
		frames.put(Frames.PRACTICEDUMMY, () -> new PracticeDummyFrame(this));

		requestSceneChange(Frames.MAIN);

		stage.setTitle(Translator.get().translate(Const.TITLE));
		stage.show();
	}

	@Override
	public void requestSceneChange(String newFrame)
	{
		try
		{
			stage.getScene().setRoot(frames.get(newFrame).get().getAsNode());
		}
		catch (Exception e)
		{
			// TODO Handle
			throw new RuntimeException("Error loading new scene for String <" + newFrame + ">", e);
		}
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

	public void exit()
	{
		// TODO Shutdown gracefully
		Platform.exit();
	}
}
