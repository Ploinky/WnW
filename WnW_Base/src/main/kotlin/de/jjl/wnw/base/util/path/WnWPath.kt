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
	
	override val pathMinX: Int = if(points.isEmpty()) 0 else points.minBy { it.x }!!.x
	override val pathMinY: Int = if(points.isEmpty()) 0 else points.minBy { it.y }!!.y
	override val pathMaxX: Int = if(points.isEmpty()) 0 else points.maxBy { it.x }!!.x
	override val pathMaxY: Int = if(points.isEmpty()) 0 else points.maxBy { it.y }!!.y
	override val pathWidth: Int = pathMaxX - pathMinX
	override val pathHeight: Int = pathMaxY - pathMinY
	
	var minX = Integer.MAX_VALUE
	var maxX = Integer.MIN_VALUE
	var minY = Integer.MAX_VALUE
	var maxY = Integer.MIN_VALUE
	var width = 0
	var height = 0
	
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
	open fun addPoint(point: WnWPoint): Unit {
		points.add(point)
		
		if(point.x < minX)
		{
			minX = point.x
		}
		if(point.x > maxX)
		{
			maxX = point.x
		}
		
		if(point.y < minY)
		{
			minY = point.y
		}
		if(point.y > maxY)
		{
			maxY = point.y
		}
		
		width = maxX - minX;
		height = maxY - minY;
	}
	
	override fun trimmedToSize(width: Int, height: Int) = trimmed().forSystem(WnWDisplaySystem(width, height, system.xAxis, system.yAxis))
	
	override fun trimmed(): WnWPath
	{
		val min = WnWPoint(pathMinX, pathMinY)
		val path = WnWPathSimple(WnWDisplaySystem(width, height, system.xAxis, system.yAxis))
		System.out.println(path.system);
		
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