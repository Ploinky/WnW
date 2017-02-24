/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

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

		JFXFrame f = addFrame();

		Label lblIp = new Label("IP:");
		lblIp.setFont(Const.FONT_DEFAULT);
		lblIp.setPadding(new Insets(0, 10, 0, 0));
		f.add(lblIp).setAligment(HPos.RIGHT, VPos.CENTER);

		TextField txtIp = new TextField("");
		txtIp.setFont(Const.FONT_DEFAULT);
		txtIp.textProperty().addListener((p, o, n) ->
		{
			if (!n.matches("[0-9]{0,3}\\.?[0-9]{0,3}\\.?[0-9]{0,3}\\.?[0-9]{0,3}"))
			{
				txtIp.setText(o);
			}
		});
		f.add(txtIp);
		f.add(new Pane());

		nextRow();

		DefaultButton btnStart = new DefaultButton("Start");
		add(btnStart).setVGrow(Priority.SOMETIMES);

		nextRow();

		DefaultButton btnBack = new DefaultButton("Back");
		add(btnBack).setVGrow(Priority.SOMETIMES);
		btnBack.setOnAction(e -> fireEvent(l -> l.requestMain()));

		nextRow();

		add(new Pane());
	}

}
