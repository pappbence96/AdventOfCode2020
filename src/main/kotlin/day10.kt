import java.io.File

fun main() {
    // Lookup calculated by hand in Excel, could be further extended
    val lookup = arrayOf(1L, 1L, 2L, 4L, 7L)

    File({}.javaClass.getResource("day10.txt").toURI())
        .readLines()
        .map { it.toInt() }
        .sorted()
        .toMutableList()
        .apply {
            add(0, 0)
            add(last() + 3)
        }
        .zipWithNext { a, b -> b - a }
        .apply { println("1. 1-J diffs multiplied by 3-J diffs: ${count { it == 1 } * count { it == 3 }}") }
        .joinToString("")
        .split("3")
        .map { lookup[it.length] }
        .reduce { acc, next -> acc * next }
        .also { println("2. Number of distinct arrangements: $it") }
}