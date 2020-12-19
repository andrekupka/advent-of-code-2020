package de.andrekupka.adventofcode.day18

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readLinesNotBlank

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val samePrecedenceSumOfResults = lines.map { samePrecedenceArithmeticExpressionParser.parseToEnd(it).evaluate() }.sum()
    println("Same precedence sum of results is $samePrecedenceSumOfResults")

    val additionBeforeMultiplicationSumOfResults = lines.map { additionBeforeMultiplicationArithmeticExpressionParser.parseToEnd(it).evaluate() }.sum()
    println("Addition before multiplication sum of results is $additionBeforeMultiplicationSumOfResults")

    val handwrittenSamePrecedenceSumOfResults = lines.map { HandwrittenArithmeticExpressionParser.parse(it).evaluate() }.sum()
    println("Handwritten same precedence sum of results is $handwrittenSamePrecedenceSumOfResults")
}