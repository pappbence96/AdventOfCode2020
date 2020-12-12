import java.io.File

enum class Seat(val value: Int) { FLOOR(0), FREE(0), OCCUPIED(1) }

class Room(private var seats: List<List<Seat>>) {
    private fun seatsWithStatus(status: Seat): Int = seats.sumOf { it.count { s -> s == status } }
    private fun getValue(i: Int, j: Int): Int = seats.getOrNull(i)?.getOrNull(j)?.value ?: 0
    private val dimX: Int get() = seats.size
    private val dimY: Int get() = seats[0].size

    private fun scanInDirection(x: Int, y: Int, xd: Int, yd: Int, distance: Int = Int.MAX_VALUE): Int {
        if(xd == 0 && yd == 0) return 0
        var currX = x + xd
        var currY = y + yd
        var steps = 1
        while(currX in (0 until dimX) && currY in(0 until dimY) && steps < distance && seats[currX][currY] == Seat.FLOOR) {
            steps++
            currX += xd
            currY += yd
        }
        if(steps > distance) return 0
        return getValue(currX, currY)
    }

    fun refresh(neighborsOnly: Boolean = true, tolerance: Int = 4): Int {
        val newSeats = seats.map { it.toMutableList() }
        var changes = 0

        for(i in seats.indices) {
            for(j in seats[i].indices) {
                val neighbors = (-1..1).sumOf {
                        a -> (-1..1).sumOf {
                            b -> scanInDirection(i, j, a, b, if (neighborsOnly) 1 else Int.MAX_VALUE)
                        }
                    }
                if(seats[i][j] == Seat.FREE && neighbors == 0) {
                    newSeats[i][j] = Seat.OCCUPIED
                    changes++
                } else if(seats[i][j] == Seat.OCCUPIED && neighbors >= tolerance) {
                    newSeats[i][j] = Seat.FREE
                    changes++
                }
            }
        }
        this.seats = newSeats
        return changes
    }

    fun equilibrium(neighborsOnly: Boolean = true, tolerance: Int = 4): Int {
        while(refresh(neighborsOnly, tolerance) > 0) { }
        return seatsWithStatus(Seat.OCCUPIED)
    }

    fun print() {
        val map = mapOf(Seat.FLOOR to ".", Seat.FREE to "L", Seat.OCCUPIED to "#")
        seats.forEach { println(it.joinToString("") { s -> map.getValue(s) }) }
        println()
    }

    companion object Factory {
        fun fromResource(resource: String): Room =
            File({}.javaClass.getResource(resource).toURI())
                .readLines()
                .map { row -> row.map { if (it == '.') Seat.FLOOR else if (it == '#') Seat.OCCUPIED else Seat.FREE }.toMutableList() }
                .let { Room(it) }
    }
}

fun main() {
    var room = Room.fromResource("day11.txt")
    println("1. Number of occupied seats: ${room.equilibrium()}")

    room = Room.fromResource("day11.txt")
    println("2. Number of occupied seats: ${room.equilibrium(false, 5)}")
}