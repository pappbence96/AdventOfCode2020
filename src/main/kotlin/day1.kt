import java.io.File

fun main(args: Array<String>) {
    val input = File( {}.javaClass.getResource("day1.txt").toURI())
        .readLines()
        .map { it.toInt() }

    for(i in 0 until (input.size - 1)) {
        for (j in i until input.size) {
            if (input[i] + input[j] == 2020) {
                println("${input[i]} + ${input[j]} == 2020")
                println("${input[i]} * ${input[j]} == ${input[i] * input[j]}")
            }
        }
    }

    for(i in 0 until (input.size - 2)) {
        for(j in i until (input.size - 1)) {
            for (k in j until input.size) {
                if (input[i] + input[j] + input[k] == 2020) {
                    println("${input[i]} + ${input[j]} + ${input[k]} == 2020")
                    println("${input[i]} * ${input[j]} * ${input[k]} == ${input[i] * input[j] * input[k]}")
                }
            }
        }
    }
}
