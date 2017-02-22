/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DefaultHostMenu extends JFXFrame
{
	public DefaultHostMenu()
	{
		init();
	}

	private void init()
	{
		setPrefSize(800, 600);
		setVgap(10);

		Label lblTitle = new Label(Const.TITLE_HOST);
		lblTitle.setFont(Const.FONT_TITLE);
		add(lblTitle);

		nextRow();		
		
		Label lblIp = new Label("IP:");
		lblIp.setFont(Const.FONT_DEFAULT);
		add(lblIp).setVGrow(Priority.SOMETIMES);

		TextField txtIp = new TextField("Enter ip");
		txtIp.setFont(Const.FONT_DEFAULT);
		add(txtIp).setVGrow(Priority.SOMETIMES);
		
		
		nextRow();		

		Button btnStart = new Button("Start");
		btnStart.setFont(Const.FONT_DEFAULT);
		add(btnStart).setVGrow(Priority.SOMETIMES);
		
		nextRow();		
		
		Button btnBack= new Button("Back");
		btnBack.setFont(Const.FONT_DEFAULT);
		add(btnBack).setVGrow(Priority.SOMETIMES);
		btnBack.setOnAction(e ->
		{
			fireEvent(l -> 
			{
				l.requestMain();
			});
		});
		
		nextRow();		

		add(new Pane()).setVGrow(Priority.ALWAYS);
	}

}
