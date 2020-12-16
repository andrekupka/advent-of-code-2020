package de.andrekupka.adventofcode.day16

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken

val ticketInformationParser = object : Grammar<TicketInformation>() {
    val yourTicketIndicator by literalToken("your ticket:")
    val nearbyTicketsIndicator by literalToken("nearby tickets:")

    val orLiteral by literalToken("or")
    val minus by literalToken("-")
    val comma by literalToken(",")
    val colon by literalToken(":")

    val number by regexToken("([1-9][0-9]*|0)")

    val ruleName by regexToken("[a-zA-Z]+(\\s+[a-zA-Z]+)*")

    val whitespace by regexToken("\\s+", ignore = true)

    val ruleNameParser by ruleName use { text }

    val numberParser by number use { text.toInt() }

    val valueRangeParser by numberParser * -minus * numberParser use { t1..t2 }

    val ticketRuleParser by ruleNameParser * -colon * separatedTerms(valueRangeParser, orLiteral) use {
        TicketRule(t1, t2)
    }

    val ticketParser by separatedTerms(numberParser, comma) use { Ticket(this) }

    val ticketInformationParser = oneOrMore(ticketRuleParser) *
            -yourTicketIndicator * ticketParser *
            -nearbyTicketsIndicator * oneOrMore(ticketParser) use {
        TicketInformation(t1, t2, t3)
    }

    override val rootParser get() = ticketInformationParser
}
