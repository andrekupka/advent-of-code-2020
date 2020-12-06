package de.andrekupka.adventofcode.day6

import de.andrekupka.adventofcode.utils.groupAndFoldByNonBlankLines
import de.andrekupka.adventofcode.utils.readLines

fun main(args: Array<String>) {
    val lines = readLines(args[0])

    val anyonePositiveAnswersByGroup = groupAndFoldByNonBlankLines(lines, { emptySet<Char>() }) { currentAnswers, line ->
        currentAnswers.union(line.toSet())
    }
    val anyonePositiveAnswerCount = anyonePositiveAnswersByGroup.sumBy { it.size }
    println("There are $anyonePositiveAnswerCount positive answer where anyone of a group")

    val everyonePositiveAnswersByGroup = groupAndFoldByNonBlankLines(lines, { ('a'..'z').toSet() }) { currentAnswers, line ->
        currentAnswers.intersect(line.toSet())
    }
    val everyonePositiveAnswerCount = everyonePositiveAnswersByGroup.sumBy { it.size }
    println("There are $everyonePositiveAnswerCount positive answer where everyone of a group answered")
}