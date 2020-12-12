package de.andrekupka.adventofcode.day12

import de.andrekupka.adventofcode.utils.readLinesNotBlank
import kotlin.math.absoluteValue

fun Ship.manhattanDistanceFromOrigin() = location.x.absoluteValue + location.y.absoluteValue

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val commands = CommandParser.parseCommands(lines)

    val ship = Ship()

    val updatedShip = commands.fold(ship) { currentShip, command -> command.applyTo(currentShip) }
    println("Update ship is $updatedShip")
    println("Manhattan distance from origin is ${updatedShip.manhattanDistanceFromOrigin()}")
}