import java.io.File

fun main() {
    var numOfDistinct = 0
    var numOfAll = 0

    File({}.javaClass.getResource("day6.txt").toURI())
        .readText()
        .split("\r\n\r\n")
        .map { it.split("\r\n") }
        .forEach { group ->
            val all = group
                .map { it.toSet() }
                .reduce{ acc, it -> acc.union(it) }
            val dist = group
                .map { it.toSet() }
                .reduce{ acc, it -> acc.intersect(it) }
            numOfAll += all.count()
            numOfDistinct += dist.count()
        }

    println("The number of questions to which anyone answered \"yes\" is $numOfAll.")
    println("The number of questions to which everyone answered \"yes\" is $numOfDistinct.")
}
