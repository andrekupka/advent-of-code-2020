package de.andrekupka.adventofcode.day3


class MapWithTrees(
    val width: Int,
    private val fields: List<FieldType>
) {
    val height = fields.size / width

    fun getType(x: Int, y: Int): FieldType {
        val xIndex = x % width

        val effectiveIndex = width * y + xIndex
        return fields[effectiveIndex]
    }

    companion object
}

fun MapWithTrees.Companion.fromLines(
    lines: List<String>, emptyChar: Char = '.', treeChar: Char = '#'
) = MapWithTreesParser(emptyChar, treeChar).parse(lines)