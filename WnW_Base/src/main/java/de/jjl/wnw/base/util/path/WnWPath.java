package de.jjl.wnw.base.util.path;

import java.util.*;
import java.util.stream.*;

public abstract class WnWPath implements Iterable<WnWPoint> 
{
	private WnWDisplaySystem system;
	
	public List<WnWPoint> toList()
	{
		return StreamSupport.stream(spliterator(), false).collect(Collectors.toList());
	}
	
	public WnWPath(WnWDisplaySystem system)
	{
		this.setSystem(system);
	}
	
	public int getPathMinX()
	{
		return StreamSupport.stream(spliterator(), false).min((p1, p2) -> Integer.compare(p1.getX(), p2.getX()))
				.orElse(new WnWPoint(0, 0)).getX();
	}
	public int getPathMaxX()
	{
		return StreamSupport.stream(spliterator(), false).min((p1, p2) -> Integer.compare(p2.getX(), p1.getX()))
				.orElse(new WnWPoint(0, 0)).getX();
	}
	
	public int getPathMinY()
	{
		return StreamSupport.stream(spliterator(), false).min((p1, p2) -> Integer.compare(p1.getY(), p2.getY()))
				.orElse(new WnWPoint(0, 0)).getY();
	}
	public int getPathMaxY()
	{
		return StreamSupport.stream(spliterator(), false).min((p1, p2) -> Integer.compare(p2.getY(), p1.getY()))
				.orElse(new WnWPoint(0, 0)).getY();
	}

	public int getPathWidth()
	{
		return getPathMaxX() - getPathMinX();
	}
	
	public int getPathHeight()
	{
		return getPathMaxY() - getPathMinY();
	}
	
	public abstract WnWPath forSystem(WnWDisplaySystem system);
	
	public WnWPath trimmed()
	{
		return new Trimmed(this);
	}
	
	public WnWPath trimmedToSize(int width, int height)
	{
		return trimmed().forSystem(new WnWDisplaySystem(width, height, getSystem().getXAxis(), getSystem().getYAxis()));
	}

	public WnWDisplaySystem getSystem()
	{
		return system;
	}

	public void setSystem(WnWDisplaySystem system)
	{
		this.system = system;
	}
}
