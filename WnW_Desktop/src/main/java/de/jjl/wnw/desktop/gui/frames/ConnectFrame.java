package de.jjl.wnw.desktop.gui.frames;

import de.jjl.wnw.desktop.consts.Frames;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
		VBox vBox = new VBox();
		root.setCenter(vBox);

		TextField txtName = new TextField();

		Button btnOk = new Button("Ok");
		btnOk.setOnAction(e ->
		{
			game.setPlayerName(txtName.getText());
			game.requestSceneChange(Frames.ONLINEMATCH);
		});

		vBox.getChildren().addAll(txtName, btnOk);
		return root;
	}
}
