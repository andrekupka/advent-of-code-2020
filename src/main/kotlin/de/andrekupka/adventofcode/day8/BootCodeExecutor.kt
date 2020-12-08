package de.andrekupka.adventofcode.day8

class SegmentationFaultException : RuntimeException()


interface ExecutionCallback {
    fun beforeInstruction(context: ExecutionContext): Boolean
}

private object NoOpExecutionCallback : ExecutionCallback {
    override fun beforeInstruction(context: ExecutionContext): Boolean = true
}

interface ExecutionContext {
    val instructionPointer: Int
    val instruction: Instruction
    val accumulator: Int
}

class BootCodeExecutor(
    private val executionCallback: ExecutionCallback = NoOpExecutionCallback
) {

    fun execute(bootCode: BootCode): Int {
        val state = ExecutionState(bootCode.instructions)

        val context = object : ExecutionContext {
            override val instructionPointer get() = state.instructionPointer
            override val instruction get() = state.currentInstruction
            override val accumulator get() = state.accumulator
        }

        while (!state.hasFinished) {
            val shouldContinue = executionCallback.beforeInstruction(context)
            if (!shouldContinue) {
                break
            }
            state.executeNextInstruction()
        }
        return state.accumulator
    }

    private class ExecutionState(
        private val instructions: List<Instruction>,
        var instructionPointer: Int = 0,
        var accumulator: Int = 0,
        var hasFinished: Boolean = false
    ) {
        val currentInstruction get() = if (instructionPointer in instructions.indices) {
            instructions[instructionPointer]
        } else throw SegmentationFaultException()

        fun executeNextInstruction() {
            execute(currentInstruction)
            instructionPointer++
            hasFinished = instructionPointer == instructions.size
        }

        private fun execute(instruction: Instruction) = when(instruction.type) {
            InstructionType.ACC -> {
                accumulator += instruction.argument
            }
            InstructionType.JMP -> {
                // subtract one because instruction pointer will be incremented afterwards
                instructionPointer += (instruction.argument - 1)
            }
            InstructionType.NOP -> Unit
        }
    }
}