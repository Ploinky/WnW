package de.jjl.wnw.base.rune.parser

import de.jjl.wnw.base.util.path.WnWPoint
import org.junit.Assert
import org.junit.Test

class WnWPathInputParserTest
{
	val parser = WnWPathInputParser()
	
	@Test
	fun testBuildGrid_basic()
	{
		val config = Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner)
		
		val grid = parser.buildGrid(200, 200, 300, 300, config)
		
		Assert.assertEquals("wrong Grid created",
				grid,
				Grid(
					100, 100,
					200, 200,
					3, 3,
					100))
	}
	
	@Test
	fun testBuildGrid_tolerance()
	{
		val config = Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner)
		
		Assert.assertEquals("Tolerance was not correctly given to Grid", 100, parser.buildGrid(0, 0, 0, 0, config).tolerance)
		config.fieldTolerance = 50
		Assert.assertEquals("Tolerance was not correctly given to Grid", 50, parser.buildGrid(0, 0, 0, 0, config).tolerance)
		config.fieldTolerance = 143
		Assert.assertEquals("Tolerance was not correctly given to Grid", 143, parser.buildGrid(0, 0, 0, 0, config).tolerance)
		config.fieldTolerance = 0
		Assert.assertEquals("Tolerance was not correctly given to Grid", 0, parser.buildGrid(0, 0, 0, 0, config).tolerance)
	}
	
	@Test
	fun testBuildGrid_gridSize()
	{
		val config = Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner)
		
		var grid = parser.buildGrid(0, 0, 1000, 1000, config)
		Assert.assertEquals("Grid has wrong size on normal-sized field", WnWPoint(3, 3), grid.gridSize);
		
		grid = parser.buildGrid(0, 0, 10000, 10000, config)
		Assert.assertEquals("Grid has wrong size on too large field", WnWPoint(3, 3), grid.gridSize);
		
		grid = parser.buildGrid(0, 0, 0, 0, config)
		Assert.assertEquals("Grid has wrong number of columns too small field", WnWPoint(3, 3), grid.gridSize);
		
		config.gridWidth = 10
		config.gridHeight = 20
		grid = parser.buildGrid(0, 0, 1000, 1000, config)
		Assert.assertEquals("Grid has wrong size on normal-sized field with high number of columsn", WnWPoint(10, 20), grid.gridSize);
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config)
		Assert.assertEquals("Grid has wrong size on too large field with high number of columns", WnWPoint(10, 20), grid.gridSize);
		
		grid = parser.buildGrid(0, 0, 0, 0, config)
		Assert.assertEquals("Grid has wrong size on too small field with high number of columns", WnWPoint(10, 20), grid.gridSize);
		
		config.gridWidth = 1
		config.gridHeight = 2
		grid = parser.buildGrid(0, 0, 1000, 1000, config)
		Assert.assertEquals("Grid has wrong size on normal-sized field with low number of columns", WnWPoint(1, 2), grid.gridSize);
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config)
		Assert.assertEquals("Grid has wrong size on too large field with low number of columns", WnWPoint(1, 2), grid.gridSize);
		
		grid = parser.buildGrid(0, 0, 0, 0, config)
		Assert.assertEquals("Grid has size on too small field with low number of columns", WnWPoint(1, 2), grid.gridSize);
	}
	
	@Test
	fun testBuildGrid_StartPos()
	{
		val config = Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner)
		
		var grid = parser.buildGrid(0, 0, 900, 900, config)
		Assert.assertEquals("wrong start-position on normal calculation", WnWPoint(0, 0), grid.start)
		
		grid = parser.buildGrid(384, 516, 900, 900, config)
		Assert.assertEquals("wrong start-position when moved into the field", WnWPoint(384, 516), grid.start)
		
		grid = parser.buildGrid(-38, -1268, 900, 900, config)
		Assert.assertEquals("wrong start-position when moved in negative", WnWPoint(-38, -1268), grid.start)
		
		
		config.corner = GridCorner.Center
		grid = parser.buildGrid(0, 0, 1000, 1000, config)
		Assert.assertEquals("wrong start-position on normal calculation for GridCorner.Center", WnWPoint(-250, -250), grid.start)
		
		grid = parser.buildGrid(1000, 500, 1000, 1000, config)
		Assert.assertEquals("wrong start-position when moved into the field on GridCorner.Center", WnWPoint(750, 250), grid.start)
		
		grid = parser.buildGrid(-1200, -560, 1000, 1000, config)
		Assert.assertEquals("wrong start-position when moved in negative on GridCorner.Center", WnWPoint(-1450, -810), grid.start)
		
		
		config.corner = GridCorner.Corner
		grid = parser.buildGrid(0, 0, 6000, 9000, config)
		Assert.assertEquals("wrong start-position on normal calculation when field is too large", WnWPoint(1500, 3000), grid.start)
		
		grid = parser.buildGrid(-8000, -7000, 6000, 9000, config)
		Assert.assertEquals("wrong start-position for too large field and moved in negative", WnWPoint(-6500, -4000), grid.start)
		
		
		grid = parser.buildGrid(0, 0, 20, 10, config)
		Assert.assertEquals("wrong start-position on normal calculation when field is too small", WnWPoint(-5, -10), grid.start)
		
		grid = parser.buildGrid(-45, -30, 20, 10, config)
		Assert.assertEquals("wrong start-position for too small field when moved in negative", WnWPoint(-50, -40), grid.start)
	}
	
	@Test
	fun testBuildGrid_fieldSizes()
	{
		val config = Config(
				3, 3,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner)
		
		var grid = parser.buildGrid(0, 0, 900, 900, config)
		Assert.assertEquals("wrong fieldSize on normal calculation", WnWPoint(300, 300), grid.fieldSize)
		
		grid = parser.buildGrid(0, 0, 10, 10, config)
		Assert.assertEquals("wrong fieldSize on too small field", WnWPoint(10, 10), grid.fieldSize)
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config)
		Assert.assertEquals("wrong fieldSize on too large field", WnWPoint(1000, 1000), grid.fieldSize)
		
		config.gridWidth = 5
		config.gridHeight = 7
		grid = parser.buildGrid(0, 0, 500, 1400, config)
		Assert.assertEquals("wrong fieldSize on normal calculation", WnWPoint(100, 200), grid.fieldSize)
		
		grid = parser.buildGrid(0, 0, 10, 10, config)
		Assert.assertEquals("wrong fieldSize on too small field", WnWPoint(10, 10), grid.fieldSize)
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config)
		Assert.assertEquals("wrong fieldSize on too large field", WnWPoint(1000, 1000), grid.fieldSize)
		
		config.minFieldWidth = 35
		config.minFieldHeight = 116
		grid = parser.buildGrid(0, 0, 500, 1400, config)
		Assert.assertEquals("wrong fieldSize on normal calculation", WnWPoint(100, 200), grid.fieldSize)
		
		grid = parser.buildGrid(0, 0, 10, 10, config)
		Assert.assertEquals("wrong fieldSize on too small field", WnWPoint(35, 116), grid.fieldSize)
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config)
		Assert.assertEquals("wrong fieldSIze on too large field", WnWPoint(1000, 1000), grid.fieldSize)
		
		config.maxFieldWidth = 600
		config.maxFieldHeight = 820
		grid = parser.buildGrid(0, 0, 500, 1400, config)
		Assert.assertEquals("wrong fieldSize on normal calculation", WnWPoint(100, 200), grid.fieldSize)
		
		grid = parser.buildGrid(0, 0, 10, 10, config)
		Assert.assertEquals("wrong fieldSize on too small field", WnWPoint(35, 116), grid.fieldSize)
		
		grid = parser.buildGrid(0, 0, 100000, 100000, config)
		Assert.assertEquals("wrong fieldSize on too large field", WnWPoint(600, 820), grid.fieldSize)
	}
	
	@Test
	fun testBuildGrid_GridCorner()
	{
		val config = Config(
				5, 5,
				10, 1000,
				10, 1000,
				100,
				true, false,
				GridCorner.Corner)
		
		var grid = parser.buildGrid(0, 0, 1000, 1000, config)
		Assert.assertEquals("grid has the wrong start-postion of Gridcorner.Corner", WnWPoint(0, 0), grid.start)
		
		config.corner = GridCorner.Center
		grid = parser.buildGrid(0, 0, 1000, 1000, config)
		Assert.assertEquals("grid has the wrong start-postion of Gridcorner.Center", WnWPoint(-125, -125), grid.start)
	}
	
	@Test
	fun testGridParse()
	{
		TODO()
	}
}