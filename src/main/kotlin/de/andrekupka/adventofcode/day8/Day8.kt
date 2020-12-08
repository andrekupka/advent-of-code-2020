package de.andrekupka.adventofcode.day8

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readFile


fun main(args: Array<String>) {
    val content = readFile(args[0])

    val bootCode = bootCodeParser.parseToEnd(content)
    bootCode.instructions.forEach { println(it) }

    val callback = InfiniteLoopAnalyzingExecutionCallback()
    BootCodeExecutor().execute(bootCode, callback)
    println("Accumulator before second instruction hit was ${callback.infiniteLoopAccumulator}")

    val executedJmpsOrNops = callback.executedInstructions.filterValues { it.type == InstructionType.NOP || it.type == InstructionType.JMP }

    val result = executeAndResolveInfiniteLoop(bootCode, executedJmpsOrNops.keys)
    println("Accumulator after resolving infinite loop is $result")
}