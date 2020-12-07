package de.andrekupka.adventofcode.day7

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken

data class BagCount(
    val count: Int,
    val color: String
)

data class LuggageRule(
    val color: String,
    val bags: List<BagCount>
)

val LuggageParser = object : Grammar<LuggageRule>() {
    val contain by literalToken("contain")
    val no by literalToken("no")
    val other by literalToken("other")
    val dot by literalToken(".")
    val comma by literalToken(",")

    val bag by regexToken("bags|bag")
    val number by regexToken("[1-9]+[0-9]*")
    val identifier by regexToken("[a-z]+")

    val whitespace by regexToken("\\s+", ignore = true)

    val numberParser by number use { text.toInt() }

    val colorParser = (1..2 times identifier) use {
        joinToString(" ") { it.text }
    }

    val bagCountParser by numberParser * colorParser * -bag use {
        BagCount(t1, t2)
    }

    val multipleBagCountParser by separatedTerms(bagCountParser, comma)
    val noBagCountParser by -no * -other * bag use { emptyList<BagCount>() }

    val bagCountAlternativeParser by multipleBagCountParser or noBagCountParser

    val startParser by colorParser * -bag * -contain

    val ruleParser = startParser * bagCountAlternativeParser * -dot use {
        LuggageRule(t1, t2)
    }

    override val rootParser get() = ruleParser
}