package de.andrekupka.adventofcode.day12

data class Location(
    val x: Int,
    val y: Int
)


operator fun Location.plus(scaledDirection: ScaledMovementDirection) = Location(
    x + scaledDirection.deltaX,
    y + scaledDirection.deltaY
)