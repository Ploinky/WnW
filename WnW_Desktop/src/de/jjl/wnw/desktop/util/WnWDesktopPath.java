/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.util;

import java.util.Iterator;

import de.jjl.wnw.base.util.path.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

/**
 * Represents a path drawn on the desktop app.
 *
 * @author johannes.litger
 */
public class WnWDesktopPath extends WnWPathSimple
{
	/** FX-Path that is shown on screen */
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
		
		path.getElements().add(path.getElements().isEmpty() ? new MoveTo(x, y) : new LineTo(x, y));
	}
	
	public Path getFXPath()
	{
		return path;
	}
	
	/**
	 * Scales the path to the specified dimensions and moves it to the top left of the node that it is
	 * placed in.
	 */
	public Path getFXPathScaled(int width, int height)
	{
		Path p = new Path();
		
		WnWPoint left = null, right = null, top = null, bottom = null;

		// Determine outermost points
		Iterator<WnWPoint> it = iterator();
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

			p.getElements().add(p.getElements().isEmpty() ?
						new MoveTo((point.x - left.x) / relSize, (point.y - top.y) / relSize)
						: new LineTo((point.x - left.x) / relSize, (point.y - top.y) / relSize));
		}

		return p;
	}

}
