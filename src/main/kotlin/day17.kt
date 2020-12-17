import java.io.File
import java.lang.Math.pow
import kotlin.math.pow

data class Quad<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)

class Space(val width: Int) {
    val cubes: Int get() = content.count { it == '#' }
    var content: Array<Char> = Array(width * width * width * width) { '.' }

    fun read(x: Int, y: Int, z: Int, w: Int): Char = content.getOrNull(w * width * width * width + z * width * width + y * width + x) ?: '.'

    fun write(x: Int, y: Int, z: Int, w: Int, c: Char) {
        content[w * width * width * width + z * width * width + y * width + x] = c
    }

    fun ping() {
        val contentCopy = content.copyOf()
        for(x in 0 until width) {
            for(y in 0 until width) {
                for(z in 0 until width) {
                    for(w in 0 until width) {
                        val r = -1..1
                        val coords = r.flatMap { cx -> r.flatMap { cy -> r.flatMap { cz -> r.map { cw -> Quad(cx, cy, cz, cw) } } } }
                            .filter { (x, y, z, w) -> !(x == 0 && y == 0 && z == 0 && w == 0) }
                        val count = coords.map { (ox, oy, oz, ow) -> if(read(x + ox, y + oy, z + oz, w + ow) == '#') 1 else 0 }.sum()
                        val current = read(x, y, z, w)
                        if(current == '#' && count !in 2..3) contentCopy[w * width * width * width + z * width * width + y * width + x] = '.'
                        else if(current == '.' && count == 3) contentCopy[w * width * width * width + z * width * width + y * width + x] = '#'
                    }
                }
            }
        }
        content = contentCopy
    }
}

fun main() {
    val input = File({}.javaClass.getResource("day17.txt").toURI())
        .readLines()
    val rounds = 6
    val maxWidth = input.size + rounds * 2

    val space = Space(maxWidth)
    val offset = (space.width - input.size) / 2
    input.forEachIndexed { x, line ->
        line.forEachIndexed { y, c ->
            println("Writing $c at (${x + (maxWidth - 1) / 2}, ${y + (maxWidth - 1) / 2}, z)")
            space.write(x - (input.size - 1) / 2 + (maxWidth - 1) / 2, y - (input.size - 1) / 2 + (maxWidth - 1) / 2, (maxWidth - 1) / 2, (maxWidth - 1) / 2, c)
        }
    }
    space.ping()
    space.ping()
    space.ping()
    space.ping()
    space.ping()
    space.ping()
    println(space.cubes)
}