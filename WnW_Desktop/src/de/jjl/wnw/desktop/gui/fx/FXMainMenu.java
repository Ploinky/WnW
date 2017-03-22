package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import de.jjl.wnw.desktop.gui.fx.comp.FXButton;
import de.jjl.wnw.desktop.gui.fx.comp.FXLabel;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class FXMainMenu extends JFXFrame
{
	public FXMainMenu(FrameListener listener)
	{
		super(listener);
		init();
	}

	private void init()
	{
		setVgap(10);

		FXLabel lblTitle = new FXLabel(Const.TITLE);
		add(lblTitle);

		nextRow();

		FXButton btnConnect = new FXButton("BtnConnect");
		add(btnConnect).vGrow(Priority.SOMETIMES);
		btnConnect.setOnAction(e ->
		{
			listener.requestSceneChange(Const.MENU_CONNECT);
		});

		nextRow();

		FXButton btnHost = new FXButton("BtnHost");
		add(btnHost).vGrow(Priority.SOMETIMES);

		nextRow();

		FXButton btnSettings = new FXButton("BtnOptions");
		add(btnSettings).vGrow(Priority.SOMETIMES);
		btnSettings.setOnAction(e ->
		{
			listener.requestSceneChange(Const.MENU_SETTINGS);
		});

		nextRow();

		FXButton btnExit = new FXButton("BtnQuit");
		add(btnExit).vGrow(Priority.SOMETIMES);
		btnExit.setOnAction(e -> Platform.exit());

		nextRow();

		add(new Pane());
	}
}
