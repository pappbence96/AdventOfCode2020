import java.io.File

fun main() {
    File({}.javaClass.getResource("day5.txt").toURI())
        .readLines()
        .map { line -> line.map { if(it in arrayOf('F', 'L')) 0 else 1 }.reduce { p, i -> p * 2 + i } }
        .sorted()
        .also { println("1. The highest seat id is ${it.last()}") }
        .zipWithNext { a, b -> if(b - a == 1) null else a + 1 }
        .first { it != null }
        .also { println("2. Your seat id is $it") }
}