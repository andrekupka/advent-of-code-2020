package de.andrekupka.adventofcode.task1

import java.io.File

fun readInput(path: String) =
    File(path)
        .readLines()
        .filter { it.isNotBlank() }
        .map { it.toInt() }

fun findNumbersOfSum(numbers: List<Int>, sumValue: Int): Pair<Int, Int>? {
    val numbersByDifferenceTo2020 = numbers.associateBy {
        sumValue - it
    }

    val secondNumber = numbers.find { it in numbersByDifferenceTo2020 } ?: return null
    val firstNumber = numbersByDifferenceTo2020[secondNumber] ?: return null
    return firstNumber to secondNumber
}

fun main(args: Array<String>) {
    val path = args[0]
    println("Reading input from $path")

    val inputNumbers = readInput(path)

    val numbersOfSum = findNumbersOfSum(inputNumbers, 2020)
    if (numbersOfSum == null) {
        println("No numbers were found")
        return
    }

    val (first, second) = numbersOfSum
    println("Numbers are $first and $second")
    val result = first * second
    println("$first * $second = $result")
}