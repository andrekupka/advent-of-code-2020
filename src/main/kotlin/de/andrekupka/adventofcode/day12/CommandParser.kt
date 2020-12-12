package de.andrekupka.adventofcode.day12

class CommandParsingException(message: String) : RuntimeException(message)

object CommandParser {

    fun parseCommands(lines: List<String>) = lines.map { parseCommand(it) }

    fun parseCommand(line: String): Command {
        if (line.length < 2) {
            throw CommandParsingException("Command must be at least 2 characters long")
        }

        val action = line[0]
        val parameter = line.substring(1).toIntOrNull() ?: throw CommandParsingException("Command parameter must be a integer")
        if (parameter <= 0) {
            throw CommandParsingException("Parameter must be a positive integer")
        }

        return when (action) {
            'N', 'E', 'S', 'W' -> MoveDirectionalCommand(action.toMovementDirection(), parameter)
            'F' -> MoveForwardCommand(parameter)
            'R' -> TurnClockwiseCommand(parameter.toDegreeSteps())
            'L' -> TurnCounterClockwiseCommand(parameter.toDegreeSteps())
            else -> throw CommandParsingException("$action is not a valid command")
        }
    }

    private fun Int.toDegreeSteps(): Int {
        if (this != 90 && this != 180 && this != 270) {
            throw CommandParsingException("$this is no valid degree parameter, must be 90, 180 or 270")
        }
        return this / 90
    }

    private fun Char.toMovementDirection() = when(this) {
        'N' -> MovementDirection.NORTH
        'E' -> MovementDirection.EAST
        'S' -> MovementDirection.SOUTH
        'W' -> MovementDirection.WEST
        else -> error("Called toMovementDirection() without checking input")
    }
}