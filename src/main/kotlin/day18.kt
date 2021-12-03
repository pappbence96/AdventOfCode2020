import java.io.File

enum class OperationType { ADD, MULT }

enum class ParenthesesType { LEFT, RIGHT }

abstract class Token

class NumericToken(val value: Int) : Token()

class ParenthesisToken(val type: ParenthesesType) : Token()

class OperatorToken(private val type: OperationType) : Token() {
    fun precedes(other: OperatorToken): Boolean = this.type < other.type

    fun perform(left: Long, right: Long): Long {
        return when (type) {
            OperationType.MULT -> left * right
            OperationType.ADD -> left + right
        }
    }
}

fun Token.isLeftParenthesis(): Boolean = this is ParenthesisToken && this.type == ParenthesesType.LEFT

fun String.spaceOutParentheses(): String {
    return this
        .replace("(", "( ")
        .replace(")", " )")
}

fun tokenize(expression: String): List<Token> {
    return expression
        .spaceOutParentheses()
        .split(' ')
        .map {
            when (it) {
                "(" -> ParenthesisToken(ParenthesesType.LEFT)
                ")" -> ParenthesisToken(ParenthesesType.RIGHT)
                "+" -> OperatorToken(OperationType.ADD)
                "*" -> OperatorToken(OperationType.MULT)
                else -> NumericToken(it.toInt())
            }
        }
}

// https://en.wikipedia.org/wiki/Shunting-yard_algorithm
fun shuntingYard(input: List<Token>, ignorePrecedence: Boolean = false): List<Token> {
    val operators = ArrayDeque<Token>()
    val output = mutableListOf<Token>()

    for (token in input) {
        when (token) {
            is NumericToken -> output.add(token)
            is OperatorToken -> {
                var operator = operators.lastOrNull()
                while (
                    operator != null
                    && !operator.isLeftParenthesis()
                    && (ignorePrecedence || operator !is OperatorToken || operator.precedes(token))
                )  {
                    operators.removeLast()
                    output.add(operator)
                    operator = operators.lastOrNull()
                }
                operators.addLast(token)
            }
            is ParenthesisToken -> {
                when (token.type) {
                    ParenthesesType.LEFT -> operators.addLast(token)
                    else -> {
                        var operator = operators.removeLast()
                        while (!operator.isLeftParenthesis()) {
                            output.add(operator)
                            operator = operators.removeLast()
                        }
                    }
                }
            }
        }
    }

    output.addAll(operators.reversed())
    return output
}

fun parseTokens(tokens: List<Token>): Long {
    val stack = ArrayDeque<Long>()

    for (token in tokens) {
        when (token) {
            is NumericToken -> stack.addLast(token.value.toLong())
            is OperatorToken -> stack.addLast(token.perform(stack.removeLast(), stack.removeLast()))
        }
    }

    return stack.removeLast()
}

fun main() {
    val tokens = File({}.javaClass.getResource("day18.txt").toURI())
        .readLines()
        .map { tokenize(it) }

    val resultNoPrecedence = tokens.sumOf { parseTokens(shuntingYard(it, true)) }
    println("1. Total result without precedence is $resultNoPrecedence")

    val result = tokens.sumOf { parseTokens(shuntingYard(it))     }
    println("2. Total result with precedence is $result")
}