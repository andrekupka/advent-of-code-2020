package de.andrekupka.adventofcode.task4

import java.io.File

data class PasswordRule(
    val indexes: List<Int>,
    val character: Char
)

data class RuleWithPassword(
    val rule: PasswordRule,
    val password: String
)

fun readLines(path: String) =
    File(path).readLines()

val lineRegex = """(\d+)-(\d+)\s+([a-z])\s*:\s+([a-z]+)""".toRegex()

fun parseRule(line: String): RuleWithPassword? {
    val result = lineRegex.matchEntire(line) ?: return null
    return result.groupValues.let {
        val firstIndex = it[1].toInt() - 1
        val secondIndex = it[2].toInt() - 1
        val character = it[3][0]
        val rule = PasswordRule(listOf(firstIndex, secondIndex), character)
        val password = it[4]
        RuleWithPassword(rule, password)
    }
}

fun RuleWithPassword.isValid() = rule.run {
    indexes.map { password[it] }.count { it == character } == 1
}

fun main(args: Array<String>) {
    val path = args[0]

    val lines = readLines(path)

    val validCount = lines.mapNotNull { parseRule(it) }.count { it.isValid() }
    print("$validCount passwords are valid")
}