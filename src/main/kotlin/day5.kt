import java.io.File

fun main() {
    File({}.javaClass.getResource("day5.txt").toURI())
        .readLines()
        .map { line ->
            val row = line
                .take(7)
                .map { c -> if (c == 'F') 0 else 1 }
                .reduce { p, i -> p * 2 + i }
            val col = line
                .takeLast(3)
                .map { c -> if (c == 'L') 0 else 1 }
                .reduce { p, i -> p * 2 + i }
            8 * row + col
        }
        .sorted()
        .also { println("1. The highest seat id is ${it.last()}") }
        .zipWithNext { a, b -> if(b - a == 1) null else a + 1 }
        .first { it != null }
        .also { println("2. Your seat id is $it") }
}