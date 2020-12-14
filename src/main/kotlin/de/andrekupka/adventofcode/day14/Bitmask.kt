package de.andrekupka.adventofcode.day14

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
    operations.foldIndexed(value to emptyList<Int>()) { index, (value, floatingIndices), operation ->
        when (operation) {
            BitOperation.ZERO -> value to floatingIndices
            BitOperation.ONE -> value.setOneAtIndex(operations.reversedIndex(index)) to floatingIndices
            BitOperation.X -> value to (floatingIndices + listOf(operations.reversedIndex(index)))
        }
    }.let { (value, floatingIndices) -> FloatingLong(value, floatingIndices) }

fun Long.setOneAtIndex(index: Int): Long = this or (1L shl index)

fun Long.setZeroAtIndex(index: Int): Long = this and (1L shl index).inv()
