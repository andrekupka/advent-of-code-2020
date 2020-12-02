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

fun parseRule(line: String): RuleWithPassword? = lineRegex.matchEntire(line)?.let {
    val (firstIndex, secondIndex, character, password) = it.destructured
    val rule = PasswordRule(listOf(firstIndex.toInt() - 1, secondIndex.toInt() - 1), character.first())
    RuleWithPassword(rule, password)
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