package de.andrekupka.adventofcode.utils

fun groupAndFlattenNonBlankLines(lines: List<String>, combinator: (String, String) -> String): List<String> {
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
                    result[lastIndex] = combinator(result[lastIndex], line)
                }
            }
            return this
        }
    }

    return lines.fold(Accumulator()) { acc, line -> acc.accumulate(line) }.result
}