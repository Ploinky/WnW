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
	
	public Path scaledFXPath(int width, int height)
	{
		Path p = new Path();
		WnWPoint left = null;
		WnWPoint top = null;
		
		// Find leftmost point
		Iterator<WnWPoint> it = iterator();
		while(it.hasNext())
		{
			WnWPoint point = it.next();
			
			if(left == null)
			{
				left = point;
			}
			if(top == null)
			{
				top = point;
			}
			
			if(point.x < left.x)
			{
				left = point;
			}
			if(point.y < top.y)
			{
				top = point;
			}
		}
		
		// Move path left
		it = iterator();
		while(it.hasNext())
		{
			WnWPoint point = it.next();
			
			if(p.getElements().isEmpty())
			{
				p.getElements().add(new MoveTo(point.x - left.x, point.y - top.y));
			}

			p.getElements().add(new LineTo(point.x - left.x, point.y - top.y));
		}
		

		return p;
	}

}
