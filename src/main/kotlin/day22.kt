import java.io.File

fun crabCombat(deckOne: MutableList<Int>, deckTwo: MutableList<Int>) {
    var round = 1
    while(deckOne.isNotEmpty() && deckTwo.isNotEmpty()) {
        println("-- Round ${round++} --")
        println("Player 1's deck: $deckOne")
        println("Player 2's deck: $deckTwo")

        val cardOne = deckOne.first()
        val cardTwo = deckTwo.first()
        println("Player 1 plays $cardOne")
        println("Player 2 plays $cardTwo")

        deckOne.removeAt(0)
        deckTwo.removeAt(0)
        if(cardOne > cardTwo) {
            println("Player 1 wins the round!")
            deckOne.add(cardOne)
            deckOne.add(cardTwo)
        } else {
            println("Player 2 wins the round!")
            deckTwo.add(cardTwo)
            deckTwo.add(cardOne)
        }
        println()
    }

    println("== Post-game results ==")
    println("Player 1's deck: $deckOne")
    println("Player 2's deck: $deckTwo")
    val winnerDeck = if(deckOne.isEmpty()) deckTwo else deckOne
    val score = winnerDeck.reduceRightIndexed { i, n, acc -> acc + (winnerDeck.size - i) * n }
    println("1. The winning player's score is $score")
}

fun recursiveCombat(deckOne: MutableList<Int>, deckTwo: MutableList<Int>, game: Int = 1): Int {
    println("=== Game $game ===")
    val deckHistory = mutableSetOf<Pair<List<Int>, List<Int>>>()
    var round = 1

    while(deckOne.isNotEmpty() && deckTwo.isNotEmpty()) {
        val history = Pair(deckOne, deckTwo)
        if(deckHistory.contains(history)) {
            return 1
        }
        deckHistory.add(history)

        println("-- Round ${round++} (Game $game) --")
        println("Player 1's deck: $deckOne")
        println("Player 2's deck: $deckTwo")
        val cardOne = deckOne.first()
        val cardTwo = deckTwo.first()
        deckOne.removeAt(0)
        deckTwo.removeAt(0)
        println("Player 1 plays $cardOne")
        println("Player 2 plays $cardTwo")

        val winner = if(deckOne.size >= cardOne && deckTwo.size >= cardTwo) {
            println("Playing a sub-game to determine the winner...")
            recursiveCombat(deckOne.take(cardOne).toMutableList(), deckTwo.take(cardTwo).toMutableList(), game + 1)
        } else {
            if(cardOne > cardTwo) 1 else 2
        }

        if(winner == 1) {
            println("Player 1 wins round $round of game $game!")
            deckOne.add(cardOne)
            deckOne.add(cardTwo)
        } else {
            println("Player 2 wins $round of game $game")
            deckTwo.add(cardTwo)
            deckTwo.add(cardOne)
        }
    }
    val gameWinner = if(deckOne.isEmpty()) 2 else 1
    println("The winner of game $game is player $gameWinner!")
    println()

    if(game == 1) {
        println("== Post-game results ==")
        println("Player 1's deck: $deckOne")
        println("Player 2's deck: $deckTwo")
        val winnerDeck = if (deckOne.isEmpty()) deckTwo else deckOne
        val score = winnerDeck.reduceRightIndexed { i, n, acc -> acc + (winnerDeck.size - i) * n }
        println("2. The winning player's score is $score")
    }

    return gameWinner
}

fun main() {
    val decks = File({}.javaClass.getResource("day22.txt").toURI())
        .readText()
        .split("\r\n\r\n")
        .map { deck -> deck.split("\r\n").drop(1).map { card -> card.toInt() } }
    println(decks)

    crabCombat(decks[0].toMutableList(), decks[1].toMutableList())
    recursiveCombat(decks[0].toMutableList(), decks[1].toMutableList())
}