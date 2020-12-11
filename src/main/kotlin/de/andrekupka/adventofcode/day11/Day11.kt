package de.andrekupka.adventofcode.day11

import de.andrekupka.adventofcode.utils.map.count
import de.andrekupka.adventofcode.utils.map.forEach
import de.andrekupka.adventofcode.utils.map.getAdjacentFieldTypes
import de.andrekupka.adventofcode.utils.map.toMutableFieldMap
import de.andrekupka.adventofcode.utils.readLinesNotBlank


@ExperimentalStdlibApi
fun performStepWithAdjacentSeats(layout: SeatLayout): SeatLayout {
    val nextLayout = layout.toMutableFieldMap()

    fun nextTypeForEmptySeat(x: Int, y: Int): PositionType =
        if (layout.getAdjacentFieldTypes(x, y).none { it == PositionType.OCCUPIED_SEAT }) {
            PositionType.OCCUPIED_SEAT
        } else PositionType.EMPTY_SEAT

    fun nextTypeForOccupiedSeat(x: Int, y: Int): PositionType =
        if (layout.getAdjacentFieldTypes(x, y).count { it == PositionType.OCCUPIED_SEAT } >= 4) {
            PositionType.EMPTY_SEAT
        } else PositionType.OCCUPIED_SEAT

    fun nextPositionType(x: Int, y: Int, type: PositionType): PositionType {
        return when (type) {
            PositionType.FLOOR -> PositionType.FLOOR
            PositionType.EMPTY_SEAT -> nextTypeForEmptySeat(x, y)
            PositionType.OCCUPIED_SEAT -> nextTypeForOccupiedSeat(x, y)
        }
    }

    layout.forEach { x, y, type ->
        nextLayout.setFieldType(x, y, nextPositionType(x, y, type))
    }
    return nextLayout
}

@ExperimentalStdlibApi
fun computeConvergedSeatLayout(layout: SeatLayout, maximumSteps: Int, stepOperation: (SeatLayout) -> SeatLayout): Pair<Int, SeatLayout>? {
    var previousLayout = layout
    repeat(maximumSteps) { index ->
        val nextLayout = stepOperation(previousLayout)
        if (previousLayout == nextLayout) {
            return (index + 1) to previousLayout
        }
        previousLayout = nextLayout
    }
    return null
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])
    val layout = SeatLayoutParser.parseMap(lines)

    val maximumSteps = 100
    val result = computeConvergedSeatLayout(layout, maximumSteps) { performStepWithAdjacentSeats(it) }
    if (result == null) {
        println("Did not converge within $maximumSteps steps")
        return
    }
    val (steps, finalLayout) = result
    println("Took $steps steps to converge")

    val occupiedSeatsCount = finalLayout.count { it == PositionType.OCCUPIED_SEAT }
    println("There are $occupiedSeatsCount occupied seats")
}