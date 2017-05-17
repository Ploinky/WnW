package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.desktop.controls.DrawPanel;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.scene.layout.Priority;

public class FXPractice extends JFXFrame
{

	public FXPractice(FrameListener listener)
	{
		super(listener);
		
		init();
	}

	private void init()
	{
		add(new DrawPanel()).vGrow(Priority.ALWAYS).hGrow(Priority.ALWAYS);
	}

}
