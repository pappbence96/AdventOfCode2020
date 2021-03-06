import java.io.File
import kotlin.system.measureTimeMillis

class Node(var value: Int) {
    lateinit var next: Node
    fun insertAfter(nodes: List<Node>) {
        nodes.last().next = this.next
        this.next = nodes.first()
    }

    fun removeNext(count: Int): List<Node> {
        var nextNode = this.next
        val nodes = mutableListOf<Node>()
        repeat(count) {
            nodes.add(nextNode)
            nextNode = nextNode.next
        }
        this.next = nextNode
        return nodes
    }
}

fun mapOfNodesOf(values: List<Int>): MutableMap<Int, Node> {
    val map = mutableMapOf<Int, Node>()
    var lastNode: Node? = null
    for(i in values) {
        val node = Node(i)
        map[i] = node
        lastNode?.let { it.next = node }
        lastNode = node
    }
    lastNode?.next = map[values.first()]!!
    return map
}

fun play(cups: List<Int>, maxValue: Int, rounds: Int): Node {
    val allCups = cups.toMutableList()
    allCups.addAll((10 .. maxValue).toList())
    val map = mapOfNodesOf(allCups)

    var selected = map[cups.first()]!!
    for(i in 0 until rounds) {
        val pick = selected.removeNext(3)
        val destination =
            (selected.value - 1 downTo 1).firstOrNull { !pick.any { e -> it == e.value } } ?:
            (maxValue downTo  selected.value + 1).first { !pick.any { e -> it == e.value } }
        map[destination]!!.insertAfter(pick)
        selected = selected.next
    }

    return map[1]!!
}

fun main() {
    val cups = File({}.javaClass.getResource("day23.txt").toURI())
        .readText()
        .map { it.toString().toInt() }

    print("1. Labels on the cups after cup 1: ")
    var cup = play(cups, 9, 100).next
    while(cup.value != 1){
        print(cup.value)
        cup = cup.next
    }
    println()

    play(cups, 1000000, 10000000).next.let {
        println("2. Product of the two cups clockwise after 1: ${it.value.toLong() * it.next.value.toLong()}")
    }
}
