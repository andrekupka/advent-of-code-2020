package de.andrekupka.adventofcode.day8

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readFile

fun main(args: Array<String>) {
    val content = readFile(args[0])

    val bootCode = bootCodeParser.parseToEnd(content)
    bootCode.instructions.forEach { println(it) }

    val callback = SecondInstructionHitAnalyzingExecutionCallback()
    BootCodeExecutor(callback).execute(bootCode)
    val result = callback.accumulatorResult
    println("Accumulator before second instruction hit was $result")
}