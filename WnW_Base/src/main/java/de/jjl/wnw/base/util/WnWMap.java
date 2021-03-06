/*
 * Copyright � 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.base.util;

import java.util.HashMap;

public class WnWMap extends HashMap<String, String>
{
	private static final long serialVersionUID = -6109863520851877963L;

	@Override
	public String toString()
	{
		String s = "";

		for(Entry<String, String> set : this.entrySet())
		{
			s += "|" + set.getKey() + "=" + set.getValue();
		}
		
		if(!s.isEmpty())
		{
			s = s.substring(1, s.length());
		}
		
		return s;
	}
	
	public String toMapString()
	{
		String s = "";

		for(Entry<String, String> set : this.entrySet())
		{
			s += "~" + set.getKey() + "^" + set.getValue();
		}
		
		if(!s.isEmpty())
		{
			s = s.substring(1, s.length());
		}
		
		return s;
	}
	
	public void fromMapString(String s)
	{
		if(s.isEmpty())
		{
			return;
		}
		
		String[] pairs = s.split("~");
		
		for(String pair : pairs)
		{
			if(!pair.matches(".+\\^.+"))
			{
				put(pair, null);
				continue;
			}
			
			String[] keyVal = pair.split("\\^");
			
			put(keyVal[0], keyVal[1]);
		}
	}
	
	public void fromString(String s)
	{
		String[] pairs = s.split("\\|");
		
		for(String pair : pairs)
		{
			if(!pair.matches(".+=.+"))
			{
				put(pair, null);
				continue;
			}
			
			String[] keyVal = pair.split("=");
			
			put(keyVal[0], keyVal[1]);
		}
	}
	
	public WnWMap(String s)
	{
		if(!s.isEmpty())
		{
			fromString(s);
		}
	}

	public WnWMap()
	{
	}
}
