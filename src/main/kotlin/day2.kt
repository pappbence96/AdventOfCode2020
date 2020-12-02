import java.io.File

fun main(args: Array<String>) {
    val input = File( {}.javaClass.getResource("day2.txt").toURI())
        .readLines()

    val correctPasswords1 = input.count { line ->
        val match = Regex("(\\d+)-(\\d+) ([a-z]): (\\w+)").find(line)!!
        val (lowerBounds, upperBounds, char, pw) = match.destructured
        pw.count { char[0] == it } in lowerBounds.toInt() .. upperBounds.toInt()
    }
    println("1. The number of correct passwords is $correctPasswords1")

    val correctPasswords2 = input.count { line ->
        val match = Regex("(\\d+)-(\\d+) ([a-z]): (\\w+)").find(line)!!
        val (lowerBounds, upperBounds, char, pw) = match.destructured
        (pw[lowerBounds.toInt() - 1] == char[0]) xor (pw[upperBounds.toInt() - 1] == char[0])
    }
    println("2. The number of correct passwords is $correctPasswords2")
}
