package de.andrekupka.adventofcode.day8

class SecondInstructionHitAnalyzingExecutionCallback : ExecutionCallback {

    private var alreadyHitInstructionPointers = mutableSetOf<Int>()

    private var hadSecondHit: Boolean = false

    private var accumulatorBeforeSecondHit: Int = 0

    val accumulatorResult get() = if (hadSecondHit) accumulatorBeforeSecondHit else null

    override fun beforeInstruction(context: ExecutionContext): Boolean = context.run {
        if (instructionPointer in alreadyHitInstructionPointers) {
            hadSecondHit = true
            accumulatorBeforeSecondHit = accumulator
            return false
        }
        alreadyHitInstructionPointers.add(instructionPointer)
        return true
    }
}