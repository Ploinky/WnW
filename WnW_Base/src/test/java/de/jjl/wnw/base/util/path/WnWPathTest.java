package de.jjl.wnw.base.util.path;

import org.junit.Test;

import java.util.List;

import org.junit.Assert;

public class WnWPathTest
{
	@Test
	public void testProperties()
	{
		WnWPathSimple path = new WnWPathSimple(new WnWDisplaySystem(0, 0));
		path.addPoint(2, 3);
		path.addPoint(4, 5);
		
		Assert.assertEquals("wrong minX", 2, path.getPathMinX());
		Assert.assertEquals("wrong maxX", 4, path.getPathMaxX());
		Assert.assertEquals("wrong pathWidth", 2, path.getPathWidth());
		Assert.assertEquals("wrong minY", 3, path.getPathMinY());
		Assert.assertEquals("wrong maxY", 5, path.getPathMaxY());
		Assert.assertEquals("wrong pathHeight", 2, path.getPathHeight());
	}

	@Test
	public void testTrim()
	{
		WnWPathSimple path = new WnWPathSimple();
		path.addPoint(2, 3);
		path.addPoint(4, 5);
		
		WnWPath pathTrimmed = path.trimmed();
		
		Assert.assertEquals("wrong minX", 0, pathTrimmed.getPathMinX());
		Assert.assertEquals("wrong maxX", path.getPathWidth(), pathTrimmed.getPathMaxX());
		Assert.assertEquals("wrong pathWidth", path.getPathWidth(), pathTrimmed.getPathWidth());
		Assert.assertEquals("wrong minY", 0, pathTrimmed.getPathMinY());
		Assert.assertEquals("wrong maxY", path.getPathHeight(), pathTrimmed.getPathMaxY());
		Assert.assertEquals("wrong pathHeight", path.getPathHeight(), pathTrimmed.getPathHeight());
		
		List<WnWPoint> points = pathTrimmed.toList() ;
		
		Assert.assertEquals("trimmed path has wrong number of points", 2, points.size());
		Assert.assertEquals("first point is wrong", new WnWPoint(0, 0), points.get(0));
		Assert.assertEquals("second point is wrong", new WnWPoint(2, 2), points.get(1));
	}

	@Test
	public void testTrim_empty()
	{
		WnWPathSimple path = new WnWPathSimple();
		WnWPath pathTrimmed = path.trimmed();
		
		Assert.assertFalse("Trimmed path should be empty", pathTrimmed.iterator().hasNext());
	}

	@Test
	public void testForSystem_bigger()
	{
		WnWPathSimple path = new WnWPathSimple(new WnWDisplaySystem(5, 5));
		path.addPoint(2, 2);
		path.addPoint(4, 4);
		
		WnWPath path2 = path.forSystem(new WnWDisplaySystem(10, 10));
		
		List<WnWPoint> points = path2.toList();
		
		Assert.assertEquals("New path should still have all points", 2, points.size());
		Assert.assertEquals("First point was not transformed correctly", new WnWPoint(4, 4), points.get(0));
		Assert.assertEquals("Second point was not transformed correctly", new WnWPoint(8, 8), points.get(1));
	}

	@Test
	public void testForSystem_smaller()
	{
		WnWPathSimple path = new WnWPathSimple(new WnWDisplaySystem(6, 6));
		path.addPoint(2, 2);
		path.addPoint(4, 4);
		
		WnWPath path2 = path.forSystem(new WnWDisplaySystem(3, 3));
		
				List<WnWPoint> points = path2.toList();
		
		Assert.assertEquals("New path should still have all points", 2, points.size());
		Assert.assertEquals("First point was not transformed correctly", new WnWPoint(1, 1), points.get(0));
		Assert.assertEquals("Second point was not transformed correctly", new WnWPoint(2, 2), points.get(1));
	}

	@Test
	public void testForSystem_removeDuplicate()
	{
		WnWPathSimple path = new WnWPathSimple(new WnWDisplaySystem(100, 100));
		path.addPoint(25, 30);
		path.addPoint(10, 38);
		path.addPoint(17, 96);

		WnWPath path2 = path.forSystem(new WnWDisplaySystem(0, 0));

		List<WnWPoint> points = path2.toList();

		Assert.assertEquals("New path has wrong number of points", 1, points.size());
		Assert.assertEquals("New path should only have ZERO-Point", WnWPoint.ZERO, points.get(0));
	}
}