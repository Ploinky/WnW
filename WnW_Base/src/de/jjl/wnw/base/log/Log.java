/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.base.log;

import de.jjl.wnw.base.log.Logger.LogLevel;

public class Log
{	
	private static Logger defaultLogger;
	
	public static void setDefaultLogger(Logger defaultLogger)
	{
		Log.defaultLogger = defaultLogger;
	}
	
	public static Logger getDefaultLogger()
	{
		return defaultLogger;
	}
	
	public static void error(String msg)
	{
		defaultLogger.error(msg);
	}
	
	public static void warn(String msg)
	{
		defaultLogger.warn(msg);
	}
	
	public static void debug(String msg)
	{
		defaultLogger.debug(msg);
	}
	
	public static void info(String msg)
	{
		defaultLogger.info(msg);
	}
	
	public static void log(LogLevel lvl, String msg)
	{
		defaultLogger.log(lvl, msg);
	}
}
