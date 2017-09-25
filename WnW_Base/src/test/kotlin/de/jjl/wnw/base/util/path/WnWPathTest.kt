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
		
		Assert.assertEquals("", 2, path.pathMinX)
		Assert.assertEquals("", 4, path.pathMaxX)
		Assert.assertEquals("", 2, path.pathWidth)
		Assert.assertEquals("", 3, path.pathMinY)
		Assert.assertEquals("", 5, path.pathMaxY)
		Assert.assertEquals("", 2, path.pathHeight)
	}
}