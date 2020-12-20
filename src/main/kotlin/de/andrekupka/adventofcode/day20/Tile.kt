package de.andrekupka.adventofcode.day20

import de.andrekupka.adventofcode.utils.map.FieldMap

enum class TileFieldType(val representation: Char) {
    WHITE('.'),
    BLACK('#');

    override fun toString() = representation.toString()

    companion object {
        private val representationMap = values().associateBy { it.representation }

        fun fromChar(representation: Char) = representationMap[representation]
    }
}

typealias TileMap = FieldMap<TileFieldType>

data class Tile(
    val id: Long,
    val map: TileMap
)