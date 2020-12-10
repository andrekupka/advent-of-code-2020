package de.andrekupka.adventofcode.day10

import de.andrekupka.adventofcode.utils.readLinesMapNotBlank

fun <K, V> MutableMap<K, V>.computeWithDefault(key: K, defaultValue: V, operation: (previousValue: V) -> V) =
    compute(key) { _, currentValue -> operation(currentValue ?: defaultValue) }

@ExperimentalStdlibApi
fun computeDifferenceDistribution(sortedRatings: List<Int>) = buildMap<Int, Int> {
    for (i in 0 until sortedRatings.lastIndex) {
        val difference = sortedRatings[i + 1] - sortedRatings[i]
        computeWithDefault(difference, 0) { it + 1 }
    }
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val adapterRatings = readLinesMapNotBlank(args[0]) { it.toInt() }

    val outletJoltage = 0
    val deviceJoltage = adapterRatings.maxOrNull()?.let { it + 3 }
    if (deviceJoltage == null) {
        println("No max rating found")
        return
    }
    val sortedRatings = (adapterRatings + listOf(outletJoltage, deviceJoltage)).sortedBy { it }

    val differenceDistribution = computeDifferenceDistribution(sortedRatings)

    val oneAndThreeDifferenceCountProduct = with(differenceDistribution) {
        getOrDefault(1, 0) * getOrDefault(3, 0)
    }
    println("The product of one and three difference counts is $oneAndThreeDifferenceCountProduct")
}