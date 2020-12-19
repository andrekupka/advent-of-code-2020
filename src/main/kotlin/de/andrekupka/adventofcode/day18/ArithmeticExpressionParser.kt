package de.andrekupka.adventofcode.day18

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser

abstract class AbstractArithmeticExpressionParser() : Grammar<ArithmeticExpression>() {
    val plus by literalToken("+")
    val times by literalToken("*")

    val openParenthesis by literalToken("(")
    val closeParenthesis by literalToken(")")

    val numberLiteral by regexToken("([1-9][0-9]*|0)")

    val whitespace by regexToken("\\s+", ignore = true)

    val plusParser by plus use { TermOperator.PLUS }
    val timesParser by times use { TermOperator.TIMES }

    val numberParser by numberLiteral use { NumberLiteralExpression(text.toLong()) }

    val parenthesizedExpressionParser: Parser<ArithmeticExpression> by -openParenthesis * parser { rootParser } * -closeParenthesis

    val termParser by numberParser or parenthesizedExpressionParser
}

val samePrecedenceArithmeticExpressionParser = object : AbstractArithmeticExpressionParser() {
    val operationParser by plusParser or timesParser

    val termChainParser by leftAssociative(termParser, operationParser) { left, operation, right -> TermExpression(operation, left, right )}

    override val rootParser get() = termChainParser
}

val additionBeforeMultiplicationArithmeticExpressionParser = object : AbstractArithmeticExpressionParser() {
    val sumChainParser by leftAssociative(termParser, plusParser) { left, operation, right -> TermExpression(operation, left, right) }

    val productChainParser by leftAssociative(sumChainParser, timesParser) { left, operation, right -> TermExpression(operation, left, right) }

    override val rootParser get() = productChainParser
}