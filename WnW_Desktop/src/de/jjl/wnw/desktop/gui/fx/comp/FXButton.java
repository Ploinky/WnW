package de.jjl.wnw.desktop.gui.fx.comp;

import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.desktop.consts.DesktopConsts;
import javafx.scene.control.Button;

public class FXButton extends Button
{
	public FXButton(String text)
	{
		super(Translator.get().translate(text));
		setFont(DesktopConsts.FONT_DEFAULT);
		setPrefWidth(250);
	}
}
