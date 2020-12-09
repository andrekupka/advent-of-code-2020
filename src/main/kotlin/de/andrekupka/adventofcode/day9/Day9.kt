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

fun findNumbersThatSumUpToWeakness(numbers: List<Long>, weakness: Long): List<Long>? {
    var sum = 0L
    var inclusiveStartIndex = 0
    var exclusiveEndIndex = 0

    while (exclusiveEndIndex <= numbers.lastIndex) {
        when {
            sum < weakness -> {
                sum += numbers[exclusiveEndIndex++]
            }
            sum > weakness -> {
                sum -= numbers[inclusiveStartIndex++]
            }
            else -> break
        }
    }

    return if (sum == weakness) {
        numbers.subList(inclusiveStartIndex, exclusiveEndIndex);
    } else null
}


fun main(args: Array<String>) {
    val numbers = readLinesMapNotBlank(args[0]) { it.toLong() }

    val weakNumber = findFirstNumberNotSumOf2AfterPreamble(numbers, 25)
    if (weakNumber == null) {
        println("No weak number was found")
        return
    }
    println("First encryption weakness is $weakNumber")

    val weaknessNumbers = findNumbersThatSumUpToWeakness(numbers, weakNumber)
    if (weaknessNumbers == null || weaknessNumbers.size < 2) {
        println("No weak number range was found")
        return
    }
    println("Weak number range is $weaknessNumbers")

    val result = weaknessNumbers.minOrNull()!! + weaknessNumbers.maxOrNull()!!
    println("Weak number result is $result")
}