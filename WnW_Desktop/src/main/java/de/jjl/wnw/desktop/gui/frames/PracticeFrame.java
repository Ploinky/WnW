/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class PracticeFrame extends Frame
{
	public PracticeFrame(Game game)
	{
		super(game);
	}

	@Override
	public Parent getAsNode()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);

		try
		{
			return loader
				.load(getClass().getResourceAsStream("/xml/PRACTICE.fxml"));
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new RuntimeException("Error loading practice-frame");
	}

}
