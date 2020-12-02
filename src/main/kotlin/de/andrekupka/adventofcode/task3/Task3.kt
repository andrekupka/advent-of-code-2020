package de.andrekupka.adventofcode.task3

import java.io.File

data class PasswordRule(
    val minimumCount: Int,
    val maximumCount: Int,
    val character: Char
)

fun readLines(path: String) =
    File(path).readLines()

val lineRegex = """(\d+)-(\d+)\s+([a-z])\s*:\s+([a-z]+)""".toRegex()

fun parseRule(line: String): Pair<PasswordRule, String>? {
    val result = lineRegex.matchEntire(line) ?: return null
    return result.groupValues.let {
        val minimumCount = it[1].toInt()
        val maximumCount = it[2].toInt()
        val character = it[3][0]
        val rule = PasswordRule(minimumCount, maximumCount, character)
        val password = it[4]
        rule to password
    }
}

fun Pair<PasswordRule, String>.isValid() = first.run {
    second
        .count { it == character }
        .let {
            it in minimumCount..maximumCount
        }
}

fun main(args: Array<String>) {
    val path = args[0]

    val lines = readLines(path)

    val validCount = lines.mapNotNull { parseRule(it) }.count { it.isValid() }
    print("$validCount passwords are valid")
}