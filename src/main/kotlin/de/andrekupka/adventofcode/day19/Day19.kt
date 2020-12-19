package de.andrekupka.adventofcode.day19

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.groupByBlankLines
import de.andrekupka.adventofcode.utils.readLines

fun main(args: Array<String>) {
    val lines = readLines(args[0])

    val lineGroups = groupByBlankLines(lines)
    if (lineGroups.size != 2) {
        println("Expected rules and message parts but got ${lineGroups.size} different parts")
        return
    }

    val ruleLines = lineGroups[0]
    val messageLines = lineGroups[1]

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