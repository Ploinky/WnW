package de.jjl.wnw.base.util.path;

import java.awt.Point;

public class WnWDisplaySystem
{
	public static WnWDisplaySystem getFor(int width, int height, boolean xAxis, boolean yAxis)
	{
		return new WnWDisplaySystem(
				xAxis ? 0 : width,
				yAxis ? 0 : height,
				width, height,
				xAxis, yAxis);
	}

	public final WnWPoint zero;
	public final WnWPoint size;
	/** if true, positive x-Axis points towards right */
	public final boolean xAxis;
	/** if true, positive y-Axis points towards up */
	public final boolean yAxis;

	public WnWDisplaySystem(WnWPoint zero, WnWPoint size, boolean xAxis, boolean yAxis)
	{
		this.zero = zero;
		this.size = size;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	public WnWDisplaySystem(int zeroX, int zeroY, int width, int height, boolean xAxis, boolean yAxis)
	{
		this(new WnWPoint(zeroX, zeroY), new WnWPoint(width, height), xAxis, yAxis);
	}
}
