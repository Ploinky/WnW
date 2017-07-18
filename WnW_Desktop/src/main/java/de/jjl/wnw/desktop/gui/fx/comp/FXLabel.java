package de.jjl.wnw.desktop.gui.fx.comp;

import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.desktop.consts.DesktopConsts;
import javafx.scene.control.Label;

public class FXLabel extends Label
{
	public FXLabel(String text)
	{
		super(Translator.get().translate(text));
		setFont(DesktopConsts.FONT_TITLE);
	}
}
