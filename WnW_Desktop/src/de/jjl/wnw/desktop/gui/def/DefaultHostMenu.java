/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.geometry.*;
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
		
		add(new Pane()).vGrow(Priority.ALWAYS);
		
		nextRow();

		JFXFrame f = addFrame();
		f.setVgap(10);
		vGrow(Priority.NEVER);

		Label lblIp = new Label("IP:");
		lblIp.setFont(Const.FONT_DEFAULT);
		lblIp.setPadding(new Insets(0, 10, 0, 0));
		f.add(lblIp).setAligment(HPos.RIGHT, VPos.CENTER).vGrow(Priority.NEVER);

		TextField txtIp = new TextField("");
		txtIp.setFont(Const.FONT_DEFAULT);
		txtIp.textProperty().addListener((p, o, n) ->
		{
			if (!n.matches("[0-9]{0,3}\\.?[0-9]{0,3}\\.?[0-9]{0,3}\\.?[0-9]{0,3}"))
			{
				txtIp.setText(o);
			}
		});
		f.add(txtIp).vGrow(Priority.NEVER);
		f.add(new Pane());
		
		f.nextRow();

		Label lblName = new Label("Name:");
		lblName.setFont(Const.FONT_DEFAULT);
		lblName.setPadding(new Insets(0, 10, 0, 0));
		f.add(lblName).setAligment(HPos.RIGHT, VPos.CENTER).vGrow(Priority.NEVER);

		TextField txtName = new TextField("");
		txtName.setFont(Const.FONT_DEFAULT);
		f.add(txtName).vGrow(Priority.NEVER);
		
		f.add(new Pane()).vGrow(Priority.NEVER);
		
		nextRow();
		
		add(new Pane()).vGrow(Priority.ALWAYS);
		
		nextRow();

		DefaultButton btnStart = new DefaultButton("Start");
		add(btnStart).vGrow(Priority.SOMETIMES);

		nextRow();

		DefaultButton btnBack = new DefaultButton("Back");
		add(btnBack).vGrow(Priority.SOMETIMES);
		btnBack.setOnAction(e -> fireEvent(l -> l.requestMain()));

		nextRow();

		add(new Pane());
	}

}
