package de.andrekupka.adventofcode.day12

data class Ship(
    val location: Location = Location(0, 0),
    val direction: MovementDirection = MovementDirection.EAST
) {

    fun turnClockwise() = copy(
        direction = direction.nextClockwise()
    )

    fun turnCounterClockwise() = copy(
        direction = direction.nextCounterClockwise()
    )

    fun moveForward(steps: Int = 1) = moveToDirection(direction, steps)

    fun moveToDirection(targetDirection: MovementDirection, steps: Int = 1) = copy(
        location = location + steps * targetDirection
    )

    override fun toString() = "Ship(location=$location,direction=$direction)"
}