package de.andrekupka.adventofcode.day4

private fun groupAndFlattenNonBlankLines(lines: List<String>): List<String> {
    class Accumulator {
        val result = mutableListOf<String>()
        private var startNewElement = true

        fun accumulate(line: String): Accumulator {
            if (line.isBlank()) {
                startNewElement = true
            } else {
                if (startNewElement) {
                    result.add(line)
                    startNewElement = false
                } else {
                    val lastIndex = result.size - 1
                    result[lastIndex] = result[lastIndex] + " " + line
                }
            }
            return this
        }
    }

    return lines.fold(Accumulator()) { acc, line -> acc.accumulate(line) }.result
}

class PassportParsingException(message: String) : RuntimeException(message)

object PassportParser {

    fun parseFromLines(lines: List<String>): List<Passport> =
        groupAndFlattenNonBlankLines(lines).map { parsePassport(it) }

    fun parsePassport(representation: String) = representation.split("\\s+".toRegex()).associate {
        val parts = it.split(":", limit = 2)
        val typePart = parts[0]
        val valuePart = parts[1]
        val type = EntryType.fromString(parts[0]) ?: throw PassportParsingException("Invalid entry type: $typePart")
        type to valuePart
    }.let { Passport(representation, it) }
}