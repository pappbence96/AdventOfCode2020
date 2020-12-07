import java.io.File

fun hasShinyGold(map: Map<String, List<Pair<String, Int>>>, current: String): Boolean {
    if(current == "shiny gold") return false
    map[current].run {
        // is not empty and either contains SG or a subgraph contains SG
        return !isNullOrEmpty() && (any { it.first == "shiny gold" || hasShinyGold(map, it.first) })
    }
}

fun countBags(map: Map<String, List<Pair<String, Int>>>, current: String): Int {
    map[current].run {
        if(isNullOrEmpty()) return 0
        return sumOf { (countBags(map, it.first) + 1) * it.second }
    }
}

fun main() {
    val input = File({}.javaClass.getResource("day7.txt").toURI())
        .readLines()
        .map {
            val (bagColor, contents) = "([\\w* ]*) bags contain (.*)".toRegex().find(it)!!.destructured
            val contentList = "(\\d+) ([\\w ]+) bags?[,.] ?".toRegex()
                .findAll(contents).map { m ->
                    val (count, color) = m.destructured
                    if(color.isEmpty()) null else Pair(color, count.toInt())
                }
                .filterNotNull().toList()
            bagColor to contentList
        }.toMap()

    val total = input.keys.count { r -> hasShinyGold(input, r) }
    val count = countBags(input,"shiny gold")
    println("1. Number of ways to pack a shiny gold bag: $total")
    println("2. Number of bags in a shiny gold bag: $count")
}