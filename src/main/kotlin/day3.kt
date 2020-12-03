import java.io.File

fun main() {
    val input = File({}.javaClass.getResource("day3.txt").toURI())
        .readLines()

    arrayOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2)).map { (r, d) ->
        input.filterIndexed { i, line -> i % d == 0 && line[i / d * r % line.length] == '#' }.count().toLong()
    }.also{ println(it) }.reduce { p, i -> p * i }.also { println(it) }
}