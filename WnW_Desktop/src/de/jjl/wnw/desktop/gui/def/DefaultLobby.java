/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.JFXFrame;
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
			
			TextArea areaChat = new TextArea();
			areaChat.setEditable(false);
			chatContainer.add(areaChat);
			
			chatContainer.nextRow();
			
			JFXFrame inputContainer = chatContainer.addFrame();
			chatContainer.vGrow(Priority.NEVER);
			
			TextField txtChat = new TextField();
			txtChat.setFont(Const.FONT_SMALL);
			txtChat.onActionProperty().set(e ->
			{
				areaChat.appendText(txtChat.getText() + "\n");
				txtChat.clear();
			});
			inputContainer.add(txtChat).vGrow(Priority.NEVER);
			
			Button btnSend = new Button("Send");
			btnSend.setFont(Const.FONT_SMALL);
			btnSend.setOnAction(e -> 
			{
				areaChat.appendText(txtChat.getText() + "\n");
				txtChat.clear();
			});
			inputContainer.add(btnSend).vGrow(Priority.NEVER).hGrow(Priority.NEVER);
			
			ListView<String> listClients = new ListView<>();
			lobbyContainer.add(listClients);
		}
}
