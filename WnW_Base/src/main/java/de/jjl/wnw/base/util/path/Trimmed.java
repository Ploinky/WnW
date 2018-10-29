package de.jjl.wnw.base.util.path;

import java.util.*;
import java.util.stream.*;

public class Trimmed extends WnWPath 
{
	private List<WnWPoint> points = new ArrayList<>();
	
	private WnWPoint lastPoint;
	
	public Trimmed(WnWPath path)
	{
		super(new WnWDisplaySystem(path.getPathWidth(), path.getPathHeight(), path.getSystem().getXAxis(), path.getSystem().getYAxis()));
		
		lastPoint = null;
		
		points = StreamSupport.stream(spliterator(), false).filter(p -> lastPoint != p).collect(Collectors.toList());
	}

	@Override
	public int getPathMinX()
	{
		return 0;
	}
	
	@Override
	public int getPathMaxX()
	{
		return getSystem().getWidth();
	}
	
	@Override
	public int getPathMinY()
	{
		return 0;
	}
	
	@Override
	public int getPathMaxY()
	{
		return getSystem().getHeight();
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
			int distX = point.getX() - system.getZeroX();
			int distY = point.getY() - system.getZeroY();
			int distXRel = distX / system.getWidth();
			int distYRel = distY / system.getHeight();
			
			WnWPoint nextPoint = new WnWPoint(system.getZeroX() + (system.getWidth() * distXRel),
								system.getZeroY() + (system.getHeight() * distYRel));
			
			if(nextPoint != lastPoint)
			{
				path.addPoint(nextPoint);
				
				lastPoint = nextPoint;
			}
		}
		
		return path;
	}
	
	@Override 
	public WnWPath trimmed() 
	{
		return this;
	}
	
	@Override
	public Iterator<WnWPoint> iterator()
	{
		return points.iterator();
	}
}