package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.util.*;
import de.jjl.wnw.desktop.controls.DrawPanel;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.scene.layout.*;

public class FXPractice extends JFXFrame implements InvalidationListener
{
	private Pane chain;

	public FXPractice(FrameListener listener)
	{
		super(listener);
		
		init();
	}

	private void init()
	{
		chain = new Pane();
		add(chain).vGrow(Priority.SOMETIMES).hGrow(Priority.ALWAYS);
		
		
		nextRow();
		
		DrawPanel p = new DrawPanel();
		p.addListener(this);
		add(p).vGrow(Priority.ALWAYS).hGrow(Priority.ALWAYS);
	}

	@Override
	public void invalidated(Observable observable)
	{
		DrawPanel p = null;
		
		if(observable instanceof DrawPanel)
		{
			p = (DrawPanel) observable;
		}
		else
		{
			return;
		}
		
		chain.getChildren().add(p.getPath().scaledFXPath(100, 100));
		
	}

}
