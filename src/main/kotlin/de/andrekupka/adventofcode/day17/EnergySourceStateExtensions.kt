package de.andrekupka.adventofcode.day17

private fun IntRange.expand() = first-1..last+1

private fun List<IntRange>.expandToCoordinates() = fold(listOf(emptyList<Int>())) { subCoordinates, indices ->
    subCoordinates.flatMap { subCoordinate ->
        indices.map { index ->
            subCoordinate + listOf(index)
        }
    }
}.map { Coordinate(it) }

fun EnergySourceState.forEach(consumer: (Coordinate, Boolean) -> Unit) {
    indicesOfDimensions.expandToCoordinates().forEach { coordinate ->
        consumer(coordinate, this[coordinate])
    }
}

fun EnergySourceState.expandedForEach(consumer: (Coordinate, Boolean) -> Unit) {
    indicesOfDimensions.map { it.expand() }.expandToCoordinates().forEach { coordinate ->
        consumer(coordinate, this[coordinate])
    }
}

fun EnergySourceState.count(predicate: (Coordinate, Boolean) -> Boolean): Int {
    var count = 0
    forEach { coordinate, active ->
        if (predicate(coordinate, active)) {
            count++
        }
    }
    return count
}