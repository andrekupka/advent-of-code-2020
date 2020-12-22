package de.andrekupka.adventofcode.day21

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readLinesNotBlank

fun computePossibleIngredientsByAllergens(foods: List<Food>): Map<String, Set<String>> {
    val possibleIngredientsByAllergens = mutableMapOf<String, Set<String>>()

    foods.forEach { food ->
        food.allergens.forEach { allergen ->
            possibleIngredientsByAllergens.compute(allergen) { _, value ->
                value?.intersect(food.ingredients) ?: food.ingredients
            }
        }
    }

    return possibleIngredientsByAllergens
}

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])
    /*val input = """
        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
        trh fvjkl sbzzf mxmxvkd (contains dairy)
        sqjhc fvjkl (contains soy)
        sqjhc mxmxvkd sbzzf (contains fish)
    """.trimIndent()

    val lines = input.lines().filter { it.isNotBlank() }*/

    val foods = lines.map { foodParser.parseToEnd(it) }

    val possibleIngredientsByAllergens = computePossibleIngredientsByAllergens(foods)
    val allIngredients = foods.flatMap { it.ingredients }.toSet()

    val safeIngredients = possibleIngredientsByAllergens.values.fold(allIngredients) { remainingIngredients, possiblyUnsafeIngredients ->
        remainingIngredients - possiblyUnsafeIngredients
    }

    println("Safe ingredients are $safeIngredients")
    val safeIngredientAppearances = safeIngredients.map { ingredient -> foods.count { ingredient in it.ingredients } }.sum()
    println("Safe ingredients appear $safeIngredientAppearances times in foods")
}