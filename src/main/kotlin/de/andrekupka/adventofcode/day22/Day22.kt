package de.andrekupka.adventofcode.day22

import de.andrekupka.adventofcode.utils.groupByBlankLines
import de.andrekupka.adventofcode.utils.readLines

fun main(args: Array<String>) {
    val lines = readLines(args[0])
    /*val lines = """
        Player 1:
        9
        2
        6
        3
        1

        Player 2:
        5
        8
        4
        7
        10
    """.trimIndent().lines()*/

    val groupedLines = groupByBlankLines(lines)

    val decks = groupedLines.map { PlayerDeckParser.parseLines(it) }

    if (decks.size != 2) {
        println("Expected 2 decks but got ${decks.size}")
        return
    }

    val gameExecutor = CombatGameExecutor(decks.first(), decks.last())
    val (winnerDeck, _) = gameExecutor.execute()

    println("Winning player is ${winnerDeck.name} with a score of ${winnerDeck.score}")

    val recursiveGameExecutor = RecursiveCombatGameExecutor(decks.first(), decks.last())
    val (recursiveWinnerDeck, _) = recursiveGameExecutor.execute()

    println("Winning player is ${recursiveWinnerDeck.name} with a score of ${recursiveWinnerDeck.score}")
}