package de.jjl.wnw.base.util.path

import java.util.Deque
import java.util.ArrayList
import java.util.LinkedList
import de.jjl.wnw.base.input.WnWPathInput

interface WnWPath : Iterable<WnWPoint>
{
	val system: WnWDisplaySystem
	
	val pathMinX: Int
	val pathMaxX: Int
	val pathMinY: Int
	val pathMaxY: Int
	val pathWidth: Int
	val pathHeight: Int
	
	fun forSystem(system: WnWDisplaySystem): WnWPath
	fun trimmed(): WnWPath
	fun trimmedToSize(width: Int, height: Int): WnWPath
}

open class WnWPathSimple(override val system: WnWDisplaySystem) : WnWPath
{
	val points = arrayListOf<WnWPoint>()
	
	override val pathMinX
		get() = if(points.isEmpty()) 0 else points.minBy { it.x }!!.x 
	override val pathMinY
		get() = if(points.isEmpty()) 0 else points.minBy { it.y }!!.y
	override val pathMaxX
		get() = if(points.isEmpty()) 0 else points.maxBy { it.x }!!.x
	override val pathMaxY
		get() = if(points.isEmpty()) 0 else points.maxBy { it.y }!!.y
	override val pathWidth
		get() = pathMaxX - pathMinX
	override val pathHeight
		get() = pathMaxY - pathMinY
	
	override fun iterator(): Iterator<WnWPoint> = points.iterator()
	
	override fun forSystem(system: WnWDisplaySystem): WnWPath
	{
		if(system == this.system)
		{
			return this;
		}
		
		val path = WnWPathSimple(system)
		var lastPoint: WnWPoint? = null
		for(point in this)
		{
			val distX = point.x - this.system.zeroX
			val distY = point.y - this.system.zeroY
			val distXRel = distX / this.system.width
			val distYRel = distY / this.system.height
			
			val nextPoint = WnWPoint(
								system.zeroX + (system.width * distXRel),
								system.zeroY + (system.height * distYRel))
			
			if(nextPoint != lastPoint)
			{
				path.addPoint(nextPoint)
				
				lastPoint = nextPoint
			}
		}
		
		return path;
	}
	
	fun addPoint(x: Int, y: Int): Unit = addPoint(WnWPoint(x, y))
	open fun addPoint(point: WnWPoint): Unit { points.add(point) }
	
	override fun trimmedToSize(width: Int, height: Int) = trimmed().forSystem(WnWDisplaySystem(width, height, system.xAxis, system.yAxis))
	
	override fun trimmed(): WnWPath
	{
		val min = WnWPoint(pathMinX, pathMinY)
		val path = WnWPathSimple(WnWDisplaySystem(pathWidth, pathHeight, system.xAxis, system.yAxis))
		
		var lastPoint: WnWPoint? = null
		for(point in this)
		{
			val nextPoint = point - min
			
			if(nextPoint != lastPoint)
			{
				path.addPoint(nextPoint)
				lastPoint = nextPoint
			}
		}
		
		return path
	}
}