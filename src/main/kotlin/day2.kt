import java.io.File

data class Quadruple<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)

fun main() {
    val input = File({}.javaClass.getResource("day2.txt").toURI())
        .readLines()
        .map {
            val (l, u, c, pw) = Regex("(\\d+)-(\\d+) ([a-z]): (\\w+)").find(it)!!.destructured
            Quadruple(l.toInt(), u.toInt(), c.first(), pw)
        }

    val t1 = input.count { (min, max, char, pw) -> pw.count { char == it } in min .. max }
    println("1. The number of correct passwords is $t1")

    val t2 = input.count { (first, second, char, pw) -> (pw[first - 1] == char) xor (pw[second - 1] == char) }
    println("2. The number of correct passwords is $t2")
}
