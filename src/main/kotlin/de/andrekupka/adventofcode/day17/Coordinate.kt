package de.andrekupka.adventofcode.day17

data class Coordinate(
    val values: List<Int>
)

val Coordinate.dimension get() = values.size

@ExperimentalStdlibApi
fun Coordinate.liftToDimension(targetDimension: Int): Coordinate {
    require(targetDimension >= dimension)

    return if (dimension == targetDimension) {
        this
    } else {
        Coordinate(buildList {
            addAll(values)
            repeat(targetDimension - dimension) {
                add(0)
            }
        })
    }
}

@ExperimentalStdlibApi
fun Coordinate.adjacentCoordinates() = values.fold(listOf(emptyList<Int>())) { subCoordinates, dimensionValue ->
    subCoordinates.flatMap { subCoordinate ->
        (dimensionValue - 1..dimensionValue + 1).map { subCoordinate + listOf(it) }
    }
}.map { Coordinate(it) }.filter { it != this }