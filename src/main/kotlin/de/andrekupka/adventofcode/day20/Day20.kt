package de.andrekupka.adventofcode.day20

import de.andrekupka.adventofcode.utils.groupByBlankLines
import de.andrekupka.adventofcode.utils.readLines

fun main(args: Array<String>) {
    val lines = readLines(args[0])

    val tileGroups = groupByBlankLines(lines)

    val tiles = tileGroups.map { TileParser.parse(it) }

    tiles.forEach {
        println(it.id)
        println(it.map)
    }
}