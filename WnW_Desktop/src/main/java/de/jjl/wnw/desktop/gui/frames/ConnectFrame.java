package de.jjl.wnw.desktop.gui.frames;

import de.jjl.wnw.desktop.consts.Frames;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConnectFrame extends Frame
{
	public ConnectFrame(Game game)
	{
		super(game);
	}

	@Override
	public Parent getAsNode()
	{
		BorderPane root = new BorderPane();
		VBox vBox = new VBox(30);
		vBox.setAlignment(Pos.CENTER);
		
		root.setCenter(vBox);

		Label lblName = new Label("Name:");
		TextField txtName = new TextField();
		
		HBox boxName = new HBox(10);
		boxName.setAlignment(Pos.CENTER);
		boxName.getChildren().addAll(lblName, txtName);
		
		Label lblServer = new Label("Host:");
		TextField txtServer = new TextField();

		HBox boxServer = new HBox(10);
		boxServer.setAlignment(Pos.CENTER);
		boxServer.getChildren().addAll(lblServer, txtServer);

		Button btnOk = new Button("Ok");
		btnOk.setDefaultButton(true);
		btnOk.setOnAction(e ->
		{
			game.setPlayerName(txtName.getText());
			game.setHost(txtServer.getText());
			game.requestSceneChange(Frames.ONLINEMATCH);
		});

		vBox.getChildren().addAll(boxName, boxServer, btnOk);
		return root;
	}
}
