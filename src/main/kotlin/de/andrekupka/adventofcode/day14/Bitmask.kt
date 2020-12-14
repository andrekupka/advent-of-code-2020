package de.andrekupka.adventofcode.day14

import de.andrekupka.adventofcode.utils.setOneAtIndex
import de.andrekupka.adventofcode.utils.setZeroAtIndex

enum class BitOperation {
    ZERO,
    ONE,
    X
}

private fun List<*>.reversedIndex(index: Int) = lastIndex - index

data class Bitmask(
    val operations: List<BitOperation>
) {
    val indexedOperationsWithEffect by lazy {
        operations
            .mapIndexed { index, operation -> operations.reversedIndex(index) to operation }
            .filter { it.second != BitOperation.X }
    }
}

fun Bitmask.maskValue(value: Long) = indexedOperationsWithEffect.fold(value) { oldValue, (index, operation) ->
    when (operation) {
        BitOperation.ZERO -> oldValue.setZeroAtIndex(index)
        BitOperation.ONE -> oldValue.setOneAtIndex(index)
        BitOperation.X -> oldValue
    }
}

fun Bitmask.maskValueFloating(value: Long): FloatingLong =
    operations.foldIndexed(value to emptyList<Int>()) { index, (value, floatingIndexes), operation ->
        when (operation) {
            BitOperation.ZERO -> value to floatingIndexes
            BitOperation.ONE -> value.setOneAtIndex(operations.reversedIndex(index)) to floatingIndexes
            BitOperation.X -> value to (floatingIndexes + listOf(operations.reversedIndex(index)))
        }
    }.let { (value, floatingIndexes) -> FloatingLong(value, floatingIndexes) }
