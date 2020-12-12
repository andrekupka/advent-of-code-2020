package de.andrekupka.adventofcode.day12

import kotlin.math.absoluteValue

data class Location(
    val x: Int,
    val y: Int
)

fun Location.turnClockwiseAroundOrigin() = Location(y, -x)

fun Location.turnCounterClockwiseAroundOrigin() = Location(-y, x)

fun Location.toVector() = MovementVector(x, y)

fun Location.manhattanDistance() = x.absoluteValue + y.absoluteValue

operator fun Location.plus(vector: MovementVector) = Location(
    x + vector.deltaX,
    y + vector.deltaY
)