import java.io.File

class HyperSpace(var size: Int, private val dimension: Int) {
    val active: Int get() = content.size
    private val content = HashSet<List<Int>>()

    fun activate(coords: List<Int>) {
        content.add(coords)
    }

    private fun isActive(coords: List<Int>) = content.contains(coords)

    fun ping() {
        size += 2

        val toAdd = mutableListOf<List<Int>>()
        val toRemove = mutableListOf<List<Int>>()

        val coordsToInspect = generateCoordinates((size - 1) / -2, (size - 1) / 2, dimension)
        coordsToInspect.forEach { currentCoord ->
            val offsets = generateCoordinates(-1, 1, dimension).filter { !it.all { c -> c == 0 } }
            val activeNeighbors = offsets.count { offset ->
                val neighbor = currentCoord.zip(offset).map { it.first + it.second }
                isActive(neighbor)
            }
            if(isActive(currentCoord) && activeNeighbors !in 2..3) {
                toRemove.add(currentCoord)
            } else if(!isActive(currentCoord) && activeNeighbors == 3) {
                toAdd.add(currentCoord)
            }
        }
        content.removeAll(toRemove)
        content.addAll(toAdd)
    }

    private fun generateCoordinates(start: Int, end: Int, dimension: Int): List<List<Int>> {
        return if(dimension == 1) {
            (start .. end).map { listOf(it) }
        } else {
            (start .. end).toList().flatMap {
                c -> generateCoordinates(start, end, dimension - 1).map {
                    it.toMutableList().also { it.add(c) }
                }
            }
        }
    }
}

fun main() {
    val input = File({}.javaClass.getResource("day17.txt").toURI())
        .readLines()
    val rounds = 6


    val s1 = HyperSpace(input.size, 3)
    val s2 = HyperSpace(input.size, 4)
    input.forEachIndexed { x, line ->
        line.forEachIndexed { y, c ->
            val rx = x - (input.size - 1) / 2
            val ry = y - (input.size - 1) / 2
            if(c == '#') { s1.activate(listOf(rx, ry, 0)) }
            if(c == '#') { s2.activate(listOf(rx, ry, 0, 0)) }
        }
    }

    repeat(rounds) {
        s1.ping()
        s2.ping()
        println("${s1.active}\t${s2.active}")
    }

    println("1. Number of active cubes in the 3D space: ${s1.active}")
    println("2. Number of active cubes in the 4D space: ${s2.active}")
}