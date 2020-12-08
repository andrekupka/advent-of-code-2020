package de.andrekupka.adventofcode.day8

class InfiniteLoopResolvingException : RuntimeException()

private fun Instruction.swapJmpOrNop() = when(type) {
    InstructionType.JMP -> Instruction(InstructionType.NOP, argument)
    InstructionType.NOP -> Instruction(InstructionType.JMP, argument)
    else -> error("Cannot swap $type")
}

private fun BootCode.exchangeJmpOrNop(instructionPointerToExchange: Int) = BootCode(
    instructions = instructions.mapIndexed { index, instruction ->
        if (index == instructionPointerToExchange) {
            instruction.swapJmpOrNop()
        } else instruction
    }
)

private fun executeWithExchangedInstruction(bootCode: BootCode, instructionPointerToExchange: Int): ExecutionResult {
    val patchedCode = bootCode.exchangeJmpOrNop(instructionPointerToExchange)
    return BootCodeExecutor().execute(patchedCode, InfiniteLoopAnalyzingExecutionCallback())
}

fun executeAndResolveInfiniteLoop(bootCode: BootCode, executedJmpOrNopPointers: Set<Int>): Int {
    val resultWithoutInfiniteLoop = executedJmpOrNopPointers.sortedDescending().asSequence().map {
        executeWithExchangedInstruction(bootCode, it)
    }.find { it is Halted } as Halted?
    return resultWithoutInfiniteLoop?.finalAccumulator ?: throw InfiniteLoopResolvingException()
}