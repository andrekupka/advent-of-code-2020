package de.andrekupka.adventofcode.day2.task3

import java.io.File

data class PasswordRule(
    val minimumCount: Int,
    val maximumCount: Int,
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
    val (minimumCount, maximumCount, character, password) = it.destructured
    val rule = PasswordRule(minimumCount.toInt(), maximumCount.toInt(), character.first())
    RuleWithPassword(rule, password)
}

fun RuleWithPassword.isValid() = rule.run {
    password.count { it == character } in minimumCount..maximumCount
}

fun main(args: Array<String>) {
    val path = args[0]

    val lines = readLines(path)

    val validCount = lines.mapNotNull { parseRule(it) }.count { it.isValid() }
    print("$validCount passwords are valid")
}