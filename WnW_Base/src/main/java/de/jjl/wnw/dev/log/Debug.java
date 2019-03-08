package de.jjl.wnw.dev.log;

import java.time.LocalDateTime;

public class Debug
{
	public static void log(String log)
	{
		System.out.println(LocalDateTime.now() + ": " + log);
	}
}
