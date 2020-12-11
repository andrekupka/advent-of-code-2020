package de.andrekupka.adventofcode.day11

import de.andrekupka.adventofcode.utils.map.*
import de.andrekupka.adventofcode.utils.readLinesNotBlank


interface SeatRules {
    fun nextTypeForEmptySeat(layout: SeatLayout, x: Int, y: Int): PositionType
    fun nextTypeForOccupiedSeat(layout: SeatLayout, x: Int, y: Int): PositionType
}

@ExperimentalStdlibApi
private object AdjacentSeatRules : SeatRules {

    override fun nextTypeForEmptySeat(layout: SeatLayout, x: Int, y: Int) =
        if (layout.getAdjacentFieldTypes(x, y).none { it == PositionType.OCCUPIED_SEAT }) {
            PositionType.OCCUPIED_SEAT
        } else PositionType.EMPTY_SEAT

    override fun nextTypeForOccupiedSeat(layout: SeatLayout, x: Int, y: Int) =
        if (layout.getAdjacentFieldTypes(x, y).count { it == PositionType.OCCUPIED_SEAT } >= 4) {
            PositionType.EMPTY_SEAT
        } else PositionType.OCCUPIED_SEAT
}

private fun SeatLayout.countVisibleOccupiedSeats(x: Int, y: Int) = countVisibleFieldTypes(
    x, y, blockType = PositionType.EMPTY_SEAT, searchType = PositionType.OCCUPIED_SEAT
)

private object VisibleSeatRules : SeatRules {

    override fun nextTypeForEmptySeat(layout: SeatLayout, x: Int, y: Int) =
        if (layout.countVisibleOccupiedSeats(x, y) == 0) {
            PositionType.OCCUPIED_SEAT
        } else PositionType.EMPTY_SEAT

    override fun nextTypeForOccupiedSeat(layout: SeatLayout, x: Int, y: Int) =
        if (layout.countVisibleOccupiedSeats(x, y) >= 5) {
            PositionType.EMPTY_SEAT
        } else PositionType.OCCUPIED_SEAT
}

@ExperimentalStdlibApi
private fun performStepWithSeatRules(layout: SeatLayout, seatRules: SeatRules): SeatLayout {
    val nextLayout = layout.toMutableFieldMap()

    fun nextPositionType(x: Int, y: Int, type: PositionType): PositionType {
        return when (type) {
            PositionType.FLOOR -> PositionType.FLOOR
            PositionType.EMPTY_SEAT -> seatRules.nextTypeForEmptySeat(layout, x, y)
            PositionType.OCCUPIED_SEAT -> seatRules.nextTypeForOccupiedSeat(layout, x, y)
        }
    }

    layout.forEach { x, y, type ->
        nextLayout.setFieldType(x, y, nextPositionType(x, y, type))
    }
    return nextLayout
}

@ExperimentalStdlibApi
fun computeConvergedSeatLayout(layout: SeatLayout, maximumSteps: Int, seatRules: SeatRules): Pair<Int, SeatLayout>? {
    var previousLayout = layout
    repeat(maximumSteps) { index ->
        val nextLayout = performStepWithSeatRules(previousLayout, seatRules)
        if (previousLayout == nextLayout) {
            return (index + 1) to previousLayout
        }
        previousLayout = nextLayout
    }
    return null
}

@ExperimentalStdlibApi
fun performLayoutConvergence(layout: SeatLayout, seatRules: SeatRules) {
    val maximumSteps = 100
    val result = computeConvergedSeatLayout(layout, maximumSteps, seatRules)
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

    performLayoutConvergence(layout, AdjacentSeatRules)

    performLayoutConvergence(layout, VisibleSeatRules)
}