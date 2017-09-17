package de.jjl.wnw.base.rune.parser

import de.jjl.wnw.base.rune.WnWInputParser
import de.jjl.wnw.base.input.WnWInput
import de.jjl.wnw.base.rune.WnWRune
import de.jjl.wnw.base.input.WnWPathInput
import de.jjl.wnw.base.util.path.WnWPath
import de.jjl.wnw.base.util.path.WnWPathSimple
import de.jjl.wnw.base.util.path.WnWDisplaySystem
import de.jjl.wnw.base.util.path.WnWPoint
import de.jjl.wnw.base.util.path.*
import de.jjl.wnw.dev.conn.rune.Runes
import java.lang.Long;

class WnWPathInputParser: WnWInputParser
{
	class Config
	{
		/** the number of fields within one row of the grid */
		var gridWidth = 3
			set(value) { field = Math.max(value, 1) }
		/** the number of fields within on column of the grid */
		var gridHeight = 3
			set(value) { field = Math.max(value, 1) }
		/** the min height of one field of the grid */
		var minFieldHeight = 10
			set(value) { field = Math.max(value, 1) }
		/** the max height of one field of the grid */
		var maxFieldHeight = 1000
			set(value) { field = Math.max(value, 1) }
		/** the min width of one field of the grid */
		var minFieldWidth = 10
			set(value) { field = Math.max(value, 1) }
		/** the max width of one field of the grid */
		var maxFieldWidth = 1000
			set(value) { field = Math.max(value, 1) }
		/**
		 * the tolerance in which to accept a point (in full percent).<br>
		 * a value of 0 means the point will only be acceppted if it is exactly on the grid-center,
		 * a value of 100 means the point must be within an ellipse described by the field
		 * and a value of > (sqrt(2) * 100) means all points shall be accepted
		 */
		var fieldTolerance = 100
			set(value) { field = Math.max(value, 0) }
		/** if true a given position in the grid may be more than once in the filtered path */
		var moveFieldTwice = true
		/** if true a path of ... -> A -> B -> A -> ... is possible */
		var moveFieldBack= false
		/** Describes how the corners of the shall be interpreted in the grid */
		var corner = GridCorner.Center
	}
	
	enum class GridCorner
	{
		/** The corners of the path are interpreted as corners of the grid as well */
		 Corner,
		/** the corners of the path are interpreted as center-points of the first/last fields of the grid*/
		 Center
	}
	
	class Grid(val fieldWidth: Int, val fieldHeight: Int, val startX: Int, val startY: Int, val rows: Int, val cols: Int, val tolerance: Int)
	{
		private val zero: WnWPoint = WnWPoint(startX, startY)
		private val fieldSize: WnWPoint = WnWPoint(fieldWidth, fieldHeight)
		private val gridSize: WnWPoint = WnWPoint(cols, rows)
		
		fun parse(point: WnWPoint): WnWPoint?
		{
			var temp = point - zero
			temp = temp / fieldSize
			
			if(temp.x < 0 || temp.x >= gridSize.x || temp.y < 0 || temp.y >= gridSize.y)
			{
				return null
			}
			
			val fieldCenter = zero + (temp * fieldSize) + (fieldSize / 2)
			
			val centerDist = WnWPoint(
							Math.max(fieldCenter.x, point.x) - Math.min(fieldCenter.x, point.x),
							Math.max(fieldCenter.y, point.y) - Math.min(fieldCenter.y, point.y))
			
			var percent = ((Math.pow(centerDist.x.toDouble(), 2.0) / Math.pow(fieldSize.x.toDouble() / 2, 2.0)
						+ Math.pow(centerDist.y.toDouble(), 2.0) / Math.pow(fieldSize.y.toDouble() / 2, 2.0))
						* 100).toInt()
			
			return if(tolerance >= percent) temp else null
		}
	}
	
	override fun parseInput(input: WnWInput): WnWRune?
	{
		if(input is WnWPathInput)
		{
			return parsePath(input.path)
		}
		return null
	}
	
	fun parsePath(path: WnWPath, config: Config = Config()): WnWRune?
	{
		val runePath = filterRunePath(path.trimmed(), config)
		return lookupRune(runePath, config)
	}
	
	fun filterRunePath(path: WnWPath, config: Config): WnWPath
	{
		val grid = buildGrid(path, config)
		return filterRunePath(path, config, grid)
	}
	
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
	
	fun buildGrid(path: WnWPath, config: Config): Grid
	{
		var gridWidth = 0
		var gridHeight = 0
		when(config.corner)
		{
			GridCorner.Center ->
			{
				gridWidth = path.system.width / (config.gridWidth - 1)
				gridHeight = path.system.height / (config.gridHeight - 1)
			}
			GridCorner.Corner ->
			{
				gridWidth = path.system.width / config.gridWidth
				gridHeight = path.system.height / config.gridHeight
			}
		}
		
		if(gridWidth * config.gridWidth < path.system.width) gridWidth += 1
		if(gridHeight * config.gridHeight < path.system.height) gridHeight += 1
		
		gridWidth = Math.max(gridWidth, config.minFieldWidth)
		gridWidth = Math.min(gridWidth, config.maxFieldWidth)
		
		gridHeight = Math.max(gridHeight, config.minFieldHeight)
		gridHeight = Math.min(gridHeight, config.maxFieldHeight)
		
		var startX = (path.system.width - (gridWidth * config.gridWidth)) / 2
		var startY = (path.system.height - (gridHeight * config.gridHeight)) / 2
		
		return Grid(gridWidth, gridHeight, startX, startY, config.gridHeight, config.gridWidth, config.fieldTolerance)
	}
	
	fun lookupRune(path: WnWPath, config: Config): WnWRune?
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

operator fun WnWPoint.times(factor: WnWPoint) = WnWPoint(x * factor.x, y * factor.y)

operator fun WnWPoint.div(factor: WnWPoint) = WnWPoint(x / factor.x, y / factor.y)