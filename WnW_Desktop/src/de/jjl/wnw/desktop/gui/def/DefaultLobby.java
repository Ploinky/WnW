/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;

public class DefaultLobby extends JFXFrame
{
	public DefaultLobby()
	{
		init();
		}

		private void init()
		{
			setPrefSize(800, 600);
			setVgap(10);

			Label lblTitle = new Label(Const.TITLE_LOBBY);
			lblTitle.setFont(Const.FONT_TITLE);
			add(lblTitle);
			
			nextRow();
			
			JFXFrame lobbyContainer = addFrame();
			
			JFXFrame chatContainer = lobbyContainer.addFrame();
			
			Label lblChat = new Label();
			lblChat.setAlignment(Pos.BOTTOM_LEFT);
			lblChat.setFont(Const.FONT_SMALL);
			chatContainer.add(lblChat);
			
			lblChat.setText("Testing123");
			
			chatContainer.nextRow();
			
			TextField txtChat = new TextField();
			txtChat.setFont(Const.FONT_SMALL);
			chatContainer.add(txtChat).vGrow(Priority.NEVER);
			
			ListView<String> listClients = new ListView<>();
			lobbyContainer.add(listClients);
		}
}
