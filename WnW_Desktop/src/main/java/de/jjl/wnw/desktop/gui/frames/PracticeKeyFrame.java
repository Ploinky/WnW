package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.base.input.WnWInput;
import de.jjl.wnw.base.input.WnWKeyboardInput;
import de.jjl.wnw.base.rune.parser.WnWKeyboardInputParser;
import de.jjl.wnw.desktop.controls.KeyboardPanel;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class PracticeKeyFrame extends Frame
{
	public PracticeKeyFrame(Game game)
	{
		super(game);
	}

	@FXML
	private KeyboardPanel keyPanel;
	
	@FXML
	private AnchorPane drawBox;
	
	private WnWKeyboardInputParser parser;

	@FXML
	private void initialize()
	{
		parser = new WnWKeyboardInputParser();
		
		keyPanel.setOnStringEntered(e ->
		{
			getRune(new WnWKeyboardInput(e.getInput()));
		});
	}

	private void getRune(WnWInput input)
	{
		// TODO Remove if no longer needed
		System.out.println(parser.parseInput(input));
	}

	@Override
	public Parent getAsNode()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);

		try
		{
			return loader.load(getClass().getResourceAsStream("/xml/PRACTICEKEY.fxml"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
}
