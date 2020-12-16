import java.io.File
import kotlin.system.measureTimeMillis

fun determineLastSpokenValue(input: List<String>, rounds:  Int): Int {
    val cache = arrayOfNulls<Int>(rounds * 2)
    input.map { it.toInt() }.forEachIndexed { i, n ->
        cache[2 * n + 1] = cache[2 * n]
        cache[2 * n] = i + 1
    }

    var lastSpoken = input.last().toInt()
    for(round in input.size + 1 .. rounds) {
        lastSpoken = if (cache[lastSpoken * 2] == null || cache[lastSpoken * 2 + 1] == null) {
            0
        } else {
            cache[lastSpoken * 2]!! - cache[lastSpoken * 2 + 1]!!
        }

        cache[lastSpoken * 2 + 1] = cache[lastSpoken * 2]
        cache[lastSpoken * 2] = round
    }
    return lastSpoken
}

fun main() {
    val input = File({}.javaClass.getResource("day15.txt").toURI())
        .readText()
        .split(",")

    measureTimeMillis {
        println("1. Spoken value at round 2020: ${determineLastSpokenValue(input, 2020)}")
        println("1. Spoken value at round 30 000 000: ${determineLastSpokenValue(input, 30000000)}")
    }.also { println("Elapsed time: $it ms") }
}
