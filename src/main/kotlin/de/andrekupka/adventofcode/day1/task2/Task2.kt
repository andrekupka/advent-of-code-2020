package de.andrekupka.adventofcode.day1.task2

import de.andrekupka.adventofcode.utils.readLinesMapNotBlank
import java.io.File

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
    val inputNumbers = readLinesMapNotBlank(args[0]) { it.toInt() }

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