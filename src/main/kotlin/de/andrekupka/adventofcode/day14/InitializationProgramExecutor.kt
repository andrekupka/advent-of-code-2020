package de.andrekupka.adventofcode.day14

class SegmentationFaultException : RuntimeException()

interface ExecutionContext {
    val memorySpace: MemorySpace
    var currentMask: Bitmask?
}

interface ExecutionRules {
    fun execute(instruction: Instruction, context: ExecutionContext)
}

object InitializationProgramExecutor {

    fun execute(program: InitializationProgram, rules: ExecutionRules): MemorySpace {
        val memorySpace = MemorySpace()

        val state = ExecutionState(rules, program.instructions, memorySpace)

        while (!state.hasHalted) {
            state.executeNextInstruction()
        }

        return memorySpace
    }

    private class ExecutionState(
        private val rules: ExecutionRules,
        private val instructions: List<Instruction>,
        override var memorySpace: MemorySpace,
        private var instructionPointer: Int = 0,
        override var currentMask: Bitmask? = null
    ): ExecutionContext {
        var hasHalted: Boolean = false
            private set

        private val currentInstruction
            get() = if (instructionPointer in instructions.indices) {
                instructions[instructionPointer]
            } else throw SegmentationFaultException()

        fun executeNextInstruction() {
            rules.execute(currentInstruction, this)
            instructionPointer++
            hasHalted = instructionPointer == instructions.size
        }
    }
}