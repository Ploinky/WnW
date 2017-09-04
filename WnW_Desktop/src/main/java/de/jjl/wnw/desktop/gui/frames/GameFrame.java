package de.jjl.wnw.desktop.gui.frames;

import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameFrame extends Frame
{
	public GameFrame(Game game)
	{
		super(game);
	}

	@FXML
	void btnTestOnAction(ActionEvent event)
	{
	}

}
