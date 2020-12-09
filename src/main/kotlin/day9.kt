import java.io.File

fun <T> Collection<T>.combine(): List<Pair<T, T>> = this.flatMap { first -> this.filter { it != first }.map { second -> first to second } }

fun main() {
    val input = File({}.javaClass.getResource("day9.txt").toURI())
        .readLines()
        .map { it.toLong() }

    val preambleLength = 25
    val weakness = input
        .windowed(preambleLength + 1)
        .map { Pair(it.take(preambleLength), it.last()) }
        .first { (list, number) ->
            list.combine().all { (a, b) -> a + b != number }
        }.second

    println("1. The weakness is $weakness")

    (0 until input.size - 1).first { i ->
        var sum = 0L
        val range = input.drop(i).takeWhile {
            sum += it
            sum <= weakness
        }
        if (range.size > 1 && range.sum() == weakness) {
            println("2. The weakness is ${(range.minOrNull() ?: 0) + (range.maxOrNull() ?: 0)} (${range.minOrNull() ?: 0} + ${range.maxOrNull() ?: 0})")
            return@first true
        }
        false
    }
}