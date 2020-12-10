package de.andrekupka.adventofcode.day10

import de.andrekupka.adventofcode.utils.readLinesMapNotBlank
import java.math.BigInteger

fun <K, V> MutableMap<K, V>.computeWithDefault(key: K, defaultValue: V, operation: (previousValue: V) -> V) =
    compute(key) { _, currentValue -> operation(currentValue ?: defaultValue) }

fun <T> List<T>.sumByBigInteger(selector: (T) -> BigInteger): BigInteger = fold(BigInteger.ZERO) {
        accumulator, element -> accumulator + selector(element)
}

@ExperimentalStdlibApi
fun computeDifferenceDistribution(sortedRatings: List<Long>) = buildMap<Long, Long> {
    for (i in 0 until sortedRatings.lastIndex) {
        val difference = sortedRatings[i + 1] - sortedRatings[i]
        computeWithDefault(difference, 0) { it + 1 }
    }
}

fun computeRatingSuccessors(sortedRatings: List<Long>, maximumRatingDifference: Long = 3) = sortedRatings.mapIndexed { index, rating ->
    rating to if (index == sortedRatings.lastIndex) {
        emptyList()
    } else {
        sortedRatings.subList(index + 1, sortedRatings.size).takeWhile { it - rating <= maximumRatingDifference }
    }
}.toMap()

fun computeNumberOfArrangements(startRating: Long, endRating: Long, ratingSuccessors: Map<Long, List<Long>>): BigInteger {
    val arrangementCache = mutableMapOf<Long, BigInteger>()

    fun computeWithCache(intermediateRating: Long): BigInteger = arrangementCache[intermediateRating] ?: run {
        if (intermediateRating == endRating) {
            BigInteger.ONE
        } else ratingSuccessors[intermediateRating]?.takeIf { it.isNotEmpty() }?.sumByBigInteger {
            computeWithCache(it)
        } ?: BigInteger.ZERO
    }.also { arrangementCache[intermediateRating] = it }

    return computeWithCache(startRating)
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val adapterRatings = readLinesMapNotBlank(args[0]) { it.toLong() }
    val sortedAdapterRatings = adapterRatings.sorted()

    val outletRating = 0L
    val deviceRating = sortedAdapterRatings.lastOrNull()?.let { it + 3 }
    if (deviceRating == null) {
        println("No device rating found")
        return
    }
    println("Device joltage is $deviceRating")
    val sortedRatings = (listOf(outletRating) + adapterRatings + listOf(deviceRating)).sortedBy { it }

    val differenceDistribution = computeDifferenceDistribution(sortedRatings)

    val oneAndThreeDifferenceCountProduct = with(differenceDistribution) {
        getOrDefault(1, 0) * getOrDefault(3, 0)
    }
    println("The product of one and three difference counts is $oneAndThreeDifferenceCountProduct")

    val adapterSuccessors = computeRatingSuccessors(sortedRatings)

    val numberOfArrangements =  computeNumberOfArrangements(outletRating, deviceRating, adapterSuccessors)
    println("There are $numberOfArrangements number of arrangements")
}