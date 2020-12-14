package de.andrekupka.adventofcode.day14

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken

val initializationProgramParser = object : Grammar<InitializationProgram>() {
    val mask by literalToken("mask")
    val mem by literalToken("mem")

    val openingBracket by literalToken("[")
    val closingBracket by literalToken("]")
    val equals by literalToken("=")

    val bitmask by regexToken("[01X]{36}")
    val number by regexToken("([1-9][0-9]*|0)")

    val whitespace by regexToken("\\s+", ignore = true)

    val bitmaskParser by bitmask use { Bitmask(text.map { it.toBitOperation() }) }

    val setMaskInstructionParser by -mask * -equals * bitmaskParser use {
        SetBitmaskInstruction(bitmask = this)
    }

    val numberParser by number use { text.toLong() }

    val writeMemoryInstructionParser by -mem * -openingBracket * numberParser * -closingBracket * -equals * numberParser use {
        WriteMemoryInstruction(address = t1, value = t2)
    }

    val instructionParser by setMaskInstructionParser or writeMemoryInstructionParser

    val programParser = zeroOrMore(instructionParser) use { InitializationProgram(instructions = this) }

    override val rootParser get() = programParser

    private fun Char.toBitOperation() = when(this) {
        'X' -> BitOperation.KEEP
        '1' -> BitOperation.ONE
        '0' -> BitOperation.ZERO
        else -> error("Unexpected character for bit operation: $this")
    }
}