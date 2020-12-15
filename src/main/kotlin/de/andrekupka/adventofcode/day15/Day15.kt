package de.andrekupka.adventofcode.day15

import de.andrekupka.adventofcode.utils.readFile

fun createNumberSequence(initialNumbers: List<Long>): Sequence<Long> {
    require(initialNumbers.isNotEmpty())

    val lastTurnByNumber = mutableMapOf<Long, Int>()
    var previousNumber = 0L

    return generateSequence(0) { it + 1 }.map { currentTurn ->
        val currentNumber = if (currentTurn in initialNumbers.indices) {
            initialNumbers[currentTurn]
        } else {
            lastTurnByNumber.get(previousNumber)?.let { lastTurn -> currentTurn - lastTurn - 1 }?.toLong() ?: 0L
        }

        if (currentTurn > 0) {
            lastTurnByNumber[previousNumber] = currentTurn - 1
        }

        previousNumber = currentNumber
        currentNumber
    }
}

fun main(args: Array<String>) {
    val content = readFile(args[0])

    val numbers = content.split(",").map { it.toLong() }

    val numberSequence = createNumberSequence(numbers)
    val numberInTurn2020 = numberSequence.take(2020).last()
    println("Number in turn 2020 is $numberInTurn2020")
}