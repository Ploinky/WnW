package de.jjl.wnw.base.util.path;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class WnWPathSimple implements WnWPath
{
	private final Deque<WnWPoint> points;
	private final WnWDisplaySystem system;

	public WnWPathSimple(WnWDisplaySystem system)
	{
		this.system = system;
		this.points = new LinkedList<>();
	}

	@Override
	public WnWDisplaySystem getSystem()
	{
		return system;
	}

	public Deque<WnWPoint> getPoints()
	{
		return points;
	}

	@Override
	public Iterator<WnWPoint> iterator()
	{
		return points.iterator();
	}

	@Override
	public WnWPath forSystem(WnWDisplaySystem system)
	{
		if(system.size.equals(getSystem().size))
		{
			if(system.zero.equals(getSystem().zero)
				&& system.xAxis == getSystem().xAxis
				&& system.yAxis == getSystem().yAxis)
			{
				return this;
			}

			final WnWPathSimple path = new WnWPathSimple(system);
			final boolean x = system.xAxis == getSystem().xAxis;
			final boolean y = system.yAxis == getSystem().yAxis;

			for(WnWPoint point : this)
			{
				addPoint(
					x ? point.x : (system.zero.x + getSystem().size.x - point.x + getSystem().zero.x),
					y ? point.y : (system.zero.y + getSystem().size.y - point.y + getSystem().zero.y));
			}

			return path;
		}

		return null;
	}

	public void addPoint(float x, float y)
	{
		points.add(new WnWPoint(x, y));
	}
}
