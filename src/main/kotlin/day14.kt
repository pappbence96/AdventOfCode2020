import java.io.File

fun combine(values: List<Char>, length: Int): List<String> {
    return if(length == 0){
        listOf("")
    }
    else {
        values.flatMap { c -> combine(values, length - 1).map { it + c } }
    }
}

fun main() {
    val input = File({}.javaClass.getResource("day14.txt").toURI())
        .readLines()
    val memory1 = mutableMapOf<String, Long>()
    val memory2 = mutableMapOf<String, Long>()
    var mask = ""

    for(line in input) {
        if(line.contains("mask")) {
            mask = line.split(" = ").last()
        } else {
            var (address, value) = "mem\\[(\\d+)] = (\\d+)".toRegex().find(line)!!.destructured
            value = value.toInt().toString(2).padStart(36, '0')
            address = address.toInt().toString(2).padStart(36, '0')

            memory1[address] = mask.indices
                .map { i -> if (mask[i] != 'X') mask[i] else value[i] }
                .joinToString("")
                .toLong(2)

            for(combination in combine(listOf('0', '1'), mask.count { it == 'X' })) {
                var lastFloatingBitIndex = 0
                val newAddress = mask.indices.map {
                    when(mask[it]) {
                        '1' -> '1'
                        '0' -> address[it]
                        else -> combination[lastFloatingBitIndex++]
                    }
                }.joinToString("")
                memory2[newAddress] = value.toLong(2)
            }
        }
    }
    println("1. Sum of values in memory: ${memory1.values.sum()}")
    println("2. Sum of values in memory: ${memory2.values.sum()}")
}