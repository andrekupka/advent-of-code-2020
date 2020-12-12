package de.andrekupka.adventofcode.day12

data class MovementVector(
    val deltaX: Int,
    val deltaY: Int
)

operator fun Int.times(vector: MovementVector) =
    MovementVector(this * vector.deltaX, this * vector.deltaY)