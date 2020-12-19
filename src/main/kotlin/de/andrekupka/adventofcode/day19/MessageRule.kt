package de.andrekupka.adventofcode.day19

interface MessageRuleMatcher {
    fun matchesToEnd(input: String): Boolean {
        val nextPositions = matches(input, 0)
        return input.length in nextPositions
    }

    fun matches(input: String, position: Int): Set<Int>
}

sealed class MessageRule : MessageRuleMatcher {
    abstract val id: Int
}

class MessageRuleReference(
    ruleProvider: () -> MessageRule
) : MessageRule() {
    val rule by lazy(ruleProvider)

    override val id get() = rule.id

    override fun matches(input: String, position: Int) = rule.matches(input, position)
}

data class TerminalMessageRule(
    override val id: Int,
    private val character: Char
) : MessageRule() {
    override fun matches(input: String, position: Int): Set<Int> =
        if (position in input.indices && input[position] == character) {
            setOf(position + 1)
        } else emptySet()
}

data class MessageRuleSequence(
    private val rules: List<MessageRule>
) : MessageRuleMatcher {

    override fun matches(input: String, position: Int): Set<Int> {
        var nextPositions = setOf(position)
        rules.forEach { rule ->
            nextPositions = nextPositions.flatMap { rule.matches(input, it) }.toSet()
            if (nextPositions.isEmpty()) {
                return nextPositions
            }
        }
        return nextPositions
    }
}

data class AlternativeSequenceMessageRule(
    override val id: Int,
    private val ruleSequences: List<MessageRuleSequence>
) : MessageRule() {
    override fun matches(input: String, position: Int): Set<Int> =
        ruleSequences.flatMap { it.matches(input, position) }.toSet()
}