/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.util;

import java.util.Iterator;

import de.jjl.wnw.base.util.path.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

public class WnWDesktopPath extends WnWPath
{
	private Path path;
	
	public WnWDesktopPath(WnWDisplaySystem system)
	{
		super(system);
		path = new Path();
	}
	
	public void addPoint(MouseEvent e)
	{
		addPoint((float) e.getX(), (float) e.getY());
	}
	
	@Override
	public void addPoint(float x, float y)
	{
		super.addPoint(x, y);
		
		if(path.getElements().isEmpty())
		{
			path.getElements().add(new MoveTo(x, y));
			return;
		}
		
		path.getElements().add(new LineTo(x, y));
	}
	
	public Path getFXPath()
	{
		return path;
	}
	
	public void clear()
	{
		path.getElements().clear();

		Iterator<WnWPoint> it = iterator();
		
		while(it.hasNext())
		{
			it.next();
			it.remove();
		}
	}

}
