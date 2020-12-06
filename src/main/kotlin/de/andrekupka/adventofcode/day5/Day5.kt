package de.andrekupka.adventofcode.day5

import de.andrekupka.adventofcode.utils.readLines

class BinarySearchException(message: String) : RuntimeException(message)

fun <T> binarySearch(input: List<T>, bottom: T, top: T, start: Int = 0, end: Int = 127): Int {
    val range = input.fold(start..end) { acc, value ->
        acc.run {
            val middle = first + (last - first) / 2
            when (value) {
                bottom -> first..middle
                top -> middle + 1..last
                else -> throw BinarySearchException("Invalid input element: $value")
            }
        }
    }
    if (range.first != range.last) {
        throw BinarySearchException("Range did not converge to a single element: $range")
    }
    return range.first
}

data class Seat(
    val row: Int,
    val column: Int
)

val Seat.id get() = row * 8 + column

fun parseSeat(input: String): Seat {
    val rowPart = input.take(7)
    val row = binarySearch(rowPart.toList(), 'F', 'B', 0, 127)

    val columnPart = input.takeLast(3)
    val column = binarySearch(columnPart.toList(), 'L', 'R', 0, 7)

    return Seat(row, column)
}

fun main(args: Array<String>) {
    val lines = readLines(args[0])

    val seats = lines.map { parseSeat(it) }

    val existingIds = seats.map { it.id }
    val maximumId = existingIds.maxByOrNull { it }!!
    println("Maximum seat id is $maximumId")

    val minimumId = existingIds.minByOrNull { it }!!

    val missingIds = (minimumId..maximumId).toSet() - existingIds
    val myId = missingIds.single()
    println("My id is $myId")
}