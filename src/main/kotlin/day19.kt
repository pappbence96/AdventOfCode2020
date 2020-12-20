import java.io.File

fun generateRuleFor(pos: Int, rules: MutableMap<Int, String>): String {
    val rule = rules[pos]!!
    return if (!rule.contains("\\d".toRegex())) {
        rule
    } else if (rule.contains("|")) {
        val split = rule.split(" | ").map { group -> group.split(" ").map { it.toInt() } }
        val left = split.first().joinToString("") { generateRuleFor(it, rules) }
        val right = split.last().joinToString("") { generateRuleFor(it, rules) }
        val newRule = "(?:$left|$right)"
        rules[pos] = newRule
        newRule
    } else {
        val newRule = rule.split(" ").map { it.toInt() }.joinToString("") { generateRuleFor(it, rules) }
        rules[pos] = newRule
        newRule
    }
}

fun main() {
    val input = File({}.javaClass.getResource("day19.txt").toURI())
        .readText().split("\r\n\r\n")

    val inputRules = input.first()
        .split("\r\n")
        .map {
            val (i, s) = "(\\d+): (.*)".toRegex().find(it)!!.destructured
            i.toInt() to s.replace("\"", "")
        }
        .toMap()
        .toMutableMap()

    val messages = input.last().split("\r\n")

    val simpleRegex = generateRuleFor(0, inputRules.toMutableMap()).toRegex()
    messages.count { simpleRegex.matches(it) }.also { println(it) }

    inputRules[8] = "42 | 42 10001"
    inputRules[10001] = "42 | 42 10002"
    inputRules[10002] = "42 | 42 10003"
    inputRules[10003] = "42 | 42 10004"
    inputRules[10004] = "42 | 42 10005"
    inputRules[10005] = "42 | 42 10006"
    inputRules[10006] = "42 | 42 10007"
    inputRules[10007] = "42 | 42 10008"
    inputRules[10008] = "42 | 42 10009"
    inputRules[10009] = "42"
    inputRules[11] = "42 31 | 42 10011 31"
    inputRules[10011] = "42 31 | 42 10012 31"
    inputRules[10012] = "42 31 | 42 10013 31"
    inputRules[10013] = "42 31 | 42 10014 31"
    inputRules[10014] = "42 31 | 42 10015 31"
    inputRules[10015] = "42 31 | 42 10016 31"
    inputRules[10016] = "42 31 | 42 10017 31"
    inputRules[10017] = "42 31 | 42 10018 31"
    inputRules[10018] = "42 31 | 42 10019 31"
    inputRules[10019] = "42 31"
    val fakeRecursiveRegex = generateRuleFor(0, inputRules.toMutableMap()).toRegex()
    messages.count { fakeRecursiveRegex.matches(it) }.also { println(it) }
}