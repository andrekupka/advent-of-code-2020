package de.andrekupka.adventofcode.day22

import de.andrekupka.adventofcode.utils.groupByBlankLines
import de.andrekupka.adventofcode.utils.readLines

fun main(args: Array<String>) {
    val lines = readLines(args[0])
    val groupedLines = groupByBlankLines(lines)

    val decks = groupedLines.map { PlayerDeckParser.parseLines(it) }

    if (decks.size != 2) {
        println("Expected 2 decks but got ${decks.size}")
        return
    }

    val gameExecutor = GameExecutor(decks.first(), decks.last())
    val (winnerDeck, looserDeck) = gameExecutor.execute()

    println("Winning player is ${winnerDeck.name} with a score of ${winnerDeck.score}")
}