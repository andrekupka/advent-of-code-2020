package de.andrekupka.adventofcode.day3

import de.andrekupka.adventofcode.utils.map.FieldMap


class MapWithTrees(
    fields: List<FieldType>,
    width: Int
) : FieldMap<FieldType>(fields, width) {

    companion object {
        fun fromLines(
            lines: List<String>, emptyChar: Char = '.', treeChar: Char = '#'
        ) = MapWithTreesParser(MapWithTreesParsingStrategy(emptyChar, treeChar)).parseMap(lines)
    }
}