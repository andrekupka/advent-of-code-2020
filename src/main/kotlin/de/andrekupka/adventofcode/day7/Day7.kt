package de.andrekupka.adventofcode.day7

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readLinesNotBlank

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])

    val rules = lines.map { bagRuleParser.parseToEnd(it) }

    val shinyGoldCount = ContainedInBagTypeCounter(rules).countBagsContainingBagOfColor("shiny gold")
    println("There are $shinyGoldCount bags that contain a shiny gold bag")

    val shinyGoldOverallBagCount = RecursiveBagCounter(rules).countBagsContainedIn("shiny gold")
    println("The shiny gold bag contains $shinyGoldOverallBagCount bags")
}