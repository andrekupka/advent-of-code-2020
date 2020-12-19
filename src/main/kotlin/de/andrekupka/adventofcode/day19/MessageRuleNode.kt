package de.andrekupka.adventofcode.day19

sealed class MessageRuleNode {
    abstract val id: Int
}

data class TerminalMessageRuleNode(
    override val id: Int,
    val character: Char
) : MessageRuleNode()

data class AlternativeSequenceMessageRuleNode(
    override val id: Int,
    val ruleSequences: List<List<Int>>
) : MessageRuleNode()