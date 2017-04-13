package de.jjl.wnw.base.util;

public interface Observable
{
	public void addListener(InvalidationListener listener);
	public void removeListener(InvalidationListener listener);
}
