package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.base.consts.Const;
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
		setFont(Const.FONT_DEFAULT);
		setPrefWidth(250);
	}
}
