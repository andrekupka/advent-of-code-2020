package de.andrekupka.adventofcode.day20

import de.andrekupka.adventofcode.utils.map.FieldMapParser
import de.andrekupka.adventofcode.utils.map.SimpleFieldMapParsingStrategy

object TileMapParsingStrategy : SimpleFieldMapParsingStrategy<TileFieldType> {

    override fun fieldFromChar(c: Char) = TileFieldType.fromChar(c)
}

object TileMapParser : FieldMapParser<TileMap, TileFieldType>(TileMapParsingStrategy)

class TileParsingException(message: String) : RuntimeException(message)

object TileParser {
    private val TILE_ID_REGEX = "Tile ([1-9][0-9]*):".toRegex()

    fun parse(lines: List<String>): Tile {
        val tileIdLine = lines.first()
        val mapRows = lines.drop(1)

        val tileId = parseTileId(tileIdLine)
        val tileMap = TileMapParser.parseMap(mapRows)
        return Tile(tileId, tileMap)
    }

    private fun parseTileId(tileIdLine: String): Long {
        val result = TILE_ID_REGEX.matchEntire(tileIdLine) ?: throw TileParsingException("Invalid tile id line: $tileIdLine")
        return result.groupValues[1].toLong()
    }
}