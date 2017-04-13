/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.base.log;

public interface Logger
{
	public enum LogLevel
	{
		
	}
	
	public void error(String msg);
	
	public void warn(String msg);
	
	public void debug(String msg);
	
	public void info(String msg);
	
	public void log(LogLevel lvl, String msg);
}
