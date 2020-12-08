package de.andrekupka.adventofcode.day8

class SegmentationFaultException : RuntimeException()


interface ExecutionCallback {
    fun beforeInstruction(context: ExecutionContext): Boolean
}

private object NoOpExecutionCallback : ExecutionCallback {
    override fun beforeInstruction(context: ExecutionContext): Boolean = true
}

interface ExecutionContext {
    val instructions: List<Instruction>
    val instructionPointer: Int
    val instruction: Instruction
    val accumulator: Int
}

sealed class ExecutionResult

data class Interrupted(val intermediateAccumulator: Int) : ExecutionResult()

data class Halted(val finalAccumulator: Int): ExecutionResult()

class BootCodeExecutor {

    fun execute(bootCode: BootCode, callback: ExecutionCallback = NoOpExecutionCallback): ExecutionResult {
        val state = ExecutionState(bootCode.instructions)

        val context = object : ExecutionContext {
            override val instructions get() = bootCode.instructions
            override val instructionPointer get() = state.instructionPointer
            override val instruction get() = state.currentInstruction
            override val accumulator get() = state.accumulator
        }

        while (!state.hasHalted) {
            val shouldInterrupt = !callback.beforeInstruction(context)
            if (shouldInterrupt) {
                return Interrupted(state.accumulator)
            }
            state.executeNextInstruction()
        }
        return Halted(state.accumulator)
    }

    private class ExecutionState(
        private val instructions: List<Instruction>,
        var instructionPointer: Int = 0,
        var accumulator: Int = 0,
        var hasHalted: Boolean = false
    ) {
        val currentInstruction get() = if (instructionPointer in instructions.indices) {
            instructions[instructionPointer]
        } else throw SegmentationFaultException()

        fun executeNextInstruction() {
            execute(currentInstruction)
            instructionPointer++
            hasHalted = instructionPointer == instructions.size
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