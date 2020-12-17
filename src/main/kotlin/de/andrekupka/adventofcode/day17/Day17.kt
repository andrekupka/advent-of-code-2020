package de.andrekupka.adventofcode.day17

import de.andrekupka.adventofcode.utils.readLinesNotBlank

@ExperimentalStdlibApi
fun performStep(state: EnergySourceState): EnergySourceState {
    val nextState = state.toMutableEnergySourceState()

    state.expandedForEach { coordinate, active ->
        val activeCount = coordinate.adjacentCoordinates().count { state[it] }
        nextState[coordinate] = if (active && activeCount !in 2..3) {
            false
        } else if (!active && activeCount == 3) {
            true
        } else active
    }

    return nextState
}

@ExperimentalStdlibApi
fun performIterations(state: EnergySourceState, numberOfIterations: Int): EnergySourceState {
    var nextState = state
    repeat(numberOfIterations) {
        nextState = performStep(nextState)
    }
    return nextState
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val initialState = EnergySourceState.fromLines(lines)

    val numberOfIterations = 6
    val stateAfterwards = performIterations(initialState, numberOfIterations)
    val numberOfActiveCells = stateAfterwards.count { _, active -> active }

    println("There are $numberOfActiveCells active cells after $numberOfIterations iterations")
}