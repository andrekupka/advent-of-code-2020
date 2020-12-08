package de.andrekupka.adventofcode.day8

class InfiniteLoopAnalyzingExecutionCallback : ExecutionCallback {

    private var alreadyHitInstructionPointers = mutableSetOf<Int>()

    private var hadInfiniteLoop: Boolean = false

    var infiniteLoopAccumulator: Int? = null
        private set

    var infiniteLoopInstructionPointer: Int? = null
        private set


    override fun beforeInstruction(context: ExecutionContext): Boolean = context.run {
        if (instructionPointer in alreadyHitInstructionPointers) {
            hadInfiniteLoop = true
            infiniteLoopAccumulator = accumulator
            infiniteLoopInstructionPointer = instructionPointer
            return false
        }
        alreadyHitInstructionPointers.add(instructionPointer)
        return true
    }
}