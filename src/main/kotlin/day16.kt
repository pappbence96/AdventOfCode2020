import java.io.File

data class Rule(val name: String, val r1: IntRange, val r2: IntRange) {
    fun matches(num: Int): Boolean = num in r1 || num in r2
}

fun main() {
    val input = File({}.javaClass.getResource("day16.txt").toURI())
        .readText()
        .split("\r\n\r\n")
        .map { it.split("\r\n") }

    val rules = input[0]
        .map {
            val (n, ll, lu, ul, uu) = "([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex().find(it)!!.destructured
            Rule(n, ll.toInt() .. lu.toInt(), ul.toInt() .. uu.toInt())
        }
    println("Rules: $rules")

    val myTicket = input[1]
        .last()
        .split(",")
        .map { it.toLong() }
    println("My ticket: $myTicket")

    val nearby = input[2]
        .drop(1)
        .map { it.split(",").map { n -> n.toInt() } }

    val sumOfBadFields = nearby.sumOf { ticket -> ticket.filter { field -> rules.none { rule -> rule.matches(field)  } }.sum() }
    println("1. Error scanning rate: $sumOfBadFields")

    val validRules = myTicket.map { rules.toMutableList() }

    nearby
        .filter { ticket -> ticket.none { field -> rules.none { rule -> rule.matches(field)  } } }
        .forEach { ticket -> ticket.forEachIndexed { index, number -> validRules[index].removeIf { !it.matches(number) } } }

    validRules.indices
        .map { i -> Pair(myTicket[i], validRules[i]) }
        .sortedBy { it.second.size }
        .also { data ->
           data.forEachIndexed { i, pair ->
               data.takeLast(data.size - i - 1).forEach { it.second.remove(pair.second.single()) }
           }
        }
        .map { if(it.second.single().name.startsWith("departure")) it.first else 1L }
        .reduce { acc, l -> acc * l }
        .also { println("2. Product of \"departure\" fields: $it") }
}
