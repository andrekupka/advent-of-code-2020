package de.andrekupka.adventofcode.day20

import de.andrekupka.adventofcode.utils.groupByBlankLines
import de.andrekupka.adventofcode.utils.map.BorderAdjacency
import de.andrekupka.adventofcode.utils.map.findAdjacencyWith
import de.andrekupka.adventofcode.utils.map.getBorders
import de.andrekupka.adventofcode.utils.map.inverse
import de.andrekupka.adventofcode.utils.readLines

@ExperimentalStdlibApi
fun computeBorderAdjacenciesByTileId(tilesById: Map<Long, Tile>): Map<Long, Set<Pair<Long, BorderAdjacency>>> = buildMap {
    val tileIdsWithBorders = tilesById.mapValues { it.value.map.getBorders() }

    tileIdsWithBorders.forEach { (firstId, firstBorders) ->
        tileIdsWithBorders.forEach { (secondId, secondBorders) ->
            if (firstId != secondId) {
                val adjacency = firstBorders.findAdjacencyWith(secondBorders)
                if (adjacency != null) {
                    compute(firstId) { _, value -> setOf(secondId to adjacency) + (value ?: emptySet()) }
                    compute(secondId) { _, value -> setOf(firstId to adjacency.inverse()) + (value ?: emptySet()) }
                }
            }
        }
    }
}

fun findCornerTileIds(borderAdjacenciesByTileId: Map<Long, Set<Pair<Long, BorderAdjacency>>>): Set<Long> =
    borderAdjacenciesByTileId.filterValues { it.size == 2 }.keys

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val lines = readLines(args[0])

    val tileGroups = groupByBlankLines(lines)

    val tiles = tileGroups.map { TileParser.parse(it) }
    val tilesById = tiles.associateBy { it.id }

    val borderAdjacenciesByTileId = computeBorderAdjacenciesByTileId(tilesById)

    val cornerIds = findCornerTileIds(borderAdjacenciesByTileId)
    val cornerIdProduct = cornerIds.reduce { a, b -> a * b }
    println("Product of corner ids is $cornerIdProduct")

    borderAdjacenciesByTileId.forEach { tileId, adjacencies ->
        println("Tile $tileId")
        adjacencies.forEach { adjacency ->
            with(adjacency) {
                println("\t$first -> ${second.ownDirection} - ${second.otherDirection} - ${second.flipped}")
            }
        }
    }
}