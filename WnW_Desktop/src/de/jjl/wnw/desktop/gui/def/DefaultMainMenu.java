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
		lblTitle.setStyle("-fx-font-size: 25pt; -fx-font-weight: bold;");
		add(lblTitle);

		nextRow();

		DefaultButton btnHost = new DefaultButton("Host");
		add(btnHost).setVGrow(Priority.SOMETIMES);
		btnHost.setOnAction(e ->
		{
			Platform.exit();
		});

		nextRow();

		DefaultButton btnExit = new DefaultButton("Exit");
		add(btnExit).setVGrow(Priority.SOMETIMES);
		btnExit.setOnAction(e ->
		{
			Platform.exit();
		});

		nextRow();

		Pane buffer = new Pane();
		add(buffer);
	}
}
