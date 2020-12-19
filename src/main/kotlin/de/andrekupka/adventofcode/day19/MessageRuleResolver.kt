package de.andrekupka.adventofcode.day19

class MessageRuleResolvingException(message: String) : RuntimeException(message)

class MessageRuleResolver(
    ruleNodes: List<MessageRuleNode>
) {
    private var ruleNodesById = ruleNodes.associateBy { it.id }

    private var rulesById = mutableMapOf<Int, MessageRule>()

    fun resolve(): Map<Int, MessageRule> {
        ruleNodesById.values.forEach {
            resolveNode(it)
        }
        return rulesById
    }

    private fun resolveNode(ruleNode: MessageRuleNode): MessageRule {
        val rule = rulesById[ruleNode.id]
        if (rule != null) {
            return rule
        }

        return with(ruleNode) {
            when (this) {
                is TerminalMessageRuleNode -> {
                    TerminalMessageRule(id, character)
                }
                is AlternativeSequenceMessageRuleNode -> {
                    AlternativeSequenceMessageRule(id, ruleSequences.map { resolveRuleSequence(it) })
                }
            }
        }.also {
            rulesById[it.id] = it
        }
    }

    private fun resolveRuleSequence(ruleSequence: List<Int>) = MessageRuleSequence(
        ruleSequence.map {
            val node = ruleNodesById[it] ?: throw MessageRuleResolvingException("There is no rule with id $it")
            MessageRuleReference { resolveNode(node) }
        }
    )
}