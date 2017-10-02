package de.jjl.wnw.base.rune.parser

import de.jjl.wnw.base.input.WnWInput
import de.jjl.wnw.base.input.WnWPathInput
import de.jjl.wnw.base.rune.WnWInputParser
import de.jjl.wnw.base.rune.WnWRune
import de.jjl.wnw.base.util.path.WnWDisplaySystem
import de.jjl.wnw.base.util.path.WnWPath
import de.jjl.wnw.base.util.path.WnWPathSimple
import de.jjl.wnw.base.util.path.WnWPoint
import de.jjl.wnw.base.util.path.div
import de.jjl.wnw.base.util.path.minus
import de.jjl.wnw.base.util.path.plus
import de.jjl.wnw.dev.conn.rune.Runes
import java.awt.geom.Ellipse2D

open class WnWPathInputParser: WnWInputParser
{
	override fun parseInput(input: WnWInput): WnWRune?
	{
		if(input is WnWPathInput)
		{
			return parsePath(input.path)
		}
		return null
	}
	
	fun parsePath(path: WnWPath, config: Config = Config()): WnWRune?  // TODO $ddd use default config from wherever
	{
		val runePath = filterRunePath(path.trimmed(), config)
		return lookupRune(runePath, config)
	}
	
	/**
	 * parse the given path using the given configuration and return a simple-path that may
	 * or may not can be directly mapped to rune
	 * 
	 * @param path the path to parse
	 * @param config to configuration for the parsing
	 * @return a parsed, simplified version of the path
	 */
	fun filterRunePath(path: WnWPath, config: Config): WnWPath
	{
		val grid = buildGrid(path, config)
		return filterRunePath(path, config, grid)
	}
	
	/**
	 * parse the given path using the given configuration and return a simple-path that may
	 * or may not can be directly mapped to rune
	 * 
	 * @param path the path to parse
	 * @param config to configuration for the parsing
	 * @param grid The grid to use to parse the path
	 * @return a parsed, simplified version of the path
	 */
	fun filterRunePath(path: WnWPath, config: Config, grid: Grid): WnWPath
	{
		val ret = WnWPathSimple(WnWDisplaySystem(config.gridWidth, config.gridHeight, path.system.xAxis, path.system.yAxis))
		
		var preLastPoint: WnWPoint? = null
		var lastPoint: WnWPoint? = null
		
		for(point in path)
		{
			val nextPoint = grid.parse(point)
			
			if(nextPoint != null
				 && nextPoint != lastPoint)
			{
				if(config.moveFieldBack || nextPoint != preLastPoint)
				{
					if(config.moveFieldTwice || nextPoint !in ret.points)
					{
						ret.addPoint(nextPoint)
						preLastPoint = lastPoint
						lastPoint = nextPoint
					}
				}
			}
		}
		
		return ret
	}
	
	/**
	 * build a grid base on the given configuration to parse the given path
	 * 
	 * @param path the path to build a grid for
	 * @param config the configuration for building the grid
	 * @return a grid to parse the given path
	 */
	fun buildGrid(path: WnWPath, config: Config): Grid
	{
		// get the pathSize
		// if given use the system-Size, else the pathSize.
		// if both or 0 use 1 to avoid exception (div by 0)
		// 
		val pathWidth = if(path.system.width != 0)
							path.system.width
						else if(path.pathWidth != 0)
							path.pathWidth
						else
							1
		val pathHeight = if(path.system.height != 0)
							path.system.height
						else if(path.pathHeight != 0)
							path.pathHeight
						else
							1
		
		return buildGrid(path.system.zeroX, path.system.zeroY, pathWidth, pathHeight, config)
	}
	
	/**
	 * build a grid base on the given configuration to parse a path placed in the field
	 * defined by the given start-position and size
	 *
	 * @param pathStartX The start-x of the field that shall be parsedby the grid
	 * @param pathStartY The start-y of the field that shall be parsed by the grid
	 * @param pathWidth The width of the field that shall be parsed by the grid
	 * @param pathHeight The height of the field that shall be parsed by the grid
	 * @param config The configuration for building the grid
	 * @return a grid to parse a path placed within the given field
	 */
	open fun buildGrid(pathStartX: Int, pathStartY: Int, pathWidth: Int, pathHeight: Int, config: Config): Grid
	{
		val width = Math.max(pathWidth, 1)
		val height = Math.max(pathHeight, 1)
		
		var gridWidth = 0
		var gridHeight = 0
		
		when(config.corner)
		{
			GridCorner.Center ->
			{
				gridWidth = width / (config.gridWidth - 1)
				gridHeight = height / (config.gridHeight - 1)
			}
			GridCorner.Corner ->
			{
				gridWidth = width / config.gridWidth
				gridHeight = height / config.gridHeight
			}
		}
		
		if(gridWidth * config.gridWidth < width) gridWidth += 1
		if(gridHeight * config.gridHeight < height) gridHeight += 1
		
		gridWidth = Math.max(gridWidth, config.minFieldWidth)
		gridWidth = Math.min(gridWidth, config.maxFieldWidth)
		
		gridHeight = Math.max(gridHeight, config.minFieldHeight)
		gridHeight = Math.min(gridHeight, config.maxFieldHeight)
		
		var startX = pathStartX + (width - (gridWidth * config.gridWidth)) / 2
		var startY = pathStartY + (height - (gridHeight * config.gridHeight)) / 2
		
		return Grid(gridWidth, gridHeight, startX, startY, config.gridHeight, config.gridWidth, config.fieldTolerance)
	}
	
	open fun lookupRune(path: WnWPath, config: Config): WnWRune?
	{
		var runeLong = 0L
		
		var i = 1
		
		for(point in path)
		{
			runeLong += ((config.gridHeight * point.y + (point.x + 1)) * i);
			
			i *= 10
		}
		var reversedNumber = 0L;
		
		while(runeLong > 0)
		{
			var temp = runeLong % 10;
			reversedNumber = reversedNumber * 10 + temp;
			runeLong = runeLong/10;
		}
		
		return Runes.getRuneForLong(reversedNumber)
	}
}

class Config(
		var gridWidth: Int = 3,
		var gridHeight: Int = 3,
		var minFieldHeight: Int = 10,
		var maxFieldHeight: Int = 1000,
		var minFieldWidth: Int = 10,
		var maxFieldWidth: Int = 1000,
		var fieldTolerance: Int = 100,
		var moveFieldTwice: Boolean = true,
		var moveFieldBack: Boolean = false,
		var corner: GridCorner = GridCorner.Center
		)
{
	/** the number of fields within one row of the grid */
//	var gridWidth = 3
//		set(value) { field = Math.max(value, 1) }
	/** the number of fields within on column of the grid */
//	var gridHeight = 3
//		set(value) { field = Math.max(value, 1) }
	/** the min height of one field of the grid */
//	var minFieldHeight = 10
//		set(value) { field = Math.max(value, 1) }
	/** the max height of one field of the grid */
//	var maxFieldHeight = 1000
//		set(value) { field = Math.max(value, 1) }
	/** the min width of one field of the grid */
//	var minFieldWidth = 10
//		set(value) { field = Math.max(value, 1) }
	/** the max width of one field of the grid */
//	var maxFieldWidth = 1000
//		set(value) { field = Math.max(value, 1) }
	/**
	 * the tolerance in which to accept a point (in full percent).<br>
	 * a value of 0 means the point will only be acceppted if it is exactly on the grid-center,
	 * a value of 100 means the point must be within an ellipse described by the field
	 * and a value of > (sqrt(2) * 100) means all points shall be accepted
	 */
//	var fieldTolerance = 100
//		set(value) { field = Math.max(value, 0) }
	/** if true a given position in the grid may be more than once in the filtered path */
//	var moveFieldTwice = true
	/** if true a path of ... -> A -> B -> A -> ... is possible */
//	var moveFieldBack= false
	/** Describes how the corners of the shall be interpreted in the grid */
//	var corner = GridCorner.Center
}
	
enum class GridCorner
{
	/** The corners of the path are interpreted as corners of the grid as well */
	 Corner,
	/** the corners of the path are interpreted as center-points of the first/last fields of the grid*/
	 Center
}
	
/**
 * Helper class to define a Grid within a field.<br>
 * The grid itself starts at a given position (startX, startY) and has cols * rows fields.
 * each field has a size of (fieldWidth, fieldHeight), so the full size of the grid is
 * (cols * fieldWidth, rows * fieldHeight).<br>
 * Using these information together with the tolerance the grid can determin the {override fun equals(other: Any?): Boolean{
return super.equals(other)
}
override fun hashCode(): Int{
return super.hashCode()
}
override fun toString(): String{
return super.toString()
}
#parse(WnWPoint) gridPosition}
 * any point given.
 * 
 * @Property fieldWidth the width of a single field of the grid
 * @Property fieldHeight the height of a single field of the grid
 * @Property startY The lowest x-coordinate of the grid - the zero-X
 * @Property startY The lowest y-coordinate of the grid - the zero-Y
 * @Property rows number of rows of the grid
 * @Property cols number of columns of the grid
 * @Property tolerance the tolerance to detect if point should be considered to agiven field in full %.<br>
 *                     a value of 0% means the point must lie directly in the center of the field while 141 (sqrt(2))
 *                     means, any point within the field will be considered.
 */
open class Grid(
		val fieldWidth: Int, val fieldHeight: Int,
		val startX: Int, val startY: Int,
		val rows: Int, val cols: Int,
		val tolerance: Int)
{
	val start = WnWPoint(startX, startY)
	val fieldSize = WnWPoint(fieldWidth, fieldHeight)
	private val fieldSizePerSqu = WnWPoint(
			Math.pow(fieldWidth * tolerance / 2 / 100.0, 2.0).toInt(),
			Math.pow(fieldHeight * tolerance / 2 / 100.0, 2.0).toInt())
	val gridSize = WnWPoint(cols, rows)
	
	/**
	 * Determine and return the gridposition of the given point.<br>
	 * The gridposition is the (x, y) position of the field of this grid, in which the point lies.
	 * This calculation considered the tolerance. If the point lies outside the grid or it does
	 * not fit into any field, null is returned.<br>
	 * I. e. considering a grid of size(1, 1) with a fieldSize of (100, 100) and a start-postion
	 * of (0, 0) if the tolerance is 100 all points with a distance <= 50 to the center (50, 50)
	 * would be considered, This would mean the points (0, 50), (25, 25) or (70, 53) would return
	 * return the gridposition (0, 0), while (95, 3), (60 100) or (0, 0) would return null.<br>
	 * on a tolerance of > 141 (sqrt(2)) all points within the grid [(0, 0) to(00, 100)] would return
	 * the gridposition (0, 0).
	 * On the other hand on a tolerance of 0 the only the point (50, 50) [the center] would be considered
	 * 
	 * @param point the point to determin the gridposition of
	 * @return the gridposition of the given point
	 */
	fun parse(point: WnWPoint): WnWPoint?
	{
		var temp = point - start
		temp = temp / fieldSize
		
		if(temp.x < 0 || temp.x >= gridSize.x || temp.y < 0 || temp.y >= gridSize.y)
		{
			return null
		}
		
		val fieldCenter = start + (temp * fieldSize) + (fieldSize / 2)
		
		if(point == fieldCenter)
		{
			return temp
		}
		
		val centerDist = WnWPoint(
						Math.max(fieldCenter.x, point.x) - Math.min(fieldCenter.x, point.x),
						Math.max(fieldCenter.y, point.y) - Math.min(fieldCenter.y, point.y))
		
		// the form:
		// a point lies within an ellipse if
		// (point.x - center.x)² / radius.x²
		// + (point.y - center.y)² / radius.y²
		// <= 1
		
		val normX = centerDist.x / (fieldWidth * 0.5 * tolerance / 100) - 0.5
		val normY = centerDist.y / (fieldHeight * 0.5 * tolerance / 100) - 0.5
		
		return if((normX * normX + normY * normY) <= 0.25) temp else null 
		
//			val v1 = fieldWidth * 0.5 * tolerance / 100.0;
//			val v2 = fieldHeight * 0.5 * tolerance / 100.0;
//			
//			val v = Math.pow(centerDist.x / v1, 2.0)
//					+ Math.pow(centerDist.y / v2, 2.0)
//			
//			return if(v <= 1) temp else null
	}
	
	override fun toString(): String
	{
		return "Grid [" +
			"zero($startX,$startY)" +
			", fieldSize($fieldWidth,$fieldHeight)" +
			", gridSize($cols,$rows)" +
			", tolerance($tolerance)" +
			"]"
	}
	
	override fun hashCode(): Int
	{
		val PRIME = 131
		
		return ((start.hashCode() * PRIME + fieldSize.hashCode()) * PRIME + gridSize.hashCode()) * PRIME
	}
	
	override fun equals(other: Any?): Boolean
	{
		return if(other is Grid)
				start == other.start
					&& fieldSize == other.fieldSize
					&& gridSize == other.gridSize
					&& tolerance == other.tolerance
				else false
	}
}

operator fun WnWPoint.times(factor: WnWPoint) = WnWPoint(x * factor.x, y * factor.y)

operator fun WnWPoint.div(factor: WnWPoint) = WnWPoint(x / factor.x, y / factor.y)

fun WnWPoint.length(): Int = this.x + this.y