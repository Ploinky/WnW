package de.jjl.wnw.desktop.gui.fx.comp;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Translator;
import javafx.scene.control.Button;

public class FXButton extends Button
{
	public FXButton(String text)
	{
		super(Translator.get().translate(text));
		setFont(Const.FONT_DEFAULT);
		setPrefWidth(250);
	}
}
