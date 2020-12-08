package de.andrekupka.adventofcode.day7

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readLinesNotBlank
import java.util.*

private fun buildMapOfParentColorsByColor(rules: List<BagRule>): Map<String, Set<String>> =
    mutableMapOf<String, MutableSet<String>>().apply {
        rules.forEach { rule ->
            rule.containedBags.map { it.color }.forEach { color ->
                computeIfAbsent(color) { mutableSetOf() }.add(rule.color)
            }
        }
    }

fun searchBagsContainingBagOfColor(searchColor: String, rules: List<BagRule>): Int {
    val parentColorMap = buildMapOfParentColorsByColor(rules)

    val traversedParentColors = mutableSetOf<String>()

    val workList = LinkedList<String>().apply { offer(searchColor) }
    while (workList.isNotEmpty()) {
        val currentColor = workList.poll()
        val parentColors = parentColorMap[currentColor] ?: emptySet()
        parentColors.filter { it !in traversedParentColors }.forEach {
            workList.offer(it)
        }
        traversedParentColors.addAll(parentColors)
    }

    return traversedParentColors.size
}

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val rules = lines.map { bagRuleParser.parseToEnd(it) }

    val shinyGoldCount = searchBagsContainingBagOfColor("shiny gold", rules)
    println("There are $shinyGoldCount bags that contain a shiny gold bag")

    val shinyGoldOverallBagCount = RecursiveBagCounter(rules).countBagsContainedIn("shiny gold")
    println("The shiny gold bag contains $shinyGoldOverallBagCount bags")
}