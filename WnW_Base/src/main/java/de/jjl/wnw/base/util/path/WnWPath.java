package de.jjl.wnw.base.util.path;

public interface WnWPath extends Iterable<WnWPoint>
{
	public WnWDisplaySystem getSystem();
	public WnWPath forSystem(WnWDisplaySystem system);
}
