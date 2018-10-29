package de.jjl.wnw.base.util.path;

public class WnWDisplaySystem
{
	private int width;
	private int height;
	private boolean xAxis;
	private boolean yAxis;
	private int zeroX;
	private int zeroY;
	
	public WnWDisplaySystem(int width, int height)
	{
		this(width, height, true, true);
	}
	
	
	public WnWDisplaySystem(int width, int height, boolean xAxis, boolean yAxis)
	{
		this(width, height, xAxis, yAxis, xAxis ? 0 : width,  yAxis ? 0 : height);
	}

	public WnWDisplaySystem(int width, int height, boolean xAxis, boolean yAxis, int zeroX, int zeroY)
	{
		this.width = width;
		this.height = height;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.zeroX = zeroX;
		this.zeroY = zeroY;
	}


	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getZeroX()
	{
		return zeroX;
	}
	
	public int getZeroY()
	{
		return zeroY;
	}
	
	public boolean getXAxis()
	{
		return xAxis;
	}
	
	public boolean getYAxis()
	{
		return yAxis;
	}
}

//data class WnWDisplaySystem(val size: WnWPoint, val zero: WnWPoint = WnWPoint.ZERO, val xAxis: Boolean = true, val yAxis: Boolean = true) {
//	constructor(
//			width: Int,
//			height: Int,
//			xAxis: Boolean = true,
//			yAxis: Boolean = true,
//			zeroX: Int = if(xAxis) 0 else width,
//			zeroY: Int = if(yAxis) 0 else height)
//	: this(WnWPoint(width, height), WnWPoint(zeroX, zeroY), xAxis, yAxis)
//}