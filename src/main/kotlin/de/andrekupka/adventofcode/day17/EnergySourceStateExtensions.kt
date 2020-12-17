package de.andrekupka.adventofcode.day17

fun EnergySourceState.forEach(consumer: (CellCoordinate, Boolean) -> Unit) {
    for (z in zIndexes) {
        for (y in yIndexes) {
            for (x in xIndexes) {
                consumer(CellCoordinate(x, y, z), this[x, y, z])
            }
        }
    }
}

private fun IntRange.expand() = first-1..last+1

fun EnergySourceState.expandedForEach(consumer: (CellCoordinate, Boolean) -> Unit) {
    for (z in zIndexes.expand()) {
        for (y in yIndexes.expand()) {
            for (x in xIndexes.expand()) {
                consumer(CellCoordinate(x, y, z), this[x, y, z])
            }
        }
    }
}

fun EnergySourceState.count(predicate: (CellCoordinate, Boolean) -> Boolean): Int {
    var count = 0
    forEach { coordinate, active ->
        if (predicate(coordinate, active)) {
            count++
        }
    }
    return count
}