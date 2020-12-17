package de.andrekupka.adventofcode.day17

data class CellCoordinate(
    val x: Int,
    val y: Int,
    val z: Int
)

@ExperimentalStdlibApi
fun CellCoordinate.adjacentCoordinates(): Set<CellCoordinate> = buildSet {
    for (zIndex in z-1..z+1) {
        for (yIndex in y-1..y+1) {
            for (xIndex in x-1..x+1) {
                if (xIndex != x || yIndex != y || zIndex != z) {
                    add(CellCoordinate(xIndex, yIndex, zIndex))
                }
            }
        }
    }
}