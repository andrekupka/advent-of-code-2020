package de.andrekupka.adventofcode.day11

import de.andrekupka.adventofcode.utils.map.*
import de.andrekupka.adventofcode.utils.readLinesNotBlank


@ExperimentalStdlibApi
private fun performStepWithAdjacentSeatRules(layout: SeatLayout): SeatLayout {
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

private fun SeatLayout.countVisibleOccupiedSeats(x: Int, y: Int) = countVisibleFieldTypes(
    x, y, blockType = PositionType.EMPTY_SEAT, searchType = PositionType.OCCUPIED_SEAT
)

@ExperimentalStdlibApi
private fun performStepWithVisibleSeatRules(layout: SeatLayout): SeatLayout {
    val nextLayout = layout.toMutableFieldMap()

    fun nextTypeForEmptySeat(x: Int, y: Int): PositionType =
        if (layout.countVisibleOccupiedSeats(x, y) == 0) {
            PositionType.OCCUPIED_SEAT
        } else PositionType.EMPTY_SEAT

    fun nextTypeForOccupiedSeat(x: Int, y: Int): PositionType =
        if (layout.countVisibleOccupiedSeats(x, y) >= 5) {
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
fun performLayoutConvergence(layout: SeatLayout, stepOperation: (SeatLayout) -> SeatLayout) {
    val maximumSteps = 100
    val result = computeConvergedSeatLayout(layout, maximumSteps) { stepOperation(it) }
    if (result == null) {
        println("Did not converge within $maximumSteps steps")
        return
    }
    val (steps, finalLayout) = result
    println("Took $steps steps to converge")

    val occupiedSeatsCount = finalLayout.count { it == PositionType.OCCUPIED_SEAT }
    println("There are $occupiedSeatsCount occupied seats")
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])
    val layout = SeatLayoutParser.parseMap(lines)

    performLayoutConvergence(layout) {
        performStepWithAdjacentSeatRules(it)
    }

    performLayoutConvergence(layout) {
        performStepWithVisibleSeatRules(it)
    }
}