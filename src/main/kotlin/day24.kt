import java.io.File

enum class HexDir { NE, E, SE, SW, W, NW }

data class Tile(var x: Int, var y: Int, var z: Int) {

    private fun moveBy(dx: Int, dy: Int, dz: Int) {
        x += dx
        y += dy
        z += dz
    }

    private fun moveInDirection(dir: HexDir) {
        when(dir) {
            HexDir.NE -> moveBy(0, 1, -1)
            HexDir.E -> moveBy(-1, 1, 0)
            HexDir.SE -> moveBy(-1, 0, 1)
            HexDir.SW -> moveBy(0, -1, 1)
            HexDir.W -> moveBy(1, -1, 0)
            HexDir.NW -> moveBy(1, 0, -1)
        }
    }

    fun moveAll(dirs: List<HexDir>) = dirs.forEach { moveInDirection(it)
    }

    fun neighbors() = HexDir.values().map { dir -> this.copy().also { it.moveInDirection(dir) } }

    fun neighborsAndSelf() = neighbors().toMutableList().also { it.add(this) }.toList()
}

fun HashSet<Tile>.flip(t: Tile) { if (contains(t)) remove(t) else add(t) }

fun main() {
    val floor = hashSetOf<Tile>()
    File({}.javaClass.getResource("day24.txt").toURI())
        .readLines()
        .map { line ->
            val dirs = "(ne|nw|se|sw|e|w)".toRegex()
                .findAll(line)
                .map { HexDir.valueOf(it.value.toUpperCase()) }
                .toList()
            Tile(0, 0, 0).also { it.moveAll(dirs) }
        }
        .forEach { floor.flip(it) }
    println("1. Initial black tiles: ${floor.count()}")

    repeat(100) {
        floor.flatMap { it.neighborsAndSelf() }
            .distinct()
            .filter { tile ->
                val active = tile.neighbors().count { n -> floor.contains(n) }
                (floor.contains(tile) && active !in 1..2) || (!floor.contains(tile) && active == 2)
            }
            .forEach { floor.flip(it)}
    }
    println("2. Black tiles after 100 days: ${floor.count()}")
}