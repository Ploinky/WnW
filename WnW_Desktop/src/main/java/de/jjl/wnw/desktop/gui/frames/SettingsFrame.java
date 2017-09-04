package de.jjl.wnw.desktop.gui.frames;

import de.jjl.wnw.desktop.consts.DesktopConsts.Frames;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SettingsFrame extends Frame
{
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

}
