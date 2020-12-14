package de.andrekupka.adventofcode.day14

enum class BitOperation {
    ONE,
    ZERO,
    KEEP
}

data class Bitmask(
    val operations: List<BitOperation>
) {
    val indexedOperationsWithEffect by lazy {
        operations
            .mapIndexed { index, operation -> operations.lastIndex - index to operation }
            .filter { it.second != BitOperation.KEEP }
    }
}

fun Bitmask.maskValue(value: Long) = indexedOperationsWithEffect.fold(value) { oldValue, (index, operation) ->
    when (operation) {
        BitOperation.ONE -> oldValue.setOneAtIndex(index)
        BitOperation.ZERO -> oldValue.setZeroAtIndex(index)
        BitOperation.KEEP -> oldValue
    }
}

fun Long.setOneAtIndex(index: Int): Long = this or (1L shl index)

fun Long.setZeroAtIndex(index: Int): Long = this and (1L shl index).inv()

sealed class Instruction

data class SetBitmaskInstruction(
    val bitmask: Bitmask
): Instruction()

data class WriteMemoryInstruction(
    val address: Long,
    val value: Long
) : Instruction()

data class InitializationProgram(
    val instructions: List<Instruction>
)