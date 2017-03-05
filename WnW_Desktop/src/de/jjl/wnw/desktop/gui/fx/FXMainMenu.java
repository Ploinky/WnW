package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
import de.jjl.wnw.desktop.gui.fx.comp.FXButton;
import de.jjl.wnw.desktop.gui.fx.comp.FXLabel;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class FXMainMenu extends JFXFrame
{
	public FXMainMenu()
	{
		init();
	}

	private void init()
	{
		setPrefSize(800, 600);
		setVgap(10);

		FXLabel lblTitle = new FXLabel(Const.TITLE);
		add(lblTitle);

		nextRow();

		FXButton btnConnect = new FXButton("BtnConnect");
		add(btnConnect).vGrow(Priority.SOMETIMES);

		nextRow();

		FXButton btnHost = new FXButton("BtnHost");
		add(btnHost).vGrow(Priority.SOMETIMES);

		nextRow();

		FXButton btnExit = new FXButton("BtnQuit");
		add(btnExit).vGrow(Priority.SOMETIMES);
		btnExit.setOnAction(e -> Platform.exit());

		nextRow();

		add(new Pane());
	}
}
