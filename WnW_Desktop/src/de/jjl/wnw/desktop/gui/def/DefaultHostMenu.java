/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
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

		DefaultLabel lblTitle = new DefaultLabel(Const.TITLE_HOST);
		lblTitle.setFont(Const.FONT_TITLE);
		add(lblTitle);

		rowBuffer();

		JFXFrame f = addFrame();
		f.setVgap(10);
		vGrow(Priority.NEVER);

		DefaultLabel lblName = new DefaultLabel("LblName");
		lblName.setFont(Const.FONT_DEFAULT);
		lblName.setPadding(new Insets(0, 10, 0, 0));
		f.add(lblName).setAligment(HPos.RIGHT, VPos.CENTER).vGrow(Priority.NEVER);

		TextField txtName = new TextField("");
		txtName.setFont(Const.FONT_DEFAULT);
		f.add(txtName).vGrow(Priority.NEVER).colBuffer();

		rowBuffer();

		DefaultButton btnStart = new DefaultButton("BtnStart");
		add(btnStart).vGrow(Priority.SOMETIMES);
		btnStart.setOnAction(e -> fireEvent(l -> l.requestLobby()));

		nextRow();

		DefaultButton btnBack = new DefaultButton("BtnBack");
		add(btnBack).vGrow(Priority.SOMETIMES);
		btnBack.setOnAction(e -> fireEvent(l -> l.requestMain()));

		rowBuffer();
	}

}
