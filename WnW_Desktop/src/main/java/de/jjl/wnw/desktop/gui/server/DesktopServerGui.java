package de.jjl.wnw.desktop.gui.server;

import java.time.LocalDateTime;

import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.dev.game.GamePlayer;
import de.jjl.wnw.dev.game.ServerGameInstance;
import de.jjl.wnw.dev.game.ServerListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DesktopServerGui extends Application implements ServerListener
{
	private ServerGameInstance server;

	private ListView<GamePlayer> playerList;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		BorderPane root = new BorderPane();

		playerList = new ListView<>();
		root.setRight(playerList);

		TextArea txtChat = new TextArea();
		txtChat.setWrapText(true);
		txtChat.setEditable(false);
		root.setCenter(txtChat);

		TextField txtChatEnter = new TextField();
		Button btnSend = new Button("Send");

		btnSend.disableProperty().bind(txtChatEnter.textProperty().isEmpty());

		txtChatEnter.setOnAction(e -> btnSend.fire());
		btnSend.setOnAction(e ->
		{
			MsgChatMessage msg = new MsgChatMessage();

			msg.setPlayer("<SERVER>");
			msg.setTimeStamp(String.format("[%02d:%02d:%02d]", LocalDateTime.now().getHour(),
					LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond()));
			msg.setMsg(txtChatEnter.getText());

			txtChatEnter.clear();

			server.sendServerChatMessage(msg);
		});

		HBox boxChat = new HBox();
		boxChat.getChildren().addAll(txtChatEnter, btnSend);

		root.setBottom(boxChat);

		primaryStage.setOnCloseRequest(e ->
		// TODO Shut down gracefully?
		System.exit(0));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		server = ServerGameInstance.getInstance();

		server.getChatMsgs().addListener((ListChangeListener<MsgChatMessage>) c ->
		{
			while (c.next())
			{
				for (MsgChatMessage msg : c.getAddedSubList())
				{
					txtChat.setText(txtChat.getText() + "\r\n" + msg.toChatString());
				}
			}
		});

		server.addServerListener(this);
	}

	@Override
	public void playerConnected(GamePlayer player)
	{
		Platform.runLater(() -> playerList.getItems().add(player));
	}

	@Override
	public void refreshPlayers()
	{
		Platform.runLater(() -> playerList.refresh());
	}

	@Override
	public void playerDisconnected(GamePlayer player)
	{
		Platform.runLater(() -> playerList.getItems().remove(player));
	}
}
