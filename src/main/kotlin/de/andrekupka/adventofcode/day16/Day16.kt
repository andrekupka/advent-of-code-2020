package de.andrekupka.adventofcode.day16

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readFile

fun main(args: Array<String>) {
    val content = readFile(args[0])

    val ticketInformation = ticketInformationParser.parseToEnd(content)
    val ticketScanningErrorRate = ticketInformation.ticketScanningErrorRate()

    println("Ticket scanning error rate is $ticketScanningErrorRate")
}