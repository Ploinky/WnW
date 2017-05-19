package de.jjl.wnw.desktop.controls;


import java.util.ArrayList;
import java.util.List;

import de.jjl.wnw.base.util.InvalidationListener;
import de.jjl.wnw.base.util.Observable;
import de.jjl.wnw.base.util.path.*;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.scene.layout.Pane;

public class DrawPanel extends Pane implements Observable
{
	private WnWDesktopPath path;
	
	private WnWDisplaySystem disSys;
	
	private List<InvalidationListener> listeners;

	public DrawPanel()
	{
		listeners = new ArrayList<>();
		
		disSys = new WnWDisplaySystem(new WnWPoint(0, 0),
			new WnWPoint((int) getWidth(), (int)getHeight()),
			true, false);

		setOnMousePressed(e ->
		{
			if(path != null)
			{
				getChildren().remove(path.getFXPath());
			}
			
			path = new WnWDesktopPath(disSys);
			getChildren().add(path.getFXPath());

			path.addPoint(e);
		});
		
		setOnMouseDragged(e ->
		{
			if(e.getX() < 0 || e.getX() > getWidth() || e.getY() < 0 || e.getY() > getHeight())
			{
				return;
			}
			path.addPoint(e);
		});
		
		setOnMouseReleased(e -> listeners.forEach(l -> l.invalidated(this)));
	}

	@Override
	public void addListener(InvalidationListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener)
	{
		listeners.remove(listener);
	}
	
	public WnWDesktopPath getPath()
	{
		return path;
	}
}
