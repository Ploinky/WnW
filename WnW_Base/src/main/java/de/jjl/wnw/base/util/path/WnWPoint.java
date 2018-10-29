package de.jjl.wnw.base.util.path;


public class WnWPoint
{
	private int x;
	
	private int y;
	
	public static final WnWPoint ZERO = new WnWPoint(0, 0);

	public WnWPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public WnWPoint negatePoint()
	{
		return new WnWPoint(-x, -y);
	}
	
	public WnWPoint plus(WnWPoint b)
	{
		return new WnWPoint(x + b.x, y + b.y);
	}
	
	public WnWPoint minus(WnWPoint b)
	{
		return new WnWPoint(x - b.x, y - b.y);
	}
	
//	public WnWPoint div(WnWPoint b)
//	{
//		return new WnWPoint(x / b.x, y / b.y);
//	}
	
	public WnWPoint div(int factor)
	{
		return new WnWPoint(x / factor, y / factor);
	}
	
	public WnWPoint rem(int factor)
	{
		return new WnWPoint(x % factor, y % factor);
	}

	public int getY()
	{
		return y;
	}
	
	public int getX()
	{
		return x;
	}

	public WnWPoint times(int factor)
	{
		return new WnWPoint(x * factor, y * factor);
	}

	public int length()
	{
		return this.x + this.y;
	}
}
