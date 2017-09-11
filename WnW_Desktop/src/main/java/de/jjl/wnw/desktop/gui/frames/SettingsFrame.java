package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.desktop.consts.Frames;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class SettingsFrame extends Frame
{
	private Parent root;
	
	public SettingsFrame(Game game)
	{
		super(game);
	}

	@FXML
	private Button btnBack;

	@FXML
	void btnBackOnAction(ActionEvent event)
	{
		game.requestSceneChange(Frames.MAIN);
	}

	@Override
	public Parent getAsNode()
	{
		if(root!= null)
		{
			return root;
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		
		try
		{
			root = loader.load(getClass().getResourceAsStream("/xml/SETTINGS.fxml"));
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}

}
