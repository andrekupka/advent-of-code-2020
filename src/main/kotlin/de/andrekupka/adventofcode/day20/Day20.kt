package de.andrekupka.adventofcode.day20

import de.andrekupka.adventofcode.utils.groupByBlankLines
import de.andrekupka.adventofcode.utils.map.getBorders
import de.andrekupka.adventofcode.utils.map.isAdjacentInAnyOrientationTo
import de.andrekupka.adventofcode.utils.readLines

fun findCornerIds(tilesById: Map<Long, Tile>): Set<Long> {
    val neighboursByTileId = mutableMapOf<Long, Set<Long>>()

    val tileIdsWithBorders = tilesById.mapValues { it.value.map.getBorders() }

    tileIdsWithBorders.forEach { (firstId, firstBorders) ->
        tileIdsWithBorders.forEach { (secondId, secondBorders) ->
            if (firstId != secondId && firstBorders.isAdjacentInAnyOrientationTo(secondBorders)) {
                neighboursByTileId.compute(firstId) { _, value -> setOf(secondId) + (value ?: emptySet())}
                neighboursByTileId.compute(secondId) { _, value -> setOf(firstId) + (value ?: emptySet())}
            }
        }
    }


    return neighboursByTileId.filterValues { it.size == 2 }.keys
}

fun main(args: Array<String>) {
    val lines = readLines(args[0])

    val tileGroups = groupByBlankLines(lines)

    val tiles = tileGroups.map { TileParser.parse(it) }
    val tilesById = tiles.associateBy { it.id }

    val cornerIds = findCornerIds(tilesById)
    val cornerIdProduct = cornerIds.reduce { a, b -> a * b }
    println("Product of corner ids is $cornerIdProduct")
}