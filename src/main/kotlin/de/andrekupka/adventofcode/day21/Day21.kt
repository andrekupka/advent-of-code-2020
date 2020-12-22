package de.andrekupka.adventofcode.day21

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import de.andrekupka.adventofcode.utils.readLinesNotBlank

private fun computePossibleIngredientsByAllergens(foods: List<Food>): Map<String, Set<String>> {
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

private fun determineSafeIngredients(foods: List<Food>, possibleIngredientsByAllergens: Map<String, Set<String>>) {
    val allIngredients = foods.flatMap { it.ingredients }.toSet()

    val safeIngredients = possibleIngredientsByAllergens.values.fold(allIngredients) { remainingIngredients, possiblyUnsafeIngredients ->
        remainingIngredients - possiblyUnsafeIngredients
    }

    println("Safe ingredients are $safeIngredients")
    val safeIngredientAppearances = safeIngredients.map { ingredient -> foods.count { ingredient in it.ingredients } }.sum()
    println("Safe ingredients appear $safeIngredientAppearances times in foods")
}

private fun computeAllergensByIngredients(possibleIngredientsByAllergens: Map<String, Set<String>>): Map<String, String> {
    val workMap = possibleIngredientsByAllergens.mapValues { it.value.toMutableSet() }.toMutableMap()

    val allergensByIngredient = mutableMapOf<String, String>()

    while (workMap.isNotEmpty()) {
        val unambiguousIngredientsToAllergens = workMap.filterValues { it.size == 1 }.map { it.value.single() to it.key }.toMap()
        unambiguousIngredientsToAllergens.values.forEach { workMap.remove(it) }

        workMap.forEach { (_, ingredients) ->
            ingredients.removeAll(unambiguousIngredientsToAllergens.keys)
        }

        allergensByIngredient.putAll(unambiguousIngredientsToAllergens)
    }

    return allergensByIngredient
}

fun main(args: Array<String>) {
    val lines = readLinesNotBlank(args[0])
    
    val foods = lines.map { foodParser.parseToEnd(it) }

    val possibleIngredientsByAllergens = computePossibleIngredientsByAllergens(foods)
    determineSafeIngredients(foods, possibleIngredientsByAllergens)

    val allergensByIngredients = computeAllergensByIngredients(possibleIngredientsByAllergens)
    val canonicalDangerousIngredients = allergensByIngredients.toList().sortedBy { it.second }.map { it.first }

    val canonicalDangerousIngredientsRepresentation = canonicalDangerousIngredients.joinToString(",")
    println("Canonical dangerous ingredients are: $canonicalDangerousIngredientsRepresentation")
}
