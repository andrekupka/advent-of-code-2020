package de.andrekupka.adventofcode.day17

private fun List<Int>.valueRange() = (minOrNull() ?: 0)..(maxOrNull() ?: 0)

class DefaultEnergySourceState(
    activeCellCoordinates: Set<CellCoordinate>
) : MutableEnergySourceState {
    private val activeCellsCoordinates = activeCellCoordinates.toMutableSet()

    override val activeCells get() = activeCellsCoordinates.toSet()

    override val xIndexes get() = activeCells.map { it.x }.valueRange()
    override val yIndexes get() = activeCells.map { it.y }.valueRange()
    override val zIndexes get() = activeCells.map { it.z }.valueRange()

    override operator fun get(x: Int, y: Int, z: Int): Boolean = this[CellCoordinate(x, y, z)]

    override fun get(coordinate: CellCoordinate): Boolean = activeCellsCoordinates.contains(coordinate)

    override operator fun set(x: Int, y: Int, z: Int, active: Boolean) {
        this[CellCoordinate(x, y, z)] = active
    }

    override fun set(coordinate: CellCoordinate, active: Boolean) {
        if (active) {
            activeCellsCoordinates.add(coordinate)
        } else {
            activeCellsCoordinates.remove(coordinate)
        }
    }
}

interface EnergySourceState {
    val activeCells: Set<CellCoordinate>

    val xIndexes: IntRange
    val yIndexes: IntRange
    val zIndexes: IntRange

    operator fun get(x: Int, y: Int, z: Int): Boolean
    operator fun get(coordinate: CellCoordinate): Boolean

    companion object
}

interface MutableEnergySourceState : EnergySourceState {
    operator fun set(x: Int, y: Int, z: Int, active: Boolean)
    operator fun set(coordinate: CellCoordinate, active: Boolean)
}

fun EnergySourceState.toMutableEnergySourceState(): MutableEnergySourceState = copy()

fun MutableEnergySourceState.toEnergySourceState(): EnergySourceState = copy()

private fun EnergySourceState.copy() = DefaultEnergySourceState(activeCells)

fun EnergySourceState.Companion.fromLines(lines: List<String>): EnergySourceState = DefaultEnergySourceState(emptySet()).apply {
    lines.forEachIndexed { yIndex, row ->
        row.forEachIndexed { xIndex, char ->
            if (char == '#') {
                this[xIndex, yIndex, 0] = true
            }
        }
    }
}