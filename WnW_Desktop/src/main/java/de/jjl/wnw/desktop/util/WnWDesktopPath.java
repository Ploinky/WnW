/*
 * Copyright � 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.util;

import de.jjl.wnw.base.util.path.*;
import de.jjl.wnw.dev.game.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Represents a path drawn on the desktop app.
 *
 * @author johannes.litger
 */
public class WnWDesktopPath extends WnWPathSimple implements Drawable
{
	/** FX-Path that is shown on screen */
	private Path path;

	private boolean isDrawing;

	public boolean isDrawing()
	{
		return isDrawing;
	}

	public void setDrawing(boolean isDrawing)
	{
		this.isDrawing = isDrawing;
	}

	public WnWDesktopPath(WnWDisplaySystem system)
	{
		super(system);
		path = new Path();
		path.setMouseTransparent(true);
	}

	public void addPoint(MouseEvent e)
	{
		addPoint((int) e.getX(), (int) e.getY());
	}

	@Override
	public void addPoint(WnWPoint point)
	{
		super.addPoint(point);

		path.getElements().add(path.getElements().isEmpty() ? new MoveTo(point.getX(), point.getY())
				: new LineTo(point.getX(), point.getY()));
	}

	public Path getFXPath()
	{
		return path;
	}

	/**
	 * Scales the path to the specified dimensions and moves it to the top left of
	 * the node that it is placed in.
	 */
	public Path getFXPathScaled(int width, int height)
	{
		Path p = new Path();

		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (WnWPoint point : this)
		{
			minX = Math.min(point.getX(), minX);
			maxX = Math.max(point.getX(), maxX);
			minY = Math.min(point.getY(), minY);
			maxY = Math.max(point.getY(), maxY);
		}

		// Scale path
		float pathWidth = maxX - minX;
		float pathHeight = maxY - minY;
		float relWidth = pathWidth / width;
		float relHeight = pathHeight / height;

		// Use the largest scale that will fit the defined rectangle
		float relSize = relWidth < relHeight ? relHeight : relWidth;

		// Move path left and scale
		for (WnWPoint point : this)
		{
			p.getElements()
					.add(p.getElements().isEmpty()
							? new MoveTo((point.getX() - minX) / relSize, (point.getY() - minY) / relSize)
							: new LineTo((point.getX() - minX) / relSize, (point.getY() - minY) / relSize));
		}

		return p;
	}

	@Override
	public void drawOn(GraphicsContext graphics)
	{
		graphics.beginPath();

		for (WnWPoint point : this)
		{
			graphics.lineTo(point.getX(), point.getY());
		}

		graphics.stroke();
		graphics.setStroke(Color.BLACK);
		graphics.setLineWidth(1);

		if (!isDrawing)
		{
			graphics.setLineWidth(6);
			graphics.setStroke(Color.TOMATO);
			graphics.stroke();

			graphics.setLineWidth(4);
			graphics.setStroke(Color.RED);
			graphics.stroke();

			graphics.setLineWidth(2);
			graphics.setStroke(Color.DARKRED);
			graphics.stroke();
		}
	}
}
