package de.andrekupka.adventofcode.day19

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.groupByBlankLines
import de.andrekupka.adventofcode.utils.readLines

val rulesToReplace = mapOf(
    "8: 42" to "8: 42 | 42 8",
    "11: 42 31" to "11: 42 31 | 42 11 31"
)

private fun countMessagesMatchingRuleZero(ruleLines: List<String>, messageLines: List<String>) {
    val ruleNodes = ruleLines.map { messageRuleParser.parseToEnd(it) }
    val rulesById = MessageRuleResolver(ruleNodes).resolve()

    val ruleZero = rulesById[0]
    if (ruleZero == null) {
        println("Expected rule 0 to be present")
        return
    }

    val numberOfMessagesMatchingRule0 = messageLines.count { ruleZero.matchesToEnd(it) }
    println("There are $numberOfMessagesMatchingRule0 messages that match rule 0")
}

fun main(args: Array<String>) {
    val lines = readLines(args[0])

    val lineGroups = groupByBlankLines(lines)
    if (lineGroups.size != 2) {
        println("Expected rules and message parts but got ${lineGroups.size} different parts")
        return
    }

    val ruleLines = lineGroups[0]
    val messageLines = lineGroups[1]

    countMessagesMatchingRuleZero(ruleLines, messageLines)

    val ruleLinesWithReplacement = ruleLines.map { rulesToReplace[it] ?: it }

    countMessagesMatchingRuleZero(ruleLinesWithReplacement, messageLines)
}