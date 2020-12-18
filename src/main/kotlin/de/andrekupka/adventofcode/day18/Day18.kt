package de.andrekupka.adventofcode.day18

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readLinesNotBlank

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val sumOfResults = lines.map { arithmeticExpressionParser.parseToEnd(it).evaluate() }.sum()

    println("Sum of results is $sumOfResults")
}