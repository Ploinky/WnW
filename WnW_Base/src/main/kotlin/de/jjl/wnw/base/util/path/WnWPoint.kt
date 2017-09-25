package de.jjl.wnw.base.util.path

data class WnWPoint(val x: Int, val y: Int)
{
	companion object
	{
		@JvmStatic val ZERO = WnWPoint(0, 0)
	}
}

operator fun WnWPoint.unaryPlus() = this

operator fun WnWPoint.unaryMinus() = WnWPoint(-this.x, -this.y)

operator fun WnWPoint.plus(b: WnWPoint) = WnWPoint(x + b.x, y + b.y)

operator fun WnWPoint.minus(b: WnWPoint) = WnWPoint(x - b.x, y - b.y)

operator fun WnWPoint.times(factor: Int) = WnWPoint(x * factor, y * factor)

operator fun WnWPoint.div(factor: Int) = WnWPoint(x / factor, y / factor)

operator fun WnWPoint.rem(factor: Int) = WnWPoint(x % factor, y % factor)
