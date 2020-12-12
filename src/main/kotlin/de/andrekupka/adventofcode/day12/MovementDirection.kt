package de.andrekupka.adventofcode.day12

enum class MovementDirection(
    val deltaX: Int,
    val deltaY: Int
) {

    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0);

    fun nextClockwise() = when(this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    fun nextCounterClockwise() = when(this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }
}

operator fun Int.times(direction: MovementDirection) =
    MovementVector(this * direction.deltaX, this * direction.deltaY)