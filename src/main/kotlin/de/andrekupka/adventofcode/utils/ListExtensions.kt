package de.andrekupka.adventofcode.utils

infix fun <T, S> List<T>.cross(others: List<S>): Sequence<Pair<T, S>> = sequence {
    forEach { ownElement ->
        others.forEach { otherElement ->
            yield(ownElement to  otherElement)
        }
    }
}