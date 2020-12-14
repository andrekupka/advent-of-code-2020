package de.andrekupka.adventofcode.day14

import de.andrekupka.adventofcode.utils.floatAtIndex

data class FloatingLong(
    val value: Long,
    val floatingBitIndexes: List<Int> = emptyList()
)

fun FloatingLong.expand() = floatingBitIndexes.fold(listOf(value)) { values, floatingIndex ->
    values.flatMap { it.floatAtIndex(floatingIndex) }
}
