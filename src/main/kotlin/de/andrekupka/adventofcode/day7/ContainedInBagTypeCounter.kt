package de.andrekupka.adventofcode.day7

import java.util.*

@ExperimentalStdlibApi
class ContainedInBagTypeCounter(rules: List<BagRule>) {

    private val parentColorsByBagColor: Map<String, Set<String>> = buildMap<String, MutableSet<String>> {
        rules.forEach { rule ->
            rule.containedBags.map { it.color }.forEach { color ->
                computeIfAbsent(color) { mutableSetOf() }.add(rule.color)
            }
        }
    }

    fun countBagsContainingBagOfColor(searchColor: String): Int {
        val traversedParentColors = mutableSetOf<String>()

        val workList = LinkedList<String>().apply { offer(searchColor) }
        while (workList.isNotEmpty()) {
            val currentColor = workList.poll()
            val parentColors = parentColorsByBagColor[currentColor] ?: emptySet()
            parentColors.filter { it !in traversedParentColors }.forEach {
                workList.offer(it)
            }
            traversedParentColors.addAll(parentColors)
        }

        return traversedParentColors.size
    }
}