package de.andrekupka.adventofcode.day21

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken

val foodParser = object : Grammar<Food>() {
    val contains by literalToken("contains")
    val openParenthesis by literalToken("(")
    val closeParenthesis by literalToken(")")
    val comma by literalToken(",")

    val identifier by regexToken("[a-z]+")

    val whitespace by regexToken("\\s+", ignore = true)

    val ingredientsParser by oneOrMore(identifier) use { map { it.text }.toSet() }
    val allergensParser by -openParenthesis * -contains * separatedTerms(identifier, comma) * -closeParenthesis use {
        map { it.text }.toSet()
    }

    val foodParser by ingredientsParser * allergensParser use { Food(t1, t2) }

    override val rootParser get() = foodParser
}