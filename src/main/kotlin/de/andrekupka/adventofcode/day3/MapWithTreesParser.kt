package de.andrekupka.adventofcode.day3

class MapParseException(message: String) : RuntimeException(message)

class MapWithTreesParser(
    private val emptyChar: Char = '.',
    private val treeChar: Char = '#'
) {

    fun parse(lines: List<String>): MapWithTrees {
        val typedLines = lines.map { parseLine(it) }
        val width = lines.map { it.length }.toSet().singleOrNull()
            ?: throw MapParseException("Lines of different length")

        return MapWithTrees(width, typedLines.flatten())
    }

    private fun parseLine(line: String): List<FieldType> =
        line.map {
            when (it) {
                emptyChar -> FieldType.EMPTY
                treeChar -> FieldType.TREE
                else -> throw MapParseException("Invalid character $it")
            }
        }
}