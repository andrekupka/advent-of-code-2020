package de.andrekupka.adventofcode.utils

fun groupByNonBlankLines(lines: List<String>): List<List<String>> {
    class Accumulator {
        val result = mutableListOf<List<String>>()
        lateinit var currentGroup: MutableList<String>

        private var startNewElement = true

        fun accumulate(line: String): Accumulator {
            if (startNewElement) {
                startNewElement = false
                currentGroup = mutableListOf()
                result.add(currentGroup)
            }
            if (line.isBlank()) {
                startNewElement = true
            } else {
                currentGroup.add(line)
            }
            return this
        }
    }

    return lines.fold(Accumulator()) { acc, line -> acc.accumulate(line) }.result.toList()
}

fun groupAndReduceByNonBlankLines(lines: List<String>, operation: (String, String) -> String): List<String> =
    groupByNonBlankLines(lines).map { it.reduce(operation) }

fun <T> groupAndFoldByNonBlankLines(lines: List<String>, initial: () -> T, operation: (T, String) -> T): List<T> =
    groupByNonBlankLines(lines).map { it.fold(initial(), operation) }