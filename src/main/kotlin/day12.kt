import java.io.File
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

enum class Direction(val value: Int) { N(0), E(0), S(0), W(0), F(0), L(-1), R(1) }

class Vec2(var x: Int = 0, var y: Int = 0) {
    override fun toString() = "($x, $y)"
    operator fun plus(rhs: Vec2) = Vec2(x + rhs.x, y + rhs.y)
    operator fun times(times: Int) = Vec2(x * times, y * times)
    val manhattan get() = abs(x) + abs(y)
    fun rotate(degrees: Double) = Vec2(
        cos(degrees * Math.PI / 180).toInt() * x - sin(degrees * Math.PI / 180).toInt() * y,
        sin(degrees * Math.PI / 180).toInt() * x + cos(degrees * Math.PI / 180).toInt() * y)
}

fun processShipPathing(direction: Vec2, input: List<Pair<Direction, Int>>, moveDirection: Boolean): Vec2 {
    val map = mapOf(Direction.N to Vec2(1, 0), Direction.E to Vec2(0, 1), Direction.S to Vec2(-1, 0), Direction.W to Vec2(0, -1))
    var d = direction
    var position = Vec2()
    input.forEach { (dir, dist) ->
        when(dir) {
            Direction.F -> position += d * dist
            Direction.L, Direction.R -> d = d.rotate(dir.value * dist.toDouble())
            else -> if(moveDirection) d += map.getValue(dir) * dist else position += map.getValue(dir) * dist
        }
    }
    return position
}

fun main() {
    val input = File({}.javaClass.getResource("day12.txt").toURI())
        .readLines()
        .map { Pair(Direction.valueOf(it.take(1)), it.drop(1).toInt()) }

    println("1. Manhattan distance: ${processShipPathing(Vec2(0, 1), input, false).manhattan}")
    println("2. Manhattan distance: ${processShipPathing(Vec2(1, 10), input, true).manhattan}")
}