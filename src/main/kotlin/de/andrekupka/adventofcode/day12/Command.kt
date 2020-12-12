package de.andrekupka.adventofcode.day12

sealed class Command {
    abstract fun applyTo(ship: Ship): Ship
}

data class MoveDirectionalCommand(
    private val movementDirection: MovementDirection,
    private val steps: Int = 1
): Command() {
    init {
        require(steps > 0)
    }

    override fun applyTo(ship: Ship) = ship.moveToDirection(movementDirection, steps)
}

data class MoveForwardCommand(
    private val steps: Int = 1
) : Command() {
    init {
        require(steps > 0)
    }

    override fun applyTo(ship: Ship) = ship.moveForward(steps)
}

data class TurnClockwiseCommand(
    private val steps: Int = 1
) : Command() {
    init {
        require(steps > 0)
    }

    override fun applyTo(ship: Ship) = (0 until steps).fold(ship) { currentShip, _ -> currentShip.turnClockwise() }
}

data class TurnCounterClockwiseCommand(
    private val steps: Int = 1
) : Command() {
    init {
        require(steps > 0)
    }

    override fun applyTo(ship: Ship) = (0 until steps).fold(ship) { currentShip, _ -> currentShip.turnCounterClockwise() }
}
