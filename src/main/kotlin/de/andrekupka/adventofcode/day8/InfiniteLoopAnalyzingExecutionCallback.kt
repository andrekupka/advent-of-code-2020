package de.andrekupka.adventofcode.day8

class InfiniteLoopAnalyzingExecutionCallback : ExecutionCallback {

    private val visitedInstructions = mutableMapOf<Int, Instruction>()

    private var hadInfiniteLoop: Boolean = false

    var infiniteLoopAccumulator: Int? = null
        private set

    var infiniteLoopInstructionPointer: Int? = null
        private set

    val executedInstructions: Map<Int, Instruction> get() = visitedInstructions

    override fun beforeInstruction(context: ExecutionContext): Boolean = context.run {
        if (instructionPointer in visitedInstructions) {
            hadInfiniteLoop = true
            infiniteLoopAccumulator = accumulator
            infiniteLoopInstructionPointer = instructionPointer
            return false
        }
        visitedInstructions[instructionPointer] = instruction
        return true
    }
}