package de.andrekupka.adventofcode.day4

import de.andrekupka.adventofcode.utils.readLines

fun main(args: Array<String>) {
    val lines = readLines(args[0])

    val passports = PassportParser.parseFromLines(lines)

    val validPassportCount = passports.count { it.containsAllRequiredEntries() }
    println("There are $validPassportCount valid passports")
}