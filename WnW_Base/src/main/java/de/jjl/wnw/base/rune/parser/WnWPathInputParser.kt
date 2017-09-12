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

class WnWPathInputParser: WnWInputParser
{
	class Config
	{
		var gridWidth = 3
			set(value) { field = Math.max(value, 1) }
		var gridHeight = 3
			set(value) { field = Math.max(value, 1) }
		var minFieldHeight = 10
			set(value) { field = Math.max(value, 1) }
		var maxFieldHeight = 10
			set(value) { field = Math.max(value, 1) }
		var minFieldWidth = 10
			set(value) { field = Math.max(value, 1) }
		var maxFieldWidth = 10
			set(value) { field = Math.max(value, 1) }
		var fieldTolerance = 100
			set(value) { field = Math.max(value, 0) }
	}
	
	class Grid(val fieldWidth: Int, val fieldHeight: Int, val startX: Int, val startY: Int, val tolerance: Int)
	{
		private val zero: WnWPoint = WnWPoint(startX, startY)
		private val size: WnWPoint = WnWPoint(fieldWidth, fieldHeight)
		
		fun parse(point: WnWPoint): WnWPoint?
		{
			var temp = point - zero
			temp = WnWPoint(temp.x / fieldWidth, temp.y / fieldHeight)
			
			val fieldCenter = zero + (temp * size) + (size / 2)
			
			val centerDist = WnWPoint(
							Math.max(fieldCenter.x, point.x) - Math.min(fieldCenter.x, point.x),
							Math.max(fieldCenter.y, point.y) - Math.min(fieldCenter.y, point.y))
			
			val distSq = centerDist.x * centerDist.x + centerDist.y * centerDist.y
			
			val angle = Math.atan(centerDist.y.toDouble() / centerDist.x.toDouble())
			val percent = (Math.sin(angle) * centerDist.x + Math.cos(angle) * centerDist.y) / (fieldWidth + fieldHeight)
			
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
		val path = filterRunePath(path, config)
		return lookupRune(path)
	}
	
	fun filterRunePath(path: WnWPath, config: Config): WnWPath
	{
		val ret = WnWPathSimple(WnWDisplaySystem(config.gridWidth, config.gridHeight, path.system.xAxis, path.system.yAxis))
		val pathTrimmed = path.trimmed()
		
		val grid = buildGrid(pathTrimmed, config)
		
		if(grid == null) TODO()
		
		var lastPoint: WnWPoint? = null
		
		for(point in pathTrimmed)
		{
			val nextPoint = grid.parse(point)
			
			if(nextPoint != null && nextPoint != lastPoint)
			{
				ret.addPoint(nextPoint)
				lastPoint = nextPoint
			}
		}
		
		return ret
	}
	
	fun buildGrid(path: WnWPath, config: Config): Grid?
	{
		var gridWidth = path.system.width / config.gridWidth
		var gridHeight = path.system.height / config.gridHeight
		
		if(gridWidth * config.gridWidth < path.system.width) gridWidth += 1
		if(gridHeight * config.gridHeight < path.system.height) gridHeight += 1
		
		gridWidth = Math.max(gridWidth, config.minFieldWidth)
		gridWidth = Math.min(gridWidth, config.maxFieldWidth)
		
		gridHeight = Math.max(gridHeight, config.minFieldHeight)
		gridHeight = Math.min(gridHeight, config.maxFieldHeight)
		
		if(gridWidth * config.gridWidth < path.system.width) TODO()
		if(gridHeight * config.gridHeight < path.system.height) TODO()
		
		var startX = 0
		var startY = 0
		
		if(gridWidth * config.gridWidth > path.system.width)
		{
			startX = -(path.system.width - (gridWidth * config.gridWidth)) / 2
		}
		if(gridHeight * config.gridHeight > path.system.height)
		{
			startY = -(path.system.height - (gridHeight * config.gridHeight)) / 2
		}
		
		return Grid(gridWidth, gridHeight, startX, startY, config.fieldTolerance)
	}
	
	fun lookupRune(path: WnWPath): WnWRune?
	{
		TODO()
	}
}

operator fun WnWPoint.times(factor: WnWPoint) = WnWPoint(x * factor.x, y * factor.y)

operator fun WnWPoint.div(factor: WnWPoint) = WnWPoint(x / factor.x, y / factor.y)