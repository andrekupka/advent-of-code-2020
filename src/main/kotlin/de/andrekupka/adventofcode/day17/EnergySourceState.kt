package de.andrekupka.adventofcode.day17

private fun List<Int>.valueRange() = (minOrNull() ?: 0)..(maxOrNull() ?: 0)


interface EnergySourceState {
    val activeCells: Set<Coordinate>
    val dimension: Int

    val indicesOfDimensions: List<IntRange>

    operator fun get(coordinate: Coordinate): Boolean

    companion object
}

interface MutableEnergySourceState : EnergySourceState {
    operator fun set(coordinate: Coordinate, active: Boolean)
}

class DefaultEnergySourceState(
    activeCellCoordinates: Set<Coordinate>,
    override val dimension: Int
) : MutableEnergySourceState {
    init {
        require(activeCellCoordinates.all { it.dimension == dimension })
    }

    private val activeCellsCoordinates = activeCellCoordinates.toMutableSet()

    override val activeCells get() = activeCellsCoordinates.toSet()

    override val indicesOfDimensions
        get() = (0 until dimension).map { dimensionIndex ->
            activeCells.map { it.values[dimensionIndex] }.valueRange()
        }

    override fun get(coordinate: Coordinate): Boolean = activeCellsCoordinates.contains(coordinate)

    override fun set(coordinate: Coordinate, active: Boolean) {
        require(coordinate.dimension == dimension)
        if (active) {
            activeCellsCoordinates.add(coordinate)
        } else {
            activeCellsCoordinates.remove(coordinate)
        }
    }
}

fun EnergySourceState.toMutableEnergySourceState(): MutableEnergySourceState = copy()

fun MutableEnergySourceState.toEnergySourceState(): EnergySourceState = copy()

private fun EnergySourceState.copy() = DefaultEnergySourceState(activeCells, dimension)

@ExperimentalStdlibApi
fun EnergySourceState.Companion.fromLines(
    lines: List<String>
): EnergySourceState = DefaultEnergySourceState(emptySet(), 2).apply {
    lines.forEachIndexed { yIndex, row ->
        row.forEachIndexed { xIndex, char ->
            if (char == '#') {
                val coordinate = Coordinate(listOf(xIndex, yIndex))
                this[coordinate] = true
            }
        }
    }
}

@ExperimentalStdlibApi
fun EnergySourceState.liftToDimension(targetDimension: Int): EnergySourceState {
    require(targetDimension >= dimension) {
        "Target dimension must be greater or equal to source dimension"
    }
    return if (targetDimension == dimension) {
        this
    } else {
        val activeCellsCoordinates = activeCells.map { it.liftToDimension(targetDimension) }.toSet()
        DefaultEnergySourceState(activeCellsCoordinates, targetDimension)
    }
}