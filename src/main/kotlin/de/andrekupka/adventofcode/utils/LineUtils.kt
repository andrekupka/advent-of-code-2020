package de.andrekupka.adventofcode.utils

fun groupByBlankLines(lines: List<String>): List<List<String>> {
    class Accumulator {
        val result = mutableListOf<List<String>>()
        lateinit var currentGroup: MutableList<String>

        private var startNewGroup = true

        fun accumulate(line: String): Accumulator {
            if (line.isBlank()) {
                startNewGroup = true
            } else {
                if (startNewGroup) {
                    startNewGroup = false
                    currentGroup = mutableListOf(line).also { result.add(it) }
                } else {
                    currentGroup.add(line)
                }
            }
            return this
        }
    }

    return lines.fold(Accumulator()) { acc, line -> acc.accumulate(line) }.result.toList()
}

fun groupAndReduceByNonBlankLines(lines: List<String>, operation: (String, String) -> String): List<String> =
    groupByBlankLines(lines).map { it.reduce(operation) }

fun <T> groupAndFoldByNonBlankLines(lines: List<String>, initial: () -> T, operation: (T, String) -> T): List<T> =
    groupByBlankLines(lines).map { it.fold(initial(), operation) }