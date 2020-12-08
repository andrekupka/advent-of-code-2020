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
    val acc by literalToken("acc") use { InstructionType.ACC }
    val jmp by literalToken("jmp") use { InstructionType.JMP }
    val nop by literalToken("nop") use { InstructionType.NOP }

    val whitespaces by regexToken("\\s+", ignore = true)

    val instructionType by acc or jmp or nop

    val argument by regexToken("[+\\-]([0-9]|[1-9][0-9]+)") use { text.toLong() }

    val instruction by instructionType and argument use { Instruction(t1, t2) }

    val code by 0 timesOrMore instruction use { BootCode(this) }

    override val rootParser get() = code
}