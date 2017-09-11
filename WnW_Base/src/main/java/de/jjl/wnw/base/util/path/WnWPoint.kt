package de.jjl.wnw.base.util.path

interface WnWPoint
{
	val x: Int
	val y: Int
	
	companion object
	{
		val ZERO = WnWPointSimple(0, 0)
	}
}

data class WnWPointSimple(override val x: Int, override val y: Int) : WnWPoint

inline fun WnWPoint(x: Int, y: Int) = WnWPointSimple(x, y)

operator fun WnWPoint.unaryPlus() = this

operator fun WnWPoint.unaryMinus() = WnWPoint(-this.x, -this.y)

operator fun WnWPoint.plus(b: WnWPoint) = WnWPoint(x + b.x, y + b.y)

operator fun WnWPoint.minus(b: WnWPoint) = WnWPoint(x - b.x, y - b.y)

operator fun WnWPoint.times(factor: Int) = WnWPoint(x * factor, y * factor)

operator fun WnWPoint.div(factor: Int) = WnWPoint(x / factor, y / factor)

operator fun WnWPoint.rem(factor: Int) = WnWPoint(x % factor, y % factor)
