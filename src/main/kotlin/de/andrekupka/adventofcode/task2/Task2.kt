package de.andrekupka.adventofcode.task2

import java.io.File

fun readInput(path: String) =
    File(path)
        .readLines()
        .filter { it.isNotBlank() }
        .map { it.toInt() }

data class ResultNumbers(
    val first: Int,
    val second: Int,
    val third: Int
)

fun findThreeNumbersOfSum(numbers: List<Int>, sumValue: Int): ResultNumbers? {
    for (a in numbers) {
        for (b in numbers) {
            for (c in numbers) {
                if (a + b + c == sumValue) {
                    return ResultNumbers(a, b, c)
                }
            }
        }
    }
    return null
}

fun main(args: Array<String>) {
    val inputNumbers = readInput(args[0])

    val numbersOfSum = findThreeNumbersOfSum(inputNumbers, 2020)
    if (numbersOfSum == null) {
        println("No numbers were found")
        return
    }
    val (first, second, third) = numbersOfSum
    println("Numbers are $first, $second and $third")
    val result = first * second * third
    println("$first * $second * $third = $result")
}