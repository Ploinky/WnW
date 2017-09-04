package de.jjl.wnw.desktop.gui.frames;

import de.jjl.wnw.desktop.consts.DesktopConsts.Frames;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainFrame extends Frame
{
	public MainFrame(Game game)
	{
		super(game);
	}

	@FXML
	private Button btnExit;

	@FXML
	private Button btnSettings;

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

}
