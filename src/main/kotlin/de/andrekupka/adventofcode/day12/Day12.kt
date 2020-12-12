package de.andrekupka.adventofcode.day12

import de.andrekupka.adventofcode.utils.readLinesNotBlank

fun Ship.manhattanDistanceFromOrigin() = location.manhattanDistance()

fun ShipWithWaypoint.manhattanDistanceFromOrigin() = location.manhattanDistance()

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val commands = CommandParser.parseCommands(lines)

    val ship = commands.fold(Ship()) { current, command -> command.applyTo(current) }
    println("Update ship is $ship")
    println("Manhattan distance from origin is ${ship.manhattanDistanceFromOrigin()}")

    val shipWithWaypoint = commands.fold(ShipWithWaypoint()) { current, command -> command.applyTo(current) }
    println("Updated ship with waypoint $shipWithWaypoint")
    println("Manhattan distance from origin is ${shipWithWaypoint.manhattanDistanceFromOrigin()}")
}
