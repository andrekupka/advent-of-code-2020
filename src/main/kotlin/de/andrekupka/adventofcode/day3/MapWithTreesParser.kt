package de.andrekupka.adventofcode.day3

import de.andrekupka.adventofcode.utils.map.FieldMapParser
import de.andrekupka.adventofcode.utils.map.FieldMapParsingStrategy

class MapWithTreesParsingStrategy(
    private val emptyChar: Char = '.',
    private val treeChar: Char = '#'
) : FieldMapParsingStrategy<MapWithTrees, FieldType> {

    override fun fieldFromChar(c: Char) = when(c) {
        emptyChar -> FieldType.EMPTY
        treeChar -> FieldType.TREE
        else -> null
    }

    override fun createMap(fields: List<FieldType>, width: Int) = MapWithTrees(fields, width)
}

class MapWithTreesParser(
    strategy: MapWithTreesParsingStrategy = MapWithTreesParsingStrategy()
) : FieldMapParser<MapWithTrees, FieldType>(strategy)