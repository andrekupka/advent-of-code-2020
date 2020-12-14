package de.andrekupka.adventofcode.day14

class SegmentationFaultException : RuntimeException()

object InitializationProgramExecutor {

    fun execute(program: InitializationProgram): MemorySpace {
        val memorySpace = MemorySpace()

        val state = ExecutionState(program.instructions, memorySpace)

        while (!state.hasHalted) {
            state.executeNextInstruction()
        }

        return memorySpace
    }

    private class ExecutionState(
        private val instructions: List<Instruction>,
        private var memorySpace: MemorySpace,
        private var instructionPointer: Int = 0,
        private var currentMask: Bitmask? = null
    ) {
        var hasHalted: Boolean = false
            private set

        private val currentInstruction
            get() = if (instructionPointer in instructions.indices) {
                instructions[instructionPointer]
            } else throw SegmentationFaultException()

        fun executeNextInstruction() {
            execute(currentInstruction)
            instructionPointer++
            hasHalted = instructionPointer == instructions.size
        }

        private fun execute(instruction: Instruction): Unit = when (instruction) {
            is SetBitmaskInstruction -> {
                currentMask = instruction.bitmask
            }
            is WriteMemoryInstruction -> {
                memorySpace[instruction.address] = instruction.value.let { currentMask?.maskValue(it) ?: it }
            }
        }
    }
}