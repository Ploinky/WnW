package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class DefaultMainMenu extends JFXFrame
{

	public DefaultMainMenu()
	{
		init();
	}

	private void init()
	{
		setPrefSize(800, 600);
		setVgap(10);

		Label lblTitle = new Label(Const.TITLE);
		lblTitle.setFont(Const.FONT_TITLE);
		add(lblTitle);

		nextRow();

		DefaultButton btnConnect = new DefaultButton("BtnConnect");
		add(btnConnect).vGrow(Priority.SOMETIMES);
		btnConnect.setOnAction(e -> listeners.forEach(c -> c.requestConnect()));

		nextRow();

		DefaultButton btnHost = new DefaultButton("BtnHost");
		add(btnHost).vGrow(Priority.SOMETIMES);
		btnHost.setOnAction(e -> listeners.forEach(c -> c.requestHost()));

		nextRow();

		DefaultButton btnExit = new DefaultButton("BtnQuit");
		add(btnExit).vGrow(Priority.SOMETIMES);
		btnExit.setOnAction(e -> Platform.exit());

		nextRow();

		Pane buffer = new Pane();
		add(buffer);
	}
}
