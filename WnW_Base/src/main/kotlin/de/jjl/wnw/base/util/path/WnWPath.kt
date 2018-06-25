package de.jjl.wnw.base.util.path

import java.util.Deque
import java.util.ArrayList
import java.util.LinkedList
import de.jjl.wnw.base.input.WnWPathInput

interface WnWPath : Iterable<WnWPoint> 
{
	val system: WnWDisplaySystem
	
	val pathMinX: Int
		get() = map { it.x } .min() ?: 0 
	val pathMaxX: Int 
		get() = map { it.x } .max() ?: 0
	val pathMinY: Int
		get() = map { it.y } .min() ?: 0
	val pathMaxY: Int
		get() = map { it.y } .max() ?: 0
	val pathWidth: Int
		get() = pathMaxX - pathMinX
	val pathHeight: Int
		get() = pathMaxY - pathMinY
	
	fun forSystem(system: WnWDisplaySystem): WnWPath
	
	fun trimmed(): WnWPath = Trimmed(this)
	fun trimmedToSize(width: Int, height: Int): WnWPath = trimmed().forSystem(WnWDisplaySystem(width, height, system.xAxis, system.yAxis))
}

open class WnWPathSimple(override val system: WnWDisplaySystem = WnWDisplaySystem(0, 0)) : WnWPath
{
	val points = arrayListOf<WnWPoint>()
	
	override fun iterator(): Iterator<WnWPoint> = points.iterator()
	
	override open fun forSystem(system: WnWDisplaySystem): WnWPath
	{
		if(system == this.system)
		{
			return this;
		}
		
		val path = WnWPathSimple(system)
		var lastPoint: WnWPoint? = null
		
		for(point in this)
		{
			var distX = point.x - this.system.zeroX
			var distY = point.y - this.system.zeroY
			
			if(this.system.width != 0)
			{
				val distXRel = distX.toDouble() / this.system.width
				distX = (system.width * distXRel).toInt()
			}
			if(this.system.height != 0)
			{
				val distYRel = distY.toDouble() / this.system.height
				distY = (system.height * distYRel).toInt()
			}
			
			val nextPoint = WnWPoint(
								system.zeroX + distX,
								system.zeroY + distY)
			
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
}

class Trimmed(path: WnWPath) : WnWPath
{
	override val system = WnWDisplaySystem(path.pathWidth, path.pathHeight, path.system.xAxis, path.system.yAxis)
	val points: List<WnWPoint>
	
	override val pathMinX = 0
	override val pathMaxX = system.width
	override val pathMinY = 0
	override val pathMaxY = system.height
	
	init
	{
		val min = WnWPoint(path.pathMinX, path.pathMinY)
		var lastPoint: WnWPoint? = null
		
		points = path.map { it - min }
					.filter {
						val l = lastPoint
						lastPoint = it
						it != l
					}
					.toList()
	}
	
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
	
	override fun trimmed() = this
	
	override fun iterator(): Iterator<WnWPoint> = points.iterator()
}
