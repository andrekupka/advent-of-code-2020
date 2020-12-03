package de.andrekupka.adventofcode.day1.task1

import de.andrekupka.adventofcode.utils.readLinesMapNotBlank
import java.io.File

fun findTwoNumbersOfSum(numbers: List<Int>, sumValue: Int): Pair<Int, Int>? {
    val numbersByDifferenceTo2020 = numbers.associateBy {
        sumValue - it
    }

    numbers.forEach { firstNumber ->
        val secondNumber = numbersByDifferenceTo2020[firstNumber]
        if (secondNumber != null && firstNumber != secondNumber) {
            return firstNumber to secondNumber
        }
    }

    return null
}

fun main(args: Array<String>) {
    val path = args[0]
    println("Reading input from $path")

    val inputNumbers = readLinesMapNotBlank(path) { it.toInt() }

    val numbersOfSum = findTwoNumbersOfSum(inputNumbers, 2020)
    if (numbersOfSum == null) {
        println("No numbers were found")
        return
    }

    val (first, second) = numbersOfSum
    println("Numbers are $first and $second")
    val result = first * second
    println("$first * $second = $result")
}