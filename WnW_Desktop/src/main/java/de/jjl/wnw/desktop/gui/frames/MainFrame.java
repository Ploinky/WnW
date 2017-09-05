package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.desktop.consts.DesktopConsts.Frames;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class MainFrame extends Frame
{
	private Parent root;
	
	public MainFrame(Game game)
	{
		super(game);
	}

	@FXML
	private Button btnExit;

	@FXML
	private Button btnSettings;
	
	@FXML
	private Button btnPractice;

	@FXML
	void btnSettingsOnAction(ActionEvent event)
	{
		game.requestSceneChange(Frames.SETTINGS);
	}

	@FXML
	void btnExitOnAction(ActionEvent event)
	{
		game.exit();
	}
	
	@FXML
	void btnPracticeOnAction(ActionEvent event)
	{
		game.requestSceneChange(Frames.PRACTICE);
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
			root = loader.load(getClass().getResourceAsStream("/xml/MAIN.fxml"));
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}

}
