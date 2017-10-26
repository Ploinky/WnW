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
		
		Assert.assertNotNull("center should always be considered", grid.parse(WnWPoint(0, 0)))
		
		Assert.assertNotNull("upper corner not considered", grid.parse(WnWPoint(0, 49)))
		Assert.assertNotNull("lower corner not considered", grid.parse(WnWPoint(0, -50)))
		Assert.assertNotNull("right corner not considered", grid.parse(WnWPoint(49, 0)))
		Assert.assertNotNull("left corner not considered", grid.parse(WnWPoint(-50, 0)))
		
		Assert.assertNotNull("point on line (up-left) not noticed", grid.parse(WnWPoint(30, 40)))
		Assert.assertNotNull("point on line (down-left) not noticed", grid.parse(WnWPoint(30, -40)))
		Assert.assertNotNull("point on line (up-left) not noticed", grid.parse(WnWPoint(-30, 40)))
		Assert.assertNotNull("point on line (down-left) not noticed", grid.parse(WnWPoint(-30, -40)))
		
		Assert.assertNull("grid-corner (down-left) erronous noticed", grid.parse(WnWPoint(-50, -50)))
		Assert.assertNull("grid-corner (up-left) erronous noticed", grid.parse(WnWPoint(-50, 50)))
		Assert.assertNull("grid-corner (down-right) erronous noticed", grid.parse(WnWPoint(50, -50)))
		Assert.assertNull("grid-corner (up-right) erronous noticed", grid.parse(WnWPoint(50, 50)))
		
		Assert.assertNull("point should be outside the ellipse", grid.parse(WnWPoint(30, 41)))
	}
			
	@Test
	fun testGridParse_tolerance142()
	{
		var grid = Grid(100, 100, -50, -50, 1, 1, 142)
		
		Assert.assertNotNull("center should always be considered", grid.parse(WnWPoint(0, 0)))
		
		Assert.assertNotNull("right corner not considered", grid.parse(WnWPoint(49, 0)))
		Assert.assertNotNull("left corner not considered", grid.parse(WnWPoint(-50, 0)))
		Assert.assertNotNull("lower corner not considered", grid.parse(WnWPoint(0, -50)))
		Assert.assertNotNull("upper corner not considered", grid.parse(WnWPoint(0, 49)))
		
		Assert.assertNotNull("upper-right grid-corner not considered", grid.parse(WnWPoint(49, 49)))
		Assert.assertNotNull("lower-right grid-corner not considered", grid.parse(WnWPoint(49, -50)))
		Assert.assertNotNull("uppder-left grid-corner not considered", grid.parse(WnWPoint(-50, 49)))
		Assert.assertNotNull("lower-left grid-corner not considered", grid.parse(WnWPoint(-50, -50)))
		
		Assert.assertNotNull("any point not detected", grid.parse(WnWPoint(-49, 10)))
		Assert.assertNotNull("any point not detected", grid.parse(WnWPoint(38, 29)))
		Assert.assertNotNull("any point not detected", grid.parse(WnWPoint(1, -25)))
	}
	
	@Test
	fun testGridParse_tolerance50()
	{
		var grid = Grid(100, 100, -50, -50, 1, 1, 50)
		
		Assert.assertNotNull("center should alwas be considered", grid.parse(WnWPoint(0, 0)))
		
		Assert.assertNotNull("right corner not considered", grid.parse(WnWPoint(25, 0)))
		Assert.assertNotNull("left corner not considered", grid.parse(WnWPoint(-25, 0)))
		Assert.assertNotNull("upper corner not considered", grid.parse(WnWPoint(0, 25)))
		Assert.assertNotNull("lower corner not considered", grid.parse(WnWPoint(0, -25)))
		
		Assert.assertNotNull("point on ellipse (down-right) not considered", grid.parse(WnWPoint(20, -15)))
		Assert.assertNotNull("point on ellipse (up-left) not considered", grid.parse(WnWPoint(-10, 22)))
		
		Assert.assertNull("grid-corner (right) erronous detected", grid.parse(WnWPoint(49, 0)))
		Assert.assertNull("grid-corner (upper) erronous detected", grid.parse(WnWPoint(0, 49)))
		Assert.assertNull("grid-corner (left) erronous detected", grid.parse(WnWPoint(-50, 0)))
		Assert.assertNull("grid-corner (lower) erronous detected", grid.parse(WnWPoint(0, -50)))
		
		Assert.assertNull("point outside ellipse (right) erronous detected", grid.parse(WnWPoint(26, 0)))
		Assert.assertNull("point outside ellipse (upper) erronous detected", grid.parse(WnWPoint(0, 26)))
		Assert.assertNull("point outside ellipse (left) erronous detected", grid.parse(WnWPoint(-26, 0)))
		Assert.assertNull("point outside ellipse (lower) erronous detected", grid.parse(WnWPoint(0, -26)))
		
		Assert.assertNull("grid-corner (lower-left) erronous detected", grid.parse(WnWPoint(-50, -50)))
		Assert.assertNull("grid-corner (lower-right) erronous detected", grid.parse(WnWPoint(49, -50)))
		Assert.assertNull("grid-corner (upper-left) erronous detected", grid.parse(WnWPoint(-50, 49)))
		Assert.assertNull("grid-corner (upper-right) erronous detected", grid.parse(WnWPoint(49, 49)))
	}
	
	@Test
	fun testGridParse_tolerance0()
	{
		var grid = Grid(100, 100, -50, -50, 1, 1, 0)
		
		Assert.assertEquals("center should always be considered", WnWPoint(0, 0), grid.parse(WnWPoint(0, 0)))
		
		Assert.assertNull("any point next to center (right) erronous detected", grid.parse(WnWPoint(1, 0)))
		Assert.assertNull("any point next to center (left) erronous detected", grid.parse(WnWPoint(-1, 0)))
		Assert.assertNull("any point next to center (upper) erronous detected", grid.parse(WnWPoint(0, 1)))
		Assert.assertNull("any point next to center (lower) erronous detected", grid.parse(WnWPoint(0, -1)))
		
		Assert.assertNull("any point (right) erronous detected", grid.parse(WnWPoint(25, 0)))
		Assert.assertNull("any point (left) erronous detected", grid.parse(WnWPoint(-25, 0)))
		Assert.assertNull("any point (upper) erronous detected", grid.parse(WnWPoint(0, 25)))
		Assert.assertNull("any point (lower) erronous detected", grid.parse(WnWPoint(0, -25)))
		
		Assert.assertNull("grid-corner (right) erronous detected", grid.parse(WnWPoint(49, 0)))
		Assert.assertNull("grid-corner (left) erronous detected", grid.parse(WnWPoint(-50, 0)))
		Assert.assertNull("grid-corner (upper) erronous detected", grid.parse(WnWPoint(0, 49)))
		Assert.assertNull("grid-corner (lower) erronous detected", grid.parse(WnWPoint(0, -50)))
		
		Assert.assertNull("grid-corner (upper-right) erronous detected", grid.parse(WnWPoint(49, 49)))
		Assert.assertNull("grid-corner (upper-left) erronous detected", grid.parse(WnWPoint(-50, 49)))
		Assert.assertNull("grid-corner (lower-right) erronous detected", grid.parse(WnWPoint(49, -50)))
		Assert.assertNull("grid-corner (lower-left) erronous detected", grid.parse(WnWPoint(-50, -50)))
	}
	
	@Test
	fun testGridParse_3x3()
	{
		var grid = Grid(100, 100, -150, -150, 3, 3, 100)
		
		Assert.assertEquals("center point (lower-left) not detected", WnWPoint(0, 0), grid.parse(WnWPoint(-100, -100)))
		Assert.assertEquals("center point (center-left) not detected", WnWPoint(0, 1), grid.parse(WnWPoint(-100, 0)))
		Assert.assertEquals("center point (upper-left) not detected", WnWPoint(0, 2), grid.parse(WnWPoint(-100, 100)))
		Assert.assertEquals("center point (lower-center) not detected", WnWPoint(1, 0), grid.parse(WnWPoint(0, -100)))
		Assert.assertEquals("center point (center-center) not detected", WnWPoint(1, 1), grid.parse(WnWPoint(0, 0)))
		Assert.assertEquals("center point (upper-center) not detected", WnWPoint(1, 2), grid.parse(WnWPoint(0, 100)))
		Assert.assertEquals("center point (lower-right) not detected", WnWPoint(2, 0), grid.parse(WnWPoint(100, -100)))
		Assert.assertEquals("center point (center-right) not detected", WnWPoint(2, 1), grid.parse(WnWPoint(100, 0)))
		Assert.assertEquals("center point (upper-right) not detected", WnWPoint(2, 2), grid.parse(WnWPoint(100, 100)))
		
		Assert.assertEquals("left corner of grid(0, 0) not detected", WnWPoint(0, 0), grid.parse(WnWPoint(-150, -100)))
		Assert.assertEquals("lower corner of grid(0, 0) not detected", WnWPoint(0, 0), grid.parse(WnWPoint(-100, -150)))
		Assert.assertEquals("right corner of grid(0, 0) not detected", WnWPoint(0, 0), grid.parse(WnWPoint(-51, -100)))
		Assert.assertEquals("upper corner of grid(0, 0) not detected", WnWPoint(0, 0), grid.parse(WnWPoint(-100, -51)))
		Assert.assertNull("lower-left grid-corner of grid(0, 0) erronous detected", grid.parse(WnWPoint(-150, -150)))
		Assert.assertNull("lower-right grid-corner of grid(0, 0) erronous detected", grid.parse(WnWPoint(-51, -150)))
		Assert.assertNull("upper-left grid-corner of grid(0, 0) erronous detected", grid.parse(WnWPoint(-150, 51)))
		Assert.assertNull("upper-right grid-corner of grid(0, 0) erronous detected", grid.parse(WnWPoint(-51, -51)))
		
		Assert.assertEquals("left corner of grid(0, 2) not detected", WnWPoint(0, 2), grid.parse(WnWPoint(-150, 100)))
		Assert.assertEquals("lower corner of grid(0, 2) not detected", WnWPoint(0, 2), grid.parse(WnWPoint(-100, 50)))
		Assert.assertEquals("right corner of grid(0, 2) not detected", WnWPoint(0, 2), grid.parse(WnWPoint(-51, 100)))
		Assert.assertEquals("upper corner of grid(0, 2) not detected", WnWPoint(0, 2), grid.parse(WnWPoint(-100, 149)))
		Assert.assertNull("lower-left grid-corner of grid(0, 2) erronous detected", grid.parse(WnWPoint(-150, 50)))
		Assert.assertNull("lower-right grid-corner of grid(0, 2) erronous detected", grid.parse(WnWPoint(-51, 50)))
		Assert.assertNull("upper-left grid-corner of grid(0, 2) erronous detected", grid.parse(WnWPoint(-150, 149)))
		Assert.assertNull("upper-right grid-corner of grid(0, 2) erronous detected", grid.parse(WnWPoint(-51, 149)))
		
		Assert.assertEquals("left corner of grid(2, 0) not detected", WnWPoint(2, 0), grid.parse(WnWPoint(50, -100)))
		Assert.assertEquals("lower corner of grid(2, 0) not detected", WnWPoint(2, 0), grid.parse(WnWPoint(100, -150)))
		Assert.assertEquals("right corner of grid(2, 0) not detected", WnWPoint(2, 0), grid.parse(WnWPoint(149, -100)))
		Assert.assertEquals("upper corner of grid(2, 0) not detected", WnWPoint(2, 0), grid.parse(WnWPoint(100, -51)))
		Assert.assertNull("lower-left grid-corner of grid(2, 0) erronous detected", grid.parse(WnWPoint(50, -150)))
		Assert.assertNull("lower-right grid-corner of grid(2, 0) erronous detected", grid.parse(WnWPoint(149, -150)))
		Assert.assertNull("upper-left grid-corner of grid(2, 0) erronous detected", grid.parse(WnWPoint(50, -51)))
		Assert.assertNull("upper-right grid-corner of grid(2, 0) erronous detected", grid.parse(WnWPoint(149, -51)))
		
		Assert.assertEquals("left corner of grid(2, 2) not detected", WnWPoint(2, 2), grid.parse(WnWPoint(50, 100)))
		Assert.assertEquals("lower corner of grid(2, 2) not detected", WnWPoint(2, 2), grid.parse(WnWPoint(100, 50)))
		Assert.assertEquals("right corner of grid(2, 2) not detected", WnWPoint(2, 2), grid.parse(WnWPoint(149, 100)))
		Assert.assertEquals("upper corner of grid(2, 2) not detected", WnWPoint(2, 2), grid.parse(WnWPoint(100, 149)))
		Assert.assertNull("lower-left grid-corner of grid(2, 2) erronous detected", grid.parse(WnWPoint(50, 50)))
		Assert.assertNull("lower-right grid-corner of grid(2, 2) erronous detected", grid.parse(WnWPoint(149, 50)))
		Assert.assertNull("upper-left grid-corner of grid(2, 2) erronous detected", grid.parse(WnWPoint(50, 149)))
		Assert.assertNull("upper-right grid-corner of grid(2, 2) erronous detected", grid.parse(WnWPoint(149, 149)))
	}
	
	@Test
	fun testGridParse_ellipse_tolerance100()
	{
		var grid = Grid(100, 200, -50, -100, 1, 1, 100);
		
		Assert.assertEquals("center not considered", WnWPoint(0, 0), grid.parse(WnWPoint(0, 0)))
		
		Assert.assertNotNull("left corner not considered", grid.parse(WnWPoint(-50, 0)))
		Assert.assertNotNull("upper corner not considered", grid.parse(WnWPoint(0, 99)))
		Assert.assertNotNull("right corner not considered", grid.parse(WnWPoint(49, 0)))
		Assert.assertNotNull("lower corner not considered", grid.parse(WnWPoint(0, -100)))
		
		Assert.assertNull("lower-left grid-corner erronous detected", grid.parse(WnWPoint(-50, -100)))
		Assert.assertNull("lower-right grid-corner erronous detected", grid.parse(WnWPoint(49, -100)))
		Assert.assertNull("upper-left grid-corner erronous detected", grid.parse(WnWPoint(-50, 99)))
		Assert.assertNull("upper-right grid-corner erronous detected", grid.parse(WnWPoint(49, 99)))
		
		Assert.assertNotNull("point on line (up-left) not noticed", grid.parse(WnWPoint(30, 80)))
		Assert.assertNotNull("point on line (down-left) not noticed", grid.parse(WnWPoint(30, -80)))
		Assert.assertNotNull("point on line (up-left) not noticed", grid.parse(WnWPoint(-30, 80)))
		Assert.assertNotNull("point on line (down-left) not noticed", grid.parse(WnWPoint(-30, -80)))
		
		Assert.assertNull("point should be outside of the ellipse", grid.parse(WnWPoint(31, 80)))
		Assert.assertNull("point should be outside of the ellipse", grid.parse(WnWPoint(31, -80)))
		Assert.assertNull("point should be outside of the ellipse", grid.parse(WnWPoint(-31, 80)))
		Assert.assertNull("point should be outside of the ellipse", grid.parse(WnWPoint(-31, -80)))
		
		Assert.assertNull("point should be outside of the ellipse", grid.parse(WnWPoint(30, 81)))
		Assert.assertNull("point should be outside of the ellipse", grid.parse(WnWPoint(30, -81)))
		Assert.assertNull("point should be outside of the ellipse", grid.parse(WnWPoint(-30, 81)))
		Assert.assertNull("point should be outside of the ellipse", grid.parse(WnWPoint(-30, -81)))
	}
	
	@Test
	fun testGridParse_ellipse_tolerance142()
	{
		var grid = Grid(100, 200, -50, -100, 1, 1, 142)
		
		Assert.assertNotNull("center should always be considered", grid.parse(WnWPoint(0, 0)))
		
		Assert.assertNotNull("right corner not considered", grid.parse(WnWPoint(49, 0)))
		Assert.assertNotNull("left corner not considered", grid.parse(WnWPoint(-50, 0)))
		Assert.assertNotNull("lower corner not considered", grid.parse(WnWPoint(0, -100)))
		Assert.assertNotNull("upper corner not considered", grid.parse(WnWPoint(0, 99)))
		
		Assert.assertNotNull("upper-right grid-corner not considered", grid.parse(WnWPoint(49, 99)))
		Assert.assertNotNull("lower-right grid-corner not considered", grid.parse(WnWPoint(49, -100)))
		Assert.assertNotNull("uppder-left grid-corner not considered", grid.parse(WnWPoint(-50, 99)))
		Assert.assertNotNull("lower-left grid-corner not considered", grid.parse(WnWPoint(-50, -100)))
		
		Assert.assertNotNull("any point not detected", grid.parse(WnWPoint(-49, 10)))
		Assert.assertNotNull("any point not detected", grid.parse(WnWPoint(38, 29)))
		Assert.assertNotNull("any point not detected", grid.parse(WnWPoint(1, -25)))
	}
	
	@Test
	fun testGridParse_ellipse_tolerance50()
	{
		var grid = Grid(100, 200, -50, -100, 1, 1, 50)
		
		Assert.assertNotNull("center should alwas be considered", grid.parse(WnWPoint(0, 0)))
		
		Assert.assertNotNull("right corner not considered", grid.parse(WnWPoint(25, 0)))
		Assert.assertNotNull("left corner not considered", grid.parse(WnWPoint(-25, 0)))
		Assert.assertNotNull("upper corner not considered", grid.parse(WnWPoint(0, 50)))
		Assert.assertNotNull("lower corner not considered", grid.parse(WnWPoint(0, -50)))
		
		Assert.assertNotNull("point on ellipse (down-right) not considered", grid.parse(WnWPoint(20, -30)))
		Assert.assertNotNull("point on ellipse (up-left) not considered", grid.parse(WnWPoint(-10, 44)))
		
		Assert.assertNull("grid-corner (right) erronous detected", grid.parse(WnWPoint(49, 0)))
		Assert.assertNull("grid-corner (upper) erronous detected", grid.parse(WnWPoint(0, 99)))
		Assert.assertNull("grid-corner (left) erronous detected", grid.parse(WnWPoint(-50, 0)))
		Assert.assertNull("grid-corner (lower) erronous detected", grid.parse(WnWPoint(0, -100)))
		
		Assert.assertNull("point outside ellipse (right) erronous detected", grid.parse(WnWPoint(26, 0)))
		Assert.assertNull("point outside ellipse (upper) erronous detected", grid.parse(WnWPoint(0, 51)))
		Assert.assertNull("point outside ellipse (left) erronous detected", grid.parse(WnWPoint(-26, 0)))
		Assert.assertNull("point outside ellipse (lower) erronous detected", grid.parse(WnWPoint(0, -51)))
		
		Assert.assertNull("grid-corner (lower-left) erronous detected", grid.parse(WnWPoint(-50, -100)))
		Assert.assertNull("grid-corner (lower-right) erronous detected", grid.parse(WnWPoint(49, -100)))
		Assert.assertNull("grid-corner (upper-left) erronous detected", grid.parse(WnWPoint(-50, 99)))
		Assert.assertNull("grid-corner (upper-right) erronous detected", grid.parse(WnWPoint(49, 99)))
	}
	
	@Test
	fun testGridParse_ellipse_tolerance0()
	{
		var grid = Grid(100, 200, -50, -100, 1, 1, 0)
		
		Assert.assertEquals("center should always be considered", WnWPoint(0, 0), grid.parse(WnWPoint(0, 0)))
		
		Assert.assertNull("any point next to center (right) erronous detected", grid.parse(WnWPoint(1, 0)))
		Assert.assertNull("any point next to center (left) erronous detected", grid.parse(WnWPoint(-1, 0)))
		Assert.assertNull("any point next to center (upper) erronous detected", grid.parse(WnWPoint(0, 1)))
		Assert.assertNull("any point next to center (lower) erronous detected", grid.parse(WnWPoint(0, -1)))
		
		Assert.assertNull("any point (right) erronous detected", grid.parse(WnWPoint(25, 0)))
		Assert.assertNull("any point (left) erronous detected", grid.parse(WnWPoint(-25, 0)))
		Assert.assertNull("any point (upper) erronous detected", grid.parse(WnWPoint(0, 25)))
		Assert.assertNull("any point (lower) erronous detected", grid.parse(WnWPoint(0, -25)))
		
		Assert.assertNull("grid-corner (right) erronous detected", grid.parse(WnWPoint(49, 0)))
		Assert.assertNull("grid-corner (left) erronous detected", grid.parse(WnWPoint(-50, 0)))
		Assert.assertNull("grid-corner (upper) erronous detected", grid.parse(WnWPoint(0, 49)))
		Assert.assertNull("grid-corner (lower) erronous detected", grid.parse(WnWPoint(0, -50)))
		
		Assert.assertNull("grid-corner (upper-right) erronous detected", grid.parse(WnWPoint(49, 49)))
		Assert.assertNull("grid-corner (upper-left) erronous detected", grid.parse(WnWPoint(-50, 49)))
		Assert.assertNull("grid-corner (lower-right) erronous detected", grid.parse(WnWPoint(49, -50)))
		Assert.assertNull("grid-corner (lower-left) erronous detected", grid.parse(WnWPoint(-50, -50)))
	}
}