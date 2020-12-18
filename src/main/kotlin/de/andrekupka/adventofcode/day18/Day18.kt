package de.andrekupka.adventofcode.day18

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readLinesNotBlank

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val strangeSumOfResults = lines.map { samePrecedenceArithmeticExpressionParser.parseToEnd(it).evaluate() }.sum()

    println("Sum of results is $strangeSumOfResults")

    val sumOfResults = lines.map { additionBeforeMultiplicationArithmeticExpressionParser.parseToEnd(it).evaluate() }.sum()

    println("Sum of results is $sumOfResults")

    val arithmeticTokens = lines.map { arithmeticTokenizer.tokenize(it) }
    arithmeticTokens.forEach {
        println(it)
    }
}