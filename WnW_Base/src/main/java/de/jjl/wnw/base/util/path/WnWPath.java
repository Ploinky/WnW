package de.jjl.wnw.base.util.path;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class WnWPath implements Iterable<WnWPoint>
{
	public final WnWDisplaySystem system;
	private final Deque<WnWPoint> points;

	public WnWPath(WnWDisplaySystem system)
	{
		this.system = system;
		points = new LinkedList<>();
	}

	public void addPoint(float x, float y)
	{
		points.add(new WnWPoint(x, y));
	}

	@Override
	public Iterator<WnWPoint> iterator()
	{
		return points.iterator();
	}
}
