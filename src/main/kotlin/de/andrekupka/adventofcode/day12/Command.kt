package de.andrekupka.adventofcode.day12

sealed class Command {
    abstract fun applyTo(ship: Ship): Ship

    abstract fun applyTo(shipWithWaypoint: ShipWithWaypoint): ShipWithWaypoint
}

data class MoveDirectionalCommand(
    private val movementDirection: MovementDirection,
    private val steps: Int = 1
): Command() {
    init {
        require(steps > 0)
    }

    override fun applyTo(ship: Ship) = ship.moveToDirection(movementDirection, steps)

    override fun applyTo(shipWithWaypoint: ShipWithWaypoint) = shipWithWaypoint.moveWaypointToDirection(movementDirection, steps)
}

data class MoveForwardCommand(
    private val steps: Int = 1
) : Command() {
    init {
        require(steps > 0)
    }

    override fun applyTo(ship: Ship) = ship.moveForward(steps)

    override fun applyTo(shipWithWaypoint: ShipWithWaypoint) = shipWithWaypoint.moveToWaypoint(steps)
}

data class TurnClockwiseCommand(
    private val steps: Int = 1
) : Command() {
    init {
        require(steps > 0)
    }

    override fun applyTo(ship: Ship) = (0 until steps).fold(ship) { currentShip, _ -> currentShip.turnClockwise() }

    override fun applyTo(shipWithWaypoint: ShipWithWaypoint) = (0 until steps).fold(shipWithWaypoint) {
        current, _ -> current.turnWaypointClockwise()
    }
}

data class TurnCounterClockwiseCommand(
    private val steps: Int = 1
) : Command() {
    init {
        require(steps > 0)
    }

    override fun applyTo(ship: Ship) = (0 until steps).fold(ship) { currentShip, _ -> currentShip.turnCounterClockwise() }

    override fun applyTo(shipWithWaypoint: ShipWithWaypoint) = (0 until steps).fold(shipWithWaypoint) {
            current, _ -> current.turnWaypointCounterClockwise()
    }
}
