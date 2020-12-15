import java.io.File
import kotlin.system.measureTimeMillis

fun determineLastSpokenValue(input: List<String>, rounds:  Int): Int {
    val cache = input
        .mapIndexed { i, n -> n.toInt() to mutableListOf(i + 1) }
        .toMap().toMutableMap()

    var lastSpoken = input.last().toInt()
    for(round in input.size + 1 .. rounds) {
        var occurrences = cache[lastSpoken]
        lastSpoken = if (occurrences == null || occurrences.size < 2) {
            0
        } else {
            occurrences[1] - occurrences[0]
        }

        occurrences = cache[lastSpoken]
        if (occurrences != null) {
            cache[lastSpoken] = mutableListOf(occurrences.last(), round)
        } else {
            cache[lastSpoken] = mutableListOf(round)
        }
    }
    println("Cache max value: ${cache.keys.maxOrNull()}")
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
