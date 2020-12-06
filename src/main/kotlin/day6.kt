import java.io.File

fun main() {
    var atLeastOneVoted = 0
    var everyoneVoted = 0

    File({}.javaClass.getResource("day6.txt").toURI())
        .readText()
        .split("\r\n\r\n")
        .map { it.split("\r\n").map { line -> line.toSet() } }
        .forEach { group ->
            atLeastOneVoted += group
                .reduce{ acc, it -> acc.union(it) }
                .count()
            everyoneVoted += group
                .reduce{ acc, it -> acc.intersect(it) }
                .count()
        }

    println("The number of questions to which anyone answered \"yes\" is $atLeastOneVoted.")
    println("The number of questions to which everyone answered \"yes\" is $everyoneVoted.")
}
