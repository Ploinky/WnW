package de.jjl.wnw.dev.rune;

public class ValueRune extends BaseRune
{
	private int value;
	
	public ValueRune(String name, long rune, int value)
	{
		super(name, rune, RuneType.VALUE);
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}

	@Override
	public int getX()
	{
		return 0;
	}

	@Override
	public int getY()
	{
		return 0;
	}

	@Override
	public void setX(int x)
	{
	}

	@Override
	public void setY(int y)
	{
	}

	@Override
	public int getWidth()
	{
		return 0;
	}

	@Override
	public int getHeight()
	{
		return 0;
	}

	@Override
	public void update(float frameTime)
	{
	}

}
