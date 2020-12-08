package de.andrekupka.adventofcode.day8

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readFile

fun main(args: Array<String>) {
    //val content = readFile(args[0])
    val content = "acc 10"

    val bootCode = bootCodeParser.parseToEnd(content)
    bootCode.instructions.forEach {
        println(it)
    }
}