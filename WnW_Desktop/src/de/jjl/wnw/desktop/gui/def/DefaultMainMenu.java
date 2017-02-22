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
		lblTitle.setFont(Const.FONT_TITLE);
		add(lblTitle);

		nextRow();		
		
		Button btnHost = new Button("Host");
		btnHost.setFont(Const.FONT_DEFAULT);
		add(btnHost).setVGrow(Priority.SOMETIMES);
		btnHost.setOnAction(e ->
		{
			fireEvent(l ->
			{
				l.requestHost();
			});
		});
		
		nextRow();		

		Button btnExit = new Button("Exit");
		btnExit.setFont(Const.FONT_DEFAULT);
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
