package de.jjl.wnw.desktop.gui.def;

import javafx.scene.control.Button;

public class DefaultButton extends Button
{
	public DefaultButton()
	{
		this("");
	}

	public DefaultButton(String text)
	{
		super(text);
		setStyle("-fx-font-size: 18pt;");
		setPrefWidth(250);
	}
}
