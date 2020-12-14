package de.andrekupka.adventofcode.day14

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readFile

object ValueMaskingExecutionRules : ExecutionRules {
    override fun execute(instruction: Instruction, context: ExecutionContext) = with(context) {
        when (instruction) {
            is SetBitmaskInstruction -> {
                currentMask = instruction.bitmask
            }
            is WriteMemoryInstruction -> {
                memorySpace[instruction.address] = instruction.value.let { currentMask?.maskValue(it) ?: it }
            }
        }
    }
}

object AddressMaskingExecutionRules : ExecutionRules {
    override fun execute(instruction: Instruction, context: ExecutionContext) = with(context) {
        when (instruction) {
            is SetBitmaskInstruction -> {
                currentMask = instruction.bitmask
            }
            is WriteMemoryInstruction -> {
                val floatingAddress = currentMask?.maskValueFloating(instruction.address) ?: FloatingLong(instruction.address)
                floatingAddress.expand().forEach {
                    memorySpace[it] = instruction.value
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val content = readFile(args[0])

    val initializationProgram = initializationProgramParser.parseToEnd(content)

    val valueMaskingMemorySpace = InitializationProgramExecutor.execute(initializationProgram, ValueMaskingExecutionRules)

    val sumOfValuesWithValueMasking = valueMaskingMemorySpace.nonZeroValues.values.sum()
    println("Sum of values after execution with value masking rules is $sumOfValuesWithValueMasking")

    val addressMaskingMemorySpace = InitializationProgramExecutor.execute(initializationProgram, AddressMaskingExecutionRules)

    val sumOfValuesWithAddressMasking = addressMaskingMemorySpace.nonZeroValues.values.sum()
    println("Sum of values after execution with address masking rules is $sumOfValuesWithAddressMasking")
}