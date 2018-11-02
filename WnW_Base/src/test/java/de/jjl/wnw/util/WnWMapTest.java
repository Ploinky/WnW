/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.util;

import org.junit.*;

import de.jjl.wnw.base.util.WnWMap;

public class WnWMapTest
{
	private WnWMap map;
	
	@Before
	public void before()
	{
		if(map == null)
		{
			map = new WnWMap();
		}
	}
	
	@Test
	public void testFromString()
	{
		map.fromString("TEST=123");
		
		Assert.assertEquals("String was not parsed correctly", "123", map.get("TEST"));
		Assert.assertEquals("Map parsed too many pairs", 1, map.size());
	}
	
	@Test
	public void testToString()
	{
		map.put("TEST", "123");
		map.put("123", "TEST");
		
		Assert.assertTrue("Map was not converted to String correctly: <" + map.toString() + ">", 
			map.toString().equals("TEST=123|123=TEST") 
			|| map.toString().equals("123=TEST|TEST=123"));
	}
}
