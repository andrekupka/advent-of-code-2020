package de.andrekupka.adventofcode.day16

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readFile

@ExperimentalStdlibApi
fun determineSortedTicketRules(rules: List<TicketRule>, validTickets: List<Ticket>): List<TicketRule> {
    val valuesSortedByIndex = rules.indices.map { index ->
        validTickets.map { it.values[index] }.toSet()
    }

    val rulesWithMatchingIndexes = rules.associateWith { rule ->
        valuesSortedByIndex.withIndex().filter { (_, values) ->
            values.all { value -> rule.acceptsValue(value) }
        }.map { it.index }
    }.toList().sortedBy { it.second.size }

    val ticketRulesWithIndex = buildList<Pair<TicketRule, Int>> {
        rulesWithMatchingIndexes.forEach { (rule, indexes) ->
            val uniqueIndex = indexes - this.map { it.second }.toSet()
            add(rule to uniqueIndex.single())
        }
    }
    return ticketRulesWithIndex.sortedBy { it.second }.map { it.first }
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val content = readFile(args[0])

    val ticketInformation = ticketInformationParser.parseToEnd(content)
    val ticketScanningErrorRate = ticketInformation.ticketScanningErrorRate()

    println("Ticket scanning error rate is $ticketScanningErrorRate")

    val validTickets = ticketInformation.validNearbyTickets

    val numberOfRules = ticketInformation.rules.size
    val numberOfValuesSet = validTickets.map { it.values.size }.toSet()
    if (numberOfValuesSet.size != 1) {
        println("Nearby tickets don't have the same number of value")
        return
    }

    if (numberOfRules != numberOfValuesSet.single()) {
        println("Tickets have a different number of values than existing rules")
        return
    }

    val sortedTicketRules = determineSortedTicketRules(ticketInformation.rules, validTickets)
    println(sortedTicketRules)

    val multipliedDepartureValues = sortedTicketRules.withIndex().filter { it.value.name.startsWith("departure") }.map {
        ticketInformation.yourTicket.values[it.index].toLong()
    }.fold(1L) { a, b -> a * b }

    println("Ticket departure values product is $multipliedDepartureValues")
}