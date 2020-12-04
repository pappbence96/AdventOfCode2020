import java.io.File

fun main() {
    Regex("\\r\\n(\\w)")
        .replace(File({}.javaClass.getResource("day4.txt").toURI()).readText(), " $1" )
        .split("\r\n")
        .map { line -> Regex("(\\w+):(#?\\w+)").findAll(line).map { Pair(it.groupValues[1], it.groupValues[2]) }.toList() }
        .filter { it.count { p -> p.first != "cid" } == 7  }
        .also { println("1. Number of valid passwords: ${it.size}") }
        .filter { line ->
            line.all {(f, s) ->
                when(f) {
                    "byr" -> s.toInt() in 1920 .. 2002
                    "iyr" -> s.toInt() in 2010 .. 2020
                    "eyr" -> s.toInt() in 2020 .. 2030
                    "hgt" -> s.length > 2 && s.take(s.length - 2).toInt() in if(s.endsWith("cm")) 150 .. 193 else if(s.endsWith("in")) 59 .. 76 else 0 .. 0
                    "hcl" -> "#[0-9a-f]{6}".toRegex().matches(s)
                    "ecl" -> s in arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                    "pid" -> "[0-9]{9}".toRegex().matches(s)
                    "cid" -> true
                    else -> false
                }
            }
        }
        .also { println("2. Number of valid passwords: ${it.size}") }
}