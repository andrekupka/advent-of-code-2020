package de.andrekupka.adventofcode.day12

data class ShipWithWaypoint(
    val location: Location = Location(0, 0),
    val waypoint: Location = Location(10, 1)
) {

    fun moveWaypointToDirection(direction: MovementDirection, steps: Int) = copy(
        waypoint = waypoint + steps * direction
    )

    fun turnWaypointClockwise() = copy(
        waypoint = waypoint.turnClockwiseAroundOrigin()
    )

    fun turnWaypointCounterClockwise() = copy(
        waypoint = waypoint.turnCounterClockwiseAroundOrigin()
    )

    fun moveToWaypoint(steps: Int = 1) = copy(
        location = location + steps * waypoint.toVector()
    )

    override fun toString() = "Ship(location=$location,waypoint=$waypoint)"
}