package de.andrekupka.adventofcode.day19

interface MessageRuleMatcher {
    fun matchesToEnd(input: String): Boolean {
        val nextIndex = matches(input, 0)
        return nextIndex != null && nextIndex == input.length
    }

    fun matches(input: String, position: Int): Int?
}

sealed class MessageRule : MessageRuleMatcher {
    abstract val id: Int
}

data class TerminalMessageRule(
    override val id: Int,
    private val character: Char
) : MessageRule() {
    override fun matches(input: String, position: Int): Int? =
        if (position in input.indices && input[position] == character) {
            position + 1
        } else null
}

data class MessageRuleSequence(
    private val rules: List<MessageRule>
) : MessageRuleMatcher {

    override fun matches(input: String, position: Int): Int? {
        var nextPosition = position
        rules.forEach {
            if (nextPosition !in input.indices) {
                return null
            }
            nextPosition = it.matches(input, nextPosition) ?: return null
        }
        return nextPosition
    }
}

data class AlternativeSequenceMessageRule(
    override val id: Int,
    private val ruleSequences: List<MessageRuleSequence>
) : MessageRule() {
    override fun matches(input: String, position: Int): Int? {
        ruleSequences.forEach {
            val nextPosition = it.matches(input, position)
            if (nextPosition != null) {
                return nextPosition
            }
        }
        return null
    }
}