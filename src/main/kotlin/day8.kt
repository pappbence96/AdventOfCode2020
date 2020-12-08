import java.io.File

fun runInstructions(input: List<Pair<String, Int>>): Pair<Int, Boolean> {
    var acc = 0
    var ptr = 0
    val visited = mutableListOf<Int>()
    while(!(visited.contains(ptr) || (ptr == input.size))) {
        visited.add(ptr)
        when(input[ptr].first) {
            "nop" -> ptr++
            "acc" -> {
                acc += input[ptr].second
                ptr++
            }
            "jmp" -> ptr += input[ptr].second
        }
    }
    return Pair(acc, ptr == input.size)
}

fun swap(p: Pair<String, Int>): Pair<String, Int> = p.copy(first = if(p.first == "nop") "jmp" else "nop")

fun main() {
    val input = File({}.javaClass.getResource("day8.txt").toURI())
        .readLines()
        .map { line -> line.split(" ").let { Pair(it[0], it[1].toInt()) } }
        .toMutableList()

    println("1. The value in the accumulator is ${runInstructions(input).first}")

    input
        .withIndex()
        .filter { it.value.first != "acc" }
        .map { it.index }
        .takeWhile { i ->
            input[i] = swap(input[i])
            val (acc, finished) = runInstructions(input)
            input[i] = swap(input[i])
            if(finished) {
                println("1. The value in the accumulator is $acc after swapping instruction #$i (${input[i]})")
            }
            !finished
        }
}
