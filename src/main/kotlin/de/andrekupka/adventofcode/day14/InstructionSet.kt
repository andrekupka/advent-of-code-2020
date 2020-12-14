package de.andrekupka.adventofcode.day14

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