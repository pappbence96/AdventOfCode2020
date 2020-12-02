import java.io.File

fun main() {
    var correctPasswords1 = 0
    var correctPasswords2 = 0
    File({}.javaClass.getResource("day2.txt").toURI())
        .forEachLine {line ->
            val match = Regex("(\\d+)-(\\d+) ([a-z]): (\\w+)").find(line)!!
            val (lowerBounds, upperBounds, char, pw) = match.destructured
            if (pw.count { char[0] == it } in lowerBounds.toInt() .. upperBounds.toInt())
                correctPasswords1++
            if ((pw[lowerBounds.toInt() - 1] == char[0]) xor (pw[upperBounds.toInt() - 1] == char[0]))
                correctPasswords2++
        }
    println("1. The number of correct passwords is $correctPasswords1")
    println("2. The number of correct passwords is $correctPasswords2")
}
