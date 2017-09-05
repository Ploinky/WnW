package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;

public class GameFrame extends Frame
{
	private Parent root;
	
	public GameFrame(Game game)
	{
		super(game);
	}

	@FXML
	void btnTestOnAction(ActionEvent event)
	{
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
			root = loader.load(getClass().getResourceAsStream("/xml/GAME.fxml"));
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}

}
