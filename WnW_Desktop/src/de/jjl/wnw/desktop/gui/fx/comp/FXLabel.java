package de.jjl.wnw.desktop.gui.fx.comp;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Translator;
import javafx.scene.control.Label;

public class FXLabel extends Label
{
	public FXLabel(String text)
	{
		super(Translator.get().translate(text));
		setFont(Const.FONT_TITLE);
	}
}
