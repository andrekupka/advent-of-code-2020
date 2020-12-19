package de.andrekupka.adventofcode.day19

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken

val messageRuleParser = object : Grammar<MessageRuleNode>() {
    val pipe by literalToken("|")
    val colon by literalToken(":")
    val doubleQuote by literalToken("\"")

    val character by regexToken("[a-z]")
    val numberLiteral by regexToken("([1-9][0-9]*|0)")

    val whitespace by regexToken("\\s+", ignore = true)

    val idParser by numberLiteral use { text.toInt() }

    val quotedCharacterParser by -doubleQuote * character * -doubleQuote use { text[0] }

    val ruleIdentifierPrefixParser by idParser * -colon

    val terminalRuleParser by ruleIdentifierPrefixParser * quotedCharacterParser use { TerminalMessageRuleNode(t1, t2) }

    val idSequenceParser by oneOrMore(idParser)

    val alternativeSequenceRuleParser by ruleIdentifierPrefixParser * separatedTerms(idSequenceParser, pipe) use {
        AlternativeSequenceMessageRuleNode(t1, t2)
    }

    val ruleParser by terminalRuleParser or alternativeSequenceRuleParser

    override val rootParser get() = ruleParser
}