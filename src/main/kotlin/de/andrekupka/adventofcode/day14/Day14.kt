package de.andrekupka.adventofcode.day14

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readFile

fun main(args: Array<String>) {
    val content = readFile(args[0])

    val initializationProgram = initializationProgramParser.parseToEnd(content)

    val memorySpace = InitializationProgramExecutor.execute(initializationProgram)

    val sumOfValues = memorySpace.nonZeroValues.values.sum()
    println("Sum of values after execution is $sumOfValues")
}