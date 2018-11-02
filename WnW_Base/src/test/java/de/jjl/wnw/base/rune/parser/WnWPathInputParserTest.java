package de.jjl.wnw.base.rune.parser;

import de.jjl.wnw.base.util.path.WnWPoint;
import org.junit.Assert;
import org.junit.Test;
 
public class WnWPathInputParserTest
{
	private WnWPathInputParser parser = new WnWPathInputParser();

	@Test
	public void testBuildGrid_basic()
	{
		Config config = new Config(3, 3, 10, 1000, 10, 1000, 100, true, false, GridCorner.Corner);;

		Grid grid = parser.buildGrid(200, 200, 300, 300, config);;

		Assert.assertEquals("wrong Grid created", grid, new Grid(100, 100, 200, 200, 3, 3, 100));
	}

	@Test
	public void testBuildGrid_tolerance()
	{
		Config config = new Config(3, 3, 10, 1000, 10, 1000, 100, true, false, GridCorner.Corner);;

		Assert.assertEquals("Tolerance was not correctly given to Grid", 100,
				parser.buildGrid(0, 0, 0, 0, config).getTolerance(), 0.1);
		config.setFieldTolerance(50);
		Assert.assertEquals("Tolerance was not correctly given to Grid", 50,
				parser.buildGrid(0, 0, 0, 0, config).getTolerance(), 0.1);
		config.setFieldTolerance(143);
		Assert.assertEquals("Tolerance was not correctly given to Grid", 143,
				parser.buildGrid(0, 0, 0, 0, config).getTolerance(), 0.1);
		config.setFieldTolerance(0);
		Assert.assertEquals("Tolerance was not correctly given to Grid", 0,
				parser.buildGrid(0, 0, 0, 0, config).getTolerance(), 0.1);
	}

	@Test
	public void testBuildGrid_gridSize_square()
	{
		Config config = new Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner);;
		
		Grid grid = parser.buildGrid(0, 0, 1000, 1000, config);;
		Assert.assertEquals("Grid has wrong size on normal-sized field", new WnWPoint(3, 3), grid.getGridSize());
		
		grid = parser.buildGrid(0, 0, 10000, 10000, config);
		Assert.assertEquals("Grid has wrong size on too large field", new WnWPoint(3, 3), grid.getGridSize());
		
		grid = parser.buildGrid(0, 0, 0, 0, config);;
		Assert.assertEquals("Grid has wrong number of columns too small field", new WnWPoint(3, 3), grid.getGridSize());
	}

	public void testBuildGrid_gridSize_large()
	{
		Config config = new Config(10, 20, 10, 1000, 10, 1000, 100, true, false, GridCorner.Corner);;

		Grid grid = parser.buildGrid(0, 0, 1000, 1000, config);;
		Assert.assertEquals("Grid has wrong size on normal-sized field with high number of columsn",
				new WnWPoint(10, 20), grid.getGridSize());

		grid = parser.buildGrid(0, 0, 100000, 100000, config);;
		Assert.assertEquals("Grid has wrong size on too large field with high number of columns", new WnWPoint(10, 20),
				grid.getGridSize());

		grid = parser.buildGrid(0, 0, 0, 0, config);;
		Assert.assertEquals("Grid has wrong size on too small field with high number of columns", new WnWPoint(10, 20),
				grid.getGridSize());
	}

	public void testBuildGrid_gridSize_small()
	{
		Config config = new Config(
				1, 2,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner);;
		
		Grid grid = parser.buildGrid(0, 0, 1000, 1000, config);;
		Assert.assertEquals("Grid has wrong size on normal-sized field with low number of columns", new WnWPoint(1, 2), grid.getGridSize());
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config);;
		Assert.assertEquals("Grid has wrong size on too large field with low number of columns", new WnWPoint(1, 2), grid.getGridSize());
		
		grid = parser.buildGrid(0, 0, 0, 0, config);;
		Assert.assertEquals("Grid has size on too small field with low number of columns", new WnWPoint(1, 2), grid.getGridSize());
	}

	@Test
	public void testBuildGrid_startPos_GridCorner()
	{
		Config config = new Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner);;
		
		Grid grid = parser.buildGrid(0, 0, 900, 900, config);;
		Assert.assertEquals("wrong start-position on normal calculation", new WnWPoint(0, 0), grid.getStart());
		
		grid = parser.buildGrid(384, 516, 900, 900, config);;
		Assert.assertEquals("wrong start-position when moved into the field", new WnWPoint(384, 516), grid.getStart());
		
		grid = parser.buildGrid(-38, -1268, 900, 900, config);;
		Assert.assertEquals("wrong start-position when moved in negative", new WnWPoint(-38, -1268), grid.getStart());
	}

	public void testBuildGrid_startPos_GridCenter()
	{
		Config config = new Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Center);
				
		Grid grid = parser.buildGrid(0, 0, 1000, 1000, config);;
		Assert.assertEquals("wrong start-position on normal calculation for GridCorner.Center", new WnWPoint(-250, -250), grid.getStart());
		
		grid = parser.buildGrid(1000, 500, 1000, 1000, config);;
		Assert.assertEquals("wrong start-position when moved into the field on GridCorner.Center", new WnWPoint(750, 250), grid.getStart());
		
		grid = parser.buildGrid(-1200, -560, 1000, 1000, config);;
		Assert.assertEquals("wrong start-position when moved in negative on GridCorner.Center", new WnWPoint(-1450, -810), grid.getStart());
	}

	public void testBuildGrid_startPos_uncentered()
	{
		Config config = new Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner);;
		
		Grid grid = parser.buildGrid(0, 0, 6000, 9000, config);;
		Assert.assertEquals("wrong start-position on normal calculation when field is too large", new WnWPoint(1500, 3000), grid.getStart());
		
		grid = parser.buildGrid(-8000, -7000, 6000, 9000, config);;
		Assert.assertEquals("wrong start-position for too large field and moved in negative", new WnWPoint(-6500, -4000), grid.getStart());
		
		
		grid = parser.buildGrid(0, 0, 20, 10, config);;
		Assert.assertEquals("wrong start-position on normal calculation when field is too small", new WnWPoint(-5, -10), grid.getStart());
		
		grid = parser.buildGrid(-45, -30, 20, 10, config);;
		Assert.assertEquals("wrong start-position for too small field when moved in negative", new WnWPoint(-50, -40), grid.getStart());
	}

	@Test
	public void testBuildGrid_fieldSizes()
	{
		Config config = new Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner);
		
		Grid grid = parser.buildGrid(0, 0, 900, 900, config);
		Assert.assertEquals("wrong fieldSize on normal calculation", new WnWPoint(300, 300), grid.getFieldSize());
		
		grid = parser.buildGrid(0, 0, 10, 10, config);
		Assert.assertEquals("wrong fieldSize on too small field", new WnWPoint(10, 10), grid.getFieldSize());
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config);
		Assert.assertEquals("wrong fieldSize on too large field", new WnWPoint(1000, 1000), grid.getFieldSize());
	}

	public void testBuildGrid_fieldSizes_largeGrid()
	{
		Config config = new Config(
				5, 7,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner);
		
		Grid grid = parser.buildGrid(0, 0, 500, 1400, config);
		Assert.assertEquals("wrong fieldSize on normal calculation", new WnWPoint(100, 200), grid.getFieldSize());
		
		grid = parser.buildGrid(0, 0, 10, 10, config);
		Assert.assertEquals("wrong fieldSize on too small field", new WnWPoint(10, 10), grid.getFieldSize());
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config);
		Assert.assertEquals("wrong fieldSize on too large field", new WnWPoint(1000, 1000), grid.getFieldSize());
	}

	public void testBuildGrid_fieldSizes_minSizes()
	{
		Config config = new Config(
				5, 7,
				35, 1000,
				116, 1000,
				100,
				true, false,
				GridCorner.Corner);
		
		Grid grid = parser.buildGrid(0, 0, 500, 1400, config);
		Assert.assertEquals("wrong fieldSize on normal calculation", new WnWPoint(100, 200), grid.getFieldSize());
		
		grid = parser.buildGrid(0, 0, 10, 10, config);
		Assert.assertEquals("wrong fieldSize on too small field", new WnWPoint(35, 116), grid.getFieldSize());
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config);
		Assert.assertEquals("wrong fieldSIze on too large field", new WnWPoint(1000, 1000), grid.getFieldSize());
	}

	public void testBuildGrid_fieldSizes_maxSizes()
	{
		Config config = new Config(
				5, 7,
				10, 600,
				10, 820,
				100,
				true, false,
				GridCorner.Corner);
		
		Grid grid = parser.buildGrid(0, 0, 500, 1400, config);
		Assert.assertEquals("wrong fieldSize on normal calculation", new WnWPoint(100, 200), grid.getFieldSize());
		
		grid = parser.buildGrid(0, 0, 10, 10, config);
		Assert.assertEquals("wrong fieldSize on too small field", new WnWPoint(10, 10), grid.getFieldSize());
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config);
		Assert.assertEquals("wrong fieldSize on too large field", new WnWPoint(600, 820), grid.getFieldSize());
	}

	@Test
	public void testBuildGrid_GridCorner()
	{
		Config config = new Config(
				5, 5,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner);
		
		Grid grid = parser.buildGrid(0, 0, 1000, 1000, config);
		Assert.assertEquals("grid has the wrong start-postion of Gridcorner.Corner", new WnWPoint(0, 0), grid.getStart());
		
		config.corner = GridCorner.Center;
		grid = parser.buildGrid(0, 0, 1000, 1000, config);
		Assert.assertEquals("grid has the wrong start-postion of Gridcorner.Center", new WnWPoint(-125, -125), grid.getStart());
	}
}