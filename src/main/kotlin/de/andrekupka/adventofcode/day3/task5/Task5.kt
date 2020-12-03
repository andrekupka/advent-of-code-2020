package de.andrekupka.adventofcode.day3.task5

import de.andrekupka.adventofcode.day3.FieldType
import de.andrekupka.adventofcode.day3.MapWithTrees
import de.andrekupka.adventofcode.day3.TraversalFieldTypeCounter
import de.andrekupka.adventofcode.day3.fromLines
import de.andrekupka.adventofcode.utils.readLines

fun main(args: Array<String>) {
    val path = args[0]

    val lines = readLines(path)

    val map = MapWithTrees.fromLines(lines)
    val counter = TraversalFieldTypeCounter(stepX = 3, stepY = 1)
    val treeCount = counter.countFieldTypeDuringTraversal(map, FieldType.TREE)
    println("Encountered $treeCount trees")
}