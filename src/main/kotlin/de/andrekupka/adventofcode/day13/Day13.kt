package de.andrekupka.adventofcode.day13

import de.andrekupka.adventofcode.utils.readLinesNotBlank

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    if (lines.size != 2) {
        println("Expected exactly 2 lines")
        return
    }

    val departureTime = lines[0].toInt()
    val busIds = lines[1].split(",").mapNotNull { it.toIntOrNull() }

    println("Departure time: $departureTime")
    println("Bus ids: $busIds")

    val busIdsWithDepartureDifference = busIds.associateWith { it - departureTime % it }.toList()
    val (optimalBusId, waitingTime) = busIdsWithDepartureDifference.minByOrNull { it.second }!!

    println("Optimal bus id is $optimalBusId")

    val busIdMultipliedByTime = optimalBusId * waitingTime
    println("Waiting time multiplied by bus id is $busIdMultipliedByTime")
}