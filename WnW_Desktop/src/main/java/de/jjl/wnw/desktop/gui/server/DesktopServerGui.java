package de.jjl.wnw.desktop.gui.server;

import java.util.Timer;

import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.dev.game.ServerGameInstance;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DesktopServerGui extends Application
{	
	private ServerGameInstance server;
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		BorderPane root = new BorderPane();
		
		TextArea txtChat = new TextArea();
		root.setCenter(txtChat);
		
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		server = ServerGameInstance.getInstance();
		
		server.getChatMsgs().addListener(new ListChangeListener<MsgChatMessage>()
		{
			@Override
			public void onChanged(Change<? extends MsgChatMessage> c)
			{
				while(c.next())
				{
					for(MsgChatMessage msg : c.getAddedSubList())
					{
						txtChat.setText(txtChat.getText() + "\r\n" + msg.toChatString());
					}
				}
			}
		});
	}
}
