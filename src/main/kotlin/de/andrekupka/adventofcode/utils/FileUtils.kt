package de.andrekupka.adventofcode.utils

import java.io.File

fun readLines(path: String) = File(path).readLines()

fun <T> readLinesMapNotBlank(path: String, transform: (String) -> T) = readLines(path)
    .filter { it.isNotBlank() }
    .map { transform(it) }