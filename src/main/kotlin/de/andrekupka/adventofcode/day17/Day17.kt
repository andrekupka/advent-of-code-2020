package de.andrekupka.adventofcode.day17

import de.andrekupka.adventofcode.utils.readLinesNotBlank

@ExperimentalStdlibApi
private fun performStep(state: EnergySourceState): EnergySourceState {
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
private fun performIterations(state: EnergySourceState, numberOfIterations: Int): EnergySourceState {
    var nextState = state
    repeat(numberOfIterations) {
        nextState = performStep(nextState)
    }
    return nextState
}

@ExperimentalStdlibApi
private fun performIterationsInDimension(twoDimensionalState: EnergySourceState, dimension: Int, numberOfIterations: Int) {
    val liftedState = twoDimensionalState.liftToDimension(dimension)
    val stateAfterwards = performIterations(liftedState, numberOfIterations)
    val numberOfActiveCells = stateAfterwards.count { _, active -> active }

    println("There are $numberOfActiveCells active cells after $numberOfIterations iterations in $dimension dimensions")
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val twoDimensionalState = EnergySourceState.fromLines(lines)

    performIterationsInDimension(twoDimensionalState, dimension = 3, numberOfIterations = 6)
    performIterationsInDimension(twoDimensionalState, dimension = 4, numberOfIterations = 6)
}