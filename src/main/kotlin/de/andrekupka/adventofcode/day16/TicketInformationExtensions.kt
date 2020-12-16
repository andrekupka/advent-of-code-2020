package de.andrekupka.adventofcode.day16

fun TicketRule.acceptsValue(value: Int) = valueRanges.any { value in it }

fun Ticket.isValidAccordingTo(rules: List<TicketRule>) = values.all { value ->
    rules.any { rule -> rule.acceptsValue(value) }
}

val TicketInformation.validNearbyTickets get() = nearbyTickets.filter { it.isValidAccordingTo(rules) }

fun TicketInformation.ticketScanningErrorRate(): Int = nearbyTickets.flatMap { ticket ->
        ticket.values.filter { value ->
            !rules.any { rule ->
                rule.acceptsValue(value)
            }
        }
    }.sum()