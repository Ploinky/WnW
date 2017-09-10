package de.jjl.wnw.base.util.path

data class WnWDisplaySystem(
			val width: Int,
			 val height: Int,
			 val xAxis: Boolean = true,
			 val yAxis: Boolean = true,
			 val zeroX: Int = if(xAxis) 0 else width,
			 val zeroY: Int = if(yAxis) 0 else height)

//data class WnWDisplaySystem(val size: WnWPoint, val zero: WnWPoint = WnWPoint.ZERO, val xAxis: Boolean = true, val yAxis: Boolean = true) {
//	constructor(
//			width: Int,
//			height: Int,
//			xAxis: Boolean = true,
//			yAxis: Boolean = true,
//			zeroX: Int = if(xAxis) 0 else width,
//			zeroY: Int = if(yAxis) 0 else height)
//	: this(WnWPoint(width, height), WnWPoint(zeroX, zeroY), xAxis, yAxis)
//}