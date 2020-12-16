package de.andrekupka.adventofcode.day16

fun TicketRule.acceptsValue(value: Int) = valueRanges.any { value in it }

fun TicketInformation.ticketScanningErrorRate(): Int = nearbyTickets.flatMap { ticket ->
        ticket.values.filter { value ->
            !rules.any { rule ->
                rule.acceptsValue(value)
            }
        }
    }.sum()