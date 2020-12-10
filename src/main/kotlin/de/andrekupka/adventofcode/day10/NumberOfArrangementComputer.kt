package de.andrekupka.adventofcode.day10

import java.math.BigInteger

class NumberOfArrangementComputer(
    private val ratingSuccessors: Map<Long, List<Long>>,
    private val startRating: Long,
    private val endRating: Long
) {

    private val arrangementCache = mutableMapOf<Long, BigInteger>()

    fun computeNumberOfArrangements(): BigInteger = computeWithCache(startRating)

    private fun computeWithCache(intermediateRating: Long): BigInteger = arrangementCache[intermediateRating] ?: run {
        if (intermediateRating == endRating) {
            BigInteger.ONE
        } else ratingSuccessors[intermediateRating]?.takeIf { it.isNotEmpty() }?.sumByBigInteger {
            computeWithCache(it)
        } ?: BigInteger.ZERO
    }.also { arrangementCache[intermediateRating] = it }
}