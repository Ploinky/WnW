package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.base.input.WnWInput;
import de.jjl.wnw.base.input.WnWKeyboardInput;
import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.rune.parser.WnWKeyboardInputParser;
import de.jjl.wnw.desktop.controls.KeyboardPanel;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import de.jjl.wnw.dev.rune.DesktopRune;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PracticeKeyFrame extends Frame
{
	public PracticeKeyFrame(Game game)
	{
		super(game);
	}

	@FXML
	private KeyboardPanel keyPanel;
	
	@FXML
	private Pane pnlRes;
	
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
		pnlRes.getChildren().clear();
		
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
		
		ImageView iv = new ImageView();
		iv.setImage(img);
		pnlRes.getChildren().add(iv);
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
