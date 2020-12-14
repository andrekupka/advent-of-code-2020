package de.andrekupka.adventofcode.day14

data class FloatingLong(
    val value: Long,
    val floatingBitIndices: List<Int> = emptyList()
)

fun FloatingLong.expand(): Sequence<Long> = generateSequence(value) { null }
