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
	
	public Path getFXPath(int width, int height)
	{
		Path p = new Path();
		
		WnWPoint left = null, right = null, top = null, bottom = null;
		
		Iterator<WnWPoint> it = iterator();
		
		// Determine outermost points
		while(it.hasNext())
		{
			WnWPoint point = it.next();
			
			left	= left == null		|| point.x < left.x		? point : left;
			right	= right == null		|| point.x > right.x	? point : right;
			top		= top == null		|| point.y < top.y		? point : top;
			bottom	= bottom == null	|| point.y > bottom.y		? point : bottom;
		}
		
		// Scale path
		float pathWidth = right.x - left.x;
		float pathHeight = bottom.y - top.y;
		float relWidth = pathWidth / width;
		float relHeight = pathHeight / height;
		
		// Use the largest scale that will fit the defined rectangle
		float relSize = relWidth < relHeight ? relHeight : relWidth;
		
		
		// Move path left and scale
		it = iterator();
		while(it.hasNext())
		{
			WnWPoint point = it.next();
			
			if(p.getElements().isEmpty())
			{
				p.getElements().add(new MoveTo((point.x - left.x) / relSize, (point.y - top.y) / relSize));
			}

			p.getElements().add(new LineTo((point.x - left.x) / relSize, (point.y - top.y) / relSize));
		}

		return p;
	}

}
