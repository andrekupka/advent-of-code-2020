package de.andrekupka.adventofcode.day4

import de.andrekupka.adventofcode.utils.groupAndFlattenNonBlankLines

class PassportParsingException(message: String) : RuntimeException(message)

object PassportParser {

    fun parseFromLines(lines: List<String>): List<Passport> =
        groupAndFlattenNonBlankLines(lines) {
                first, second -> "$first $second"
        }.map { parsePassport(it) }

    fun parsePassport(representation: String) = representation.split("\\s+".toRegex()).associate {
        val parts = it.split(":", limit = 2)
        val typePart = parts[0]
        val valuePart = parts[1]
        val type = EntryType.fromString(parts[0]) ?: throw PassportParsingException("Invalid entry type: $typePart")
        type to valuePart
    }.let { Passport(representation, it) }
}