package de.andrekupka.adventofcode.day16

data class TicketRule(
    val name: String,
    val valueRanges: List<IntRange>
)

data class Ticket(
    val values: List<Int>
)

data class TicketInformation(
    val rules: List<TicketRule>,
    val yourTicket: Ticket,
    val nearbyTickets: List<Ticket>
)