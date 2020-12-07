package de.andrekupka.adventofcode.day7

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readLinesNotBlank

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    lines.forEach {
        val rule = LuggageParser.parseToEnd(it)
        println(rule)
    }
}