package de.andrekupka.adventofcode.day9

import de.andrekupka.adventofcode.utils.readLinesMapNotBlank
import kotlin.math.max

fun <T> List<T>.getXPrecedingElementsFromIndex(precedingSize: Int, index: Int): List<T> {
    val inclusiveMinimumIndex = max(index - precedingSize, 0)
    return subList(inclusiveMinimumIndex, index)
}

fun List<Long>.sumsOfAnyTwoContained(): Set<Long> =
    flatMapIndexed { firstIndex, firstNumber ->
        subList(firstIndex + 1, size).map { secondNumber ->
            firstNumber + secondNumber
        }
    }.toSet()

fun findFirstNumberNotSumOf2AfterPreamble(numbers: List<Long>, preambleSize: Int): Long? {
    numbers.subList(preambleSize, numbers.size).forEachIndexed { index, number ->
        val precedingNumbers = numbers.getXPrecedingElementsFromIndex(preambleSize, index + preambleSize)
        val sumsOfTwo = precedingNumbers.sumsOfAnyTwoContained()
        if (number !in sumsOfTwo) {
            return number
        }
    }
    return null
}

fun main(args: Array<String>) {
    val numbers = readLinesMapNotBlank(args[0]) { it.toLong() }

    val result = findFirstNumberNotSumOf2AfterPreamble(numbers, 25)
    println("First number not matching is $result")
}