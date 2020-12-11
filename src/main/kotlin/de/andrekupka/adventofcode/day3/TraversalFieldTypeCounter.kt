package de.andrekupka.adventofcode.day3

class TraversalFieldTypeCounter(
    private val stepX: Int,
    private val stepY: Int
) {

    fun countFieldTypeDuringTraversal(map: MapWithTrees, fieldType: FieldType): Int {
        var count = 0
        var x = 0
        var y = 0
        while (y < map.height) {
            val currentType = map.getFieldType(x, y)
            if (currentType == fieldType) {
                count++
            }
            x += stepX
            y += stepY
        }
        return count
    }
}