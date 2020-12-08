package de.andrekupka.adventofcode.day7

class RecursiveBagCounter(rules: List<BagRule>) {

    private val rulesByColor = rules.associateBy { it.color }

    fun countBagsContainedIn(color: String): Int = computeRecursiveBagCount(color) - 1

    private fun computeRecursiveBagCount(color: String): Int {
        val rule = rulesByColor[color] ?: return 1

        return 1 + rule.containedBags.sumBy {
            it.count * countBagsContainedIn(it.color)
        }
    }
}