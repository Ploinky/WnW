package de.jjl.wnw.base.util.path

import org.junit.Test
import org.junit.Assert

class WnWPathTest
{
	@Test
	fun testProperties()
	{
		val path = WnWPathSimple(WnWDisplaySystem(0, 0))
		path.addPoint(2, 3)
		path.addPoint(4, 5)
		
		Assert.assertEquals("wrong minX", 2, path.pathMinX)
		Assert.assertEquals("wrong maxX", 4, path.pathMaxX)
		Assert.assertEquals("wrong pathWidth", 2, path.pathWidth)
		Assert.assertEquals("wrong minY", 3, path.pathMinY)
		Assert.assertEquals("wrong maxY", 5, path.pathMaxY)
		Assert.assertEquals("wrong pathHeight", 2, path.pathHeight)
	}
	
	@Test
	fun testTrim()
	{
		val path = WnWPathSimple()
		path.addPoint(2, 3)
		path.addPoint(4, 5)
		
		val pathTrimmed = path.trimmed()
		
		Assert.assertEquals("wrong minX", 0, pathTrimmed.pathMinX)
		Assert.assertEquals("wrong maxX", path.pathWidth, pathTrimmed.pathMaxX)
		Assert.assertEquals("wrong pathWidth", path.pathWidth, pathTrimmed.pathWidth)
		Assert.assertEquals("wrong minY", 0, pathTrimmed.pathMinY)
		Assert.assertEquals("wrong maxY", path.pathHeight, pathTrimmed.pathMaxY)
		Assert.assertEquals("wrong pathHeight", path.pathHeight, pathTrimmed.pathHeight)
		
		val points = pathTrimmed.toList() 
		
		Assert.assertEquals("trimmed path has wrong number of points", 2, points.size)
		Assert.assertEquals("first point is wrong", WnWPoint(0, 0), points[0])
		Assert.assertEquals("second point is wrong", WnWPoint(2, 2), points[1])
	}
	
	@Test
	fun testTrim_empty()
	{
		val path = WnWPathSimple()
		val pathTrimmed = path.trimmed()
		
		Assert.assertFalse("Trimmed path should be empty", pathTrimmed.iterator().hasNext())
	}
	
	@Test
	fun testForSystem_bigger()
	{
		val path = WnWPathSimple(WnWDisplaySystem(5, 5))
		path.addPoint(2, 2)
		path.addPoint(4, 4)
		
		val path2 = path.forSystem(WnWDisplaySystem(10, 10))
		
		val points = path2.toList()
		
		Assert.assertEquals("New path should still have all points", 2, points.size)
		Assert.assertEquals("First point was not transformed correctly", WnWPoint(4, 4), points[0])
		Assert.assertEquals("Second point was not transformed correctly", WnWPoint(8, 8), points[1])
	}
	
	@Test
	fun testForSystem_smaller()
	{
		val path = WnWPathSimple(WnWDisplaySystem(6, 6))
		path.addPoint(2, 2)
		path.addPoint(4, 4)
		
		val path2 = path.forSystem(WnWDisplaySystem(3, 3))
		
		val points = path2.toList()
		
		Assert.assertEquals("New path should still have all points", 2, points.size)
		Assert.assertEquals("First point was not transformed correctly", WnWPoint(1, 1), points[0])
		Assert.assertEquals("Second point was not transformed correctly", WnWPoint(2, 2), points[1])
	}
	
	@Test
	fun testForSystem_removeDuplicate()
	{
		val path = WnWPathSimple(WnWDisplaySystem(100, 100))
		path.addPoint(25, 30)
		path.addPoint(10, 38)
		path.addPoint(17, 96)
		
		val path2 = path.forSystem(WnWDisplaySystem(0 ,0))
		
		val points = path2.toList();
		
		Assert.assertEquals("New path has wrong number of points", 1, points.size)
		Assert.assertEquals("New path should only have ZERO-Point", WnWPoint.ZERO, points[0])
	}
}