package de.jjl.wnw.base.util.path;

import java.util.*;

public class WnWPathSimple extends WnWPath
{
	public WnWPathSimple(WnWDisplaySystem system)
	{
		super(system);
	}

	public WnWPathSimple()
	{
		this(new WnWDisplaySystem(0, 0));
	}

	private List<WnWPoint> points = new ArrayList<WnWPoint>();
	
	@Override
	public Iterator<WnWPoint> iterator()
	{
		return points.iterator();
	}
	
	@Override
	public WnWPath forSystem(WnWDisplaySystem system)
	{
		if(system == this.getSystem())
		{
			return this;
		}
		
		WnWPathSimple path = new WnWPathSimple(system);
		WnWPoint lastPoint = null;
		
		for(WnWPoint point : this)
		{
			int distX = point.getX() - this.getSystem().getZeroX();
			int distY = point.getY() - this.getSystem().getZeroY();
			
			if(this.getSystem().getWidth() != 0)
			{
				double distXRel = (double) distX / this.getSystem().getWidth();
				distX = (int) (system.getWidth() * distXRel);
			}
			
			if(this.getSystem().getHeight() != 0)
			{
				double distYRel = (double) distY / this.getSystem().getHeight();
				distY = (int) (system.getHeight() * distYRel);
			}
			
			WnWPoint nextPoint = new WnWPoint(
								system.getZeroX() + distX,
								system.getZeroY() + distY);
			
			if(!nextPoint.equals(lastPoint))
			{
				path.addPoint(nextPoint);
				
				lastPoint = nextPoint;
			}
		}
		
		return path;
	}
	
	public void addPoint(int x, int y)
	{
		addPoint(new WnWPoint(x, y));
	}

	public void addPoint(WnWPoint point)
	{
		points.add(point);
	}

	public boolean contains(WnWPoint p)
	{
		return points.contains(p);
	}
}