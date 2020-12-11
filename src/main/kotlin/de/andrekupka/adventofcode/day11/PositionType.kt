package de.andrekupka.adventofcode.day11

enum class PositionType(val representation: Char) {
    FLOOR('.'),
    EMPTY_SEAT('L'),
    OCCUPIED_SEAT('#');

    override fun toString(): String = representation.toString()

    companion object {
        private val representationMap = values().associateBy { it.representation }

        fun fromChar(representation: Char) = representationMap[representation]
    }
}