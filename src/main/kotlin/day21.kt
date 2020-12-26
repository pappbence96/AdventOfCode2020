import java.io.File

data class Food(val ingredients: List<String>, val allergens: List<String>)

fun main() {
    val input = File({}.javaClass.getResource("day21.txt").toURI())
        .readLines()
        .map {
            val (ingredients, allergens) = "([\\w ]+) \\(contains ([\\w ,]+)\\)".toRegex().find(it)!!.destructured
            Food(ingredients.split(" "), allergens.split(", "))
        }

    val allergenToPossibleIngredients = input
        .flatMap { it.allergens }
        .asSequence()
        .distinct()
        .map { allergen ->
            allergen to input
                .filter { r -> r.allergens.contains(allergen) }
                .map { r -> r.ingredients }
                .reduce { acc, ingredients -> acc.toMutableList().apply { retainAll(ingredients) } }
                .toMutableList()
        }
        .toMutableList()

    val ingredientToAllergen = mutableMapOf<String, String>()

    while (ingredientToAllergen.size != allergenToPossibleIngredients.size) {
        val (allergen, ingredients) = allergenToPossibleIngredients.first { it.second.size == 1}
        val ingredient = ingredients.single()
        ingredientToAllergen[ingredient] = allergen
        allergenToPossibleIngredients.forEach { it.second.remove(ingredient) }
    }

    input
        .flatMap { it.ingredients }
        .count { !ingredientToAllergen.containsKey(it) }
        .also { println("1. Occurences of non-allergen containing ingredients: $it") }


    ingredientToAllergen
        .toList()
        .sortedBy { it.second }
        .map { it.first }
        .joinToString(",") { it }
        .also { println("2. Canonical dangerous ingredient list: $it") }
}