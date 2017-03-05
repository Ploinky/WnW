package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import de.jjl.wnw.desktop.gui.fx.comp.FXLabel;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;

public class FXLobby extends JFXFrame
{

	public FXLobby(FrameListener listener)
	{
		super(listener);
		init();
	}

	private void init()
	{
		FXLabel lblTitle = new FXLabel("TitleLobby");
		lblTitle.setFont(Const.FONT_TITLE);
		add(lblTitle).vGrow(Priority.SOMETIMES);

		nextRow();

		ListView<String> listPlayers = new ListView<>();
		add(listPlayers).vGrow(Priority.SOMETIMES);
	}
}
