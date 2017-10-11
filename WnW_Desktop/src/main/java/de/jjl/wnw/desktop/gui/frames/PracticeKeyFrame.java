package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.base.input.*;
import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.rune.parser.WnWKeyboardInputParser;
import de.jjl.wnw.desktop.controls.KeyboardPanel;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import de.jjl.wnw.dev.rune.DesktopRune;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;

public class PracticeKeyFrame extends Frame
{
	public PracticeKeyFrame(Game game)
	{
		super(game);
	}

	@FXML
	private KeyboardPanel keyPanel;
	
	@FXML
	private Canvas canvas;
	
	private GraphicsContext graphics;
		
	private WnWKeyboardInputParser parser;

	@FXML
	private void initialize()
	{
		graphics = canvas.getGraphicsContext2D();
		parser = new WnWKeyboardInputParser();
		
		keyPanel.setOnStringEntered(e ->
		{
			getRune(new WnWKeyboardInput(e.getInput()));
		});
	}

	private void getRune(WnWInput input)
	{
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		WnWRune r = parser.parseInput(input);
		
		if(r == null || !(r instanceof DesktopRune))
		{
			return;
		}
		
		Image img = ((DesktopRune) r).getImage();
		
		if(img == null)
		{
			return;
		}
		
		graphics.drawImage(img, 0, 0, 50, 50);
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
