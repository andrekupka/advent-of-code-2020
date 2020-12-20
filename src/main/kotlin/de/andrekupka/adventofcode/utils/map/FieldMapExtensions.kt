package de.andrekupka.adventofcode.utils.map

import de.andrekupka.adventofcode.utils.cross
import kotlin.math.max

inline fun <F> FieldMap<F>.forEach(operation: (x: Int, y: Int, type: F) -> Unit) {
    for (y in 0 until height) {
        for (x in 0 until width) {
            operation(x, y, getFieldType(x, y)!!)
        }
    }
}

inline fun <F> FieldMap<F>.count(predicate: (F) -> Boolean): Int {
    var count = 0
    forEach { _, _, type ->
        if (predicate(type)) {
            count++
        }
    }
    return count
}


@ExperimentalStdlibApi
fun <F> FieldMap<F>.getAdjacentFieldTypes(x: Int, y: Int): List<F> = buildList {
    for (adjacentY in y - 1..y + 1) {
        for (adjacentX in x - 1..x + 1) {
            if (adjacentX != x || adjacentY != y) {
                getFieldType(adjacentX, adjacentY)?.let { add(it) }
            }
        }
    }
}

private val DIRECTION_LIST = listOf(
    -1 to 0, // left
    -1 to -1, // left up
    0 to -1, // up
    1 to -1, // right up
    1 to 0, // right
    1 to 1, // right down
    0 to 1, // down
    -1 to 1 // left down
)

fun <F> FieldMap<F>.countVisibleFieldTypes(x: Int, y: Int, blockType: F, searchType: F): Int {
    fun searchInDirection(deltaX: Int, deltaY: Int): Boolean {
        for (distance in 1..max(width, height)) {
            val effectiveX = x + deltaX * distance
            val effectiveY = y + deltaY * distance
            when (getFieldType(effectiveX, effectiveY)) {
                blockType, null -> return false
                searchType -> return true
            }
        }
        return false
    }

    return DIRECTION_LIST.map { (deltaX, deltaY) -> searchInDirection(deltaX, deltaY) }.count { it }
}

fun <F> FieldMap<F>.getBorders(): FieldMapBorders<F> = FieldMapBorders(
    north = (0 until width).map { this[it, 0] },
    east = (0 until height).map { this[width - 1, it] },
    south = (width-1 downTo 0).map { this[it, height - 1] },
    west = (height-1 downTo 0).map { this[0, it] },
)

fun <F> FieldMapBorders<F>.toList(): List<List<F>> = listOf(north, east, south, west)

fun <F> FieldMapBorders<F>.findAdjacencyWith(other: FieldMapBorders<F>): BorderAdjacency? {
    val ownBorders = toList()
    val otherBorders = other.toList()
    ownBorders.forEachIndexed { ownDirection, ownBorder ->
        otherBorders.forEachIndexed { otherDirection, otherBorder ->
            if (ownBorder == otherBorder) {
                return BorderAdjacency(ownDirection, otherDirection, flipped = false)
            } else if (ownBorder == otherBorder.asReversed()) {
                return BorderAdjacency(ownDirection, otherDirection, flipped = true)
            }
        }
    }
    return null
}

fun BorderAdjacency.inverse() = BorderAdjacency(otherDirection, ownDirection, flipped)