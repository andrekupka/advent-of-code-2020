package de.andrekupka.adventofcode.day11

enum class PositionType {
    FLOOR,
    EMPTY_SEAT,
    OCCUPIED_SEAT;

    companion object {
        fun fromChar(c: Char): PositionType? = when(c) {
            '.' -> FLOOR
            'L' -> EMPTY_SEAT
            '#' -> OCCUPIED_SEAT
            else -> null
        }
    }
}