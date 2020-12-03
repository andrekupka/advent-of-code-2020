package de.andrekupka.adventofcode.day3.task6

import de.andrekupka.adventofcode.day3.FieldType
import de.andrekupka.adventofcode.day3.MapWithTrees
import de.andrekupka.adventofcode.day3.TraversalFieldTypeCounter
import de.andrekupka.adventofcode.day3.fromLines
import de.andrekupka.adventofcode.utils.readLines
import java.io.File

fun main(args: Array<String>) {
    val path = args[0]

    val lines = readLines(path)

    val map = MapWithTrees.fromLines(lines)

    val steps = listOf(
        1 to 1,
        3 to 1,
        5 to 1,
        7 to 1,
        1 to 2
    )

    val result = steps
        .map {
            TraversalFieldTypeCounter(it.first, it.second).countFieldTypeDuringTraversal(map, FieldType.TREE).toLong()
        }
        .reduceRight { a, b -> a * b }

    println("Multiplied tree encounters are $result")
}