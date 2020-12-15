package de.andrekupka.adventofcode.day15

import de.andrekupka.adventofcode.utils.readFile
import kotlin.system.measureTimeMillis

private fun createNumberSequence(initialNumbers: List<Long>): Sequence<Long> {
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

private fun computeNumberOfTurn(initialNumbers: List<Long>, turn: Int) {
    val time = measureTimeMillis {
        val numberInTurn = createNumberSequence(initialNumbers).take(turn).last()
        println("Number in turn $turn is $numberInTurn")
    }
    println("Computing number in turn $turn took $time ms")
}

fun main(args: Array<String>) {
    val content = readFile(args[0])

    val numbers = content.split(",").map { it.toLong() }

    computeNumberOfTurn(numbers, 2020)
    computeNumberOfTurn(numbers, 30000000)
}