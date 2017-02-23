package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
		add(lblTitle).setHGrow(Priority.ALWAYS).setVGrow(Priority.ALWAYS).setAligment(HPos.CENTER, VPos.CENTER);

		Button btnHost = new Button("Host");
		btnHost.setStyle("-fx-font-size: 18pt;");
		btnHost.setPrefWidth(250);
		add(btnHost).setHGrow(Priority.ALWAYS).setVGrow(Priority.SOMETIMES).setAligment(HPos.CENTER, VPos.CENTER);
		btnHost.setOnAction(e ->
		{
			Platform.exit();
		});

		Button btnExit = new Button("Exit");
		btnExit.setStyle("-fx-font-size: 18pt;");
		btnExit.setPrefWidth(250);
		add(btnExit).setHGrow(Priority.ALWAYS).setVGrow(Priority.SOMETIMES).setAligment(HPos.CENTER, VPos.CENTER);
		btnExit.setOnAction(e ->
		{
			Platform.exit();
		});

		Pane buffer = new Pane();
		add(buffer).setHGrow(Priority.ALWAYS).setVGrow(Priority.ALWAYS).setAligment(HPos.CENTER, VPos.CENTER);
	}
}
