package de.jjl.wnw.base.rune.parser

import de.jjl.wnw.base.util.path.WnWPoint
import org.junit.Assert
import org.junit.Test

class GridTest
{
	@Test
	fun testGridParse_tolerance100()
	{
		var grid = Grid(100, 100, -50, -50, 1, 1, 100);
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, 50)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, -50)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(50, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-50, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(30, 40)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(30, -40)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-30, 40)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-30, -40)))
		Assert.assertNull("", grid.parse(WnWPoint(-50, -50)))
		Assert.assertNull("", grid.parse(WnWPoint(-50, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(50, -50)))
		Assert.assertNull("", grid.parse(WnWPoint(50, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(50, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(30, 41)))
	}
			
	@Test
	fun testGridParse_tolerance142()
	{
		var grid = Grid(100, 100, -50, -50, 1, 1, 142)
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(50, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-50, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, -50)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, 50)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(50, 50)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(50, -50)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-50, 50)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-50, -50)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-49, 10)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(38, 29)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(1, -25)))
	}
			
	fun testGridParse_tolerance50()
	{
		var grid = Grid(100, 100, -50, -50, 1, 1, 50)
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(25, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-25, 0)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, 25)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, -25)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(20, -15)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-10, 22)))
		Assert.assertNull("", grid.parse(WnWPoint(50, 0)))
		Assert.assertNull("", grid.parse(WnWPoint(0, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(-50, 0)))
		Assert.assertNull("", grid.parse(WnWPoint(0, -50)))
		Assert.assertNull("", grid.parse(WnWPoint(-50, -50)))
		Assert.assertNull("", grid.parse(WnWPoint(50, -50)))
		Assert.assertNull("", grid.parse(WnWPoint(-50, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(50, 50)))
	}
	
	fun testGridParse_tolerance0()
	{
		var grid = Grid(100, 100, -50, -50, 1, 1, 0)
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(0, 0)))
		Assert.assertEquals("", grid.parse(WnWPoint(0, 1)))
		Assert.assertEquals("", grid.parse(WnWPoint(0, 25)))
		Assert.assertEquals("", grid.parse(WnWPoint(0, 50)))
		Assert.assertEquals("", grid.parse(WnWPoint(50, 50)))
		Assert.assertEquals("", grid.parse(WnWPoint(0, -25)))
		Assert.assertEquals("", grid.parse(WnWPoint(12, 0)))
	}
	
	@Test
	fun testGridParse_3x3()
	{
		var grid = Grid(100, 100, -150, -150, 3, 3, 100)
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-100, -100)))
		Assert.assertEquals("", WnWPoint(0, 1), grid.parse(WnWPoint(-100, 0)))
		Assert.assertEquals("", WnWPoint(0, 2), grid.parse(WnWPoint(-100, 100)))
		Assert.assertEquals("", WnWPoint(1, 0), grid.parse(WnWPoint(0, -100)))
		Assert.assertEquals("", WnWPoint(1, 1), grid.parse(WnWPoint(0, 0)))
		Assert.assertEquals("", WnWPoint(1, 2), grid.parse(WnWPoint(0, 100)))
		Assert.assertEquals("", WnWPoint(2, 0), grid.parse(WnWPoint(100, -100)))
		Assert.assertEquals("", WnWPoint(2, 1), grid.parse(WnWPoint(100, 0)))
		Assert.assertEquals("", WnWPoint(2, 2), grid.parse(WnWPoint(100, 100)))
		
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-150, -100)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-100, -150)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-51, -100)))
		Assert.assertEquals("", WnWPoint(0, 0), grid.parse(WnWPoint(-100, -51)))
		Assert.assertNull("", grid.parse(WnWPoint(-150, -150)))
		Assert.assertNull("", grid.parse(WnWPoint(-51, -150)))
		Assert.assertNull("", grid.parse(WnWPoint(-150, 51)))
		Assert.assertNull("", grid.parse(WnWPoint(-51, -51)))
		
		Assert.assertEquals("", WnWPoint(0, 2), grid.parse(WnWPoint(-150, 100)))
		Assert.assertEquals("", WnWPoint(0, 2), grid.parse(WnWPoint(-100, 50)))
		Assert.assertEquals("", WnWPoint(0, 2), grid.parse(WnWPoint(-51, 100)))
		Assert.assertEquals("", WnWPoint(0, 2), grid.parse(WnWPoint(-100, 149)))
		Assert.assertNull("", grid.parse(WnWPoint(-150, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(-51, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(-150, 149)))
		Assert.assertNull("", grid.parse(WnWPoint(-51, 149)))
		
		Assert.assertEquals("", WnWPoint(2, 0), grid.parse(WnWPoint(50, -100)))
		Assert.assertEquals("", WnWPoint(2, 0), grid.parse(WnWPoint(100, -150)))
		Assert.assertEquals("", WnWPoint(2, 0), grid.parse(WnWPoint(149, -100)))
		Assert.assertEquals("", WnWPoint(2, 0), grid.parse(WnWPoint(100, -51)))
		Assert.assertNull("", grid.parse(WnWPoint(50, -150)))
		Assert.assertNull("", grid.parse(WnWPoint(149, -150)))
		Assert.assertNull("", grid.parse(WnWPoint(50, -51)))
		Assert.assertNull("", grid.parse(WnWPoint(149, -51)))
		
		Assert.assertEquals("", WnWPoint(2, 2), grid.parse(WnWPoint(50, 100)))
		Assert.assertEquals("", WnWPoint(2, 2), grid.parse(WnWPoint(100, 50)))
		Assert.assertEquals("", WnWPoint(2, 2), grid.parse(WnWPoint(149, 100)))
		Assert.assertEquals("", WnWPoint(2, 2), grid.parse(WnWPoint(100, 149)))
		Assert.assertNull("", grid.parse(WnWPoint(50, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(149, 50)))
		Assert.assertNull("", grid.parse(WnWPoint(50, 149)))
		Assert.assertNull("", grid.parse(WnWPoint(149, 149)))
	}
	
	@Test
	fun testGridParse_ellipse_tolerance100()
	{
		TODO()
	}
	
	@Test
	fun testGridParse_ellipse_tolerance142()
	{
		TODO()
	}
	
	@Test
	fun testGridParse_ellipse_tolerance50()
	{
		TODO()
	}
	
	@Test
	fun testGridParse_ellipse_tolerance0()
	{
		TODO()
	}
}