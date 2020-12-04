package de.andrekupka.adventofcode.utils

import java.io.File

fun readLines(path: String) = File(path).readLines()

fun readLinesNotBlank(path: String) = readLines(path).filter { it.isNotBlank() }

fun <T> readLinesMapNotBlank(path: String, transform: (String) -> T) = readLinesNotBlank(path)
    .map { transform(it) }