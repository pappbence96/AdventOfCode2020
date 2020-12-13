import java.io.File

class Equation(val remainder: Long, val divisor: Long) {
    override fun toString(): String = "($remainder, $divisor)"
}

fun main() {
    val input = File({}.javaClass.getResource("day13.txt").toURI()).readLines()
    val timestamp = input.first().toLong()
    val ids = input.last()
        .split(",")
        .map { it.toLongOrNull() ?: -1L }
        .mapIndexed { index, i -> Equation(i - index.toLong() % i, i)  }
        .filter { it.divisor != -1L }

    val nextBus = ids.minByOrNull { id -> (timestamp / id.divisor + 1L) * id.divisor }?.divisor ?: return

    val waitTime = (timestamp / nextBus + 1) * nextBus - timestamp
    println("1. The minimum amount of waiting is ${ nextBus * waitTime }")

    var earliestOffset = ids.first().remainder
    var previousRemainders = 1L
    for(i in 1 until ids.size) {
        previousRemainders *= ids[i - 1].divisor
        while(earliestOffset % ids[i].divisor != ids[i].remainder) {
            earliestOffset += previousRemainders
        }
    }
    println("2. The next approproate time offset: $earliestOffset")
}