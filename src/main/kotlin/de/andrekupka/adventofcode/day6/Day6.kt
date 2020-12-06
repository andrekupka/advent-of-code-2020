package de.andrekupka.adventofcode.day6

import de.andrekupka.adventofcode.utils.groupAndFoldByNonBlankLines
import de.andrekupka.adventofcode.utils.readLines

fun main(args: Array<String>) {
    val lines = readLines(args[0])
    val groupedLines = groupAndFoldByNonBlankLines(lines, { "" }) { first, second -> "$first$second" }
    val positiveAnswers = groupedLines.map { it.toSet() }

    val positiveAnswerCount = positiveAnswers.sumBy { it.size }
    println("There are overall $positiveAnswerCount positive answer")
}