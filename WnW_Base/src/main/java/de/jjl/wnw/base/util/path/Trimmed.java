package de.jjl.wnw.base.util.path;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

public class Trimmed extends WnWPath 
{
	private WnWDisplaySystem system;
	
	private List<WnWPoint> points;
	
	@Override
	public int getPathMinX()
	{
		return 0;
	}
	
	@Override
	public int getPathMinY()
	{
		return 0;
	}

	@Override
	public int getPathMaxX()
	{
		return getSystem().getWidth();
	}

	@Override
	public int getPathMaxY()
	{
		return getSystem().getHeight();
	}

	public Trimmed(WnWPath path)
	{
		super(new WnWDisplaySystem(path.getPathWidth(), path.getPathHeight(), path.getSystem().getXAxis(),
				path.getSystem().getYAxis()));
		system = new WnWDisplaySystem(path.getPathWidth(), path.getPathHeight(), path.getSystem().getXAxis(),
				path.getSystem().getYAxis());
		points = new ArrayList<WnWPoint>();
		
		WnWPoint min = new WnWPoint(path.getPathMinX(), path.getPathMinY());
		
			
		points = path.toList().stream().map(e -> e.minus(min))
					.filter(new Predicate<WnWPoint>()
					{
						WnWPoint lastPoint = null;
						
						public boolean test(WnWPoint t)
						{
							WnWPoint l = lastPoint;
							lastPoint = t;
							return t != l;
						};
					}).collect(Collectors.toList());
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
				int distXRel = distX / this.system.getWidth();
				int distYRel = distY / this.system.getHeight();
				
				WnWPoint nextPoint = new WnWPoint(
									system.getZeroX() + (system.getWidth() * distXRel),
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