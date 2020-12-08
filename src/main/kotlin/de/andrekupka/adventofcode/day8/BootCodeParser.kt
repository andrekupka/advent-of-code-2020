package de.andrekupka.adventofcode.day8

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken

enum class InstructionType {
    ACC,
    JMP,
    NOP
}

data class Instruction(
    val type: InstructionType,
    val argument: Long
)

data class BootCode(
    val instructions: List<Instruction>
)

val bootCodeParser = object : Grammar<BootCode>() {
    val acc by literalToken("acc")
    val jmp by literalToken("jmp")
    val nop by literalToken("nop")

    val number by regexToken("[+\\-]([1-9][0-9]+|[0-9])")

    val whitespaces by regexToken("\\s+", ignore = true)

    val accParser by acc use { InstructionType.ACC }
    val jmpParser by jmp use { InstructionType.JMP }
    val nopParser by nop use { InstructionType.NOP }

    val instructionType by accParser  or jmpParser or nopParser

    val numberArgument by number use { text.toLong() }

    val instruction by instructionType and numberArgument use { Instruction(t1, t2) }

    val code by zeroOrMore(instruction) use { BootCode(this) }

    override val rootParser get() = code
}