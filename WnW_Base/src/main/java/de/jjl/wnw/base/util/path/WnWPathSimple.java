package de.jjl.wnw.base.util.path;

import java.util.*;

public class WnWPathSimple extends WnWPath
{
	private WnWDisplaySystem system = new WnWDisplaySystem(0, 0);
	
	public WnWPathSimple(WnWDisplaySystem system)
	{
		super(system);
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
		if(system == this.system)
		{
			return this;
		}
		
		WnWPathSimple path = new WnWPathSimple(system);
		WnWPoint lastPoint = null;
		
		for(WnWPoint point : this)
		{
			int distX = point.getX() - this.system.getZeroX();
			int distY = point.getY() - this.system.getZeroY();
			
			if(system.getWidth() != 0)
			{
				int distXRel = (int) ((double) distX / system.getWidth());
				distX = (int)(system.getWidth() * distXRel);
			}
			
			if(system.getHeight() != 0)
			{
				double distYRel = (double) distY / system.getHeight();
				distY = (int) (system.getHeight() * distYRel);
			}
			
			WnWPoint nextPoint = new WnWPoint(system.getZeroX() + distX, system.getZeroY() + distY);
			
			if(nextPoint != lastPoint)
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

	public boolean contains(WnWPoint nextPoint)
	{
		for(WnWPoint p : points)
		{
			if(p == nextPoint)
			{
				return true;
			}
		}
		
		return false;
	}
}