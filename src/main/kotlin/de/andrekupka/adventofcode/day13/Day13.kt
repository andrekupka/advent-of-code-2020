package de.andrekupka.adventofcode.day13

import de.andrekupka.adventofcode.utils.readLinesNotBlank


fun solvePart1(departureTime: Long, busIds: List<Long>) {
    println("Departure time: $departureTime")
    println("Bus ids: $busIds")

    val busIdsWithDepartureDifference = busIds.associateWith { it - departureTime % it }.toList()
    val (optimalBusId, waitingTime) = busIdsWithDepartureDifference.minByOrNull { it.second }!!

    println("Optimal bus id is $optimalBusId")
    println("Waiting time is $waitingTime")

    val busIdMultipliedByTime = optimalBusId * waitingTime
    println("Waiting time multiplied by bus id is $busIdMultipliedByTime")
}


fun solvePart2(busIdsWithOffset: List<Pair<Long, Long>>) {
    println("Bus ids with offsets are $busIdsWithOffset")

    var timestamp = 0L
    var step = 1L

    for ((busId, offset) in busIdsWithOffset) {
        val steppedTimestamps = generateSequence(timestamp) { it + step }
        for (nextTimestamp in steppedTimestamps) {
            if ((nextTimestamp + offset) % busId == 0L) {
                timestamp = nextTimestamp
                step *= busId
                break
            }
        }
    }

    println("First timestamp that fulfills offset constraints is $timestamp")
}

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    if (lines.size != 2) {
        println("Expected exactly 2 lines")
        return
    }

    val departureTime = lines[0].toLong()
    val busIds = lines[1].split(",").mapNotNull { it.toLongOrNull() }

    solvePart1(departureTime, busIds)

    val busIdsWithOffset = lines[1].split(",")
        .map { it.toLongOrNull() }
        .mapIndexedNotNull { index, id -> id?.let { id to index.toLong() } }

    solvePart2(busIdsWithOffset)
}