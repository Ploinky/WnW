package de.jjl.wnw.base.util.path;

public class WnWPoint
{
	public final int x;
	public final int y;

	public WnWPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public WnWPoint(float x, float y)
	{
		this((int)x, (int)y);
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof  WnWPoint
				&& x == ((WnWPoint)o).x
				&& y == ((WnWPoint)o).y;
	}
	
	@Override
	public int hashCode()
	{
		final int PRIME = 101;
		return (x * PRIME + y) * PRIME;
	}
}
