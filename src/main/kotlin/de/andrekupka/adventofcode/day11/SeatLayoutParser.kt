package de.andrekupka.adventofcode.day11

import de.andrekupka.adventofcode.utils.map.FieldMapParser
import de.andrekupka.adventofcode.utils.map.SimpleFieldMapParsingStrategy

object SeatLayoutParsingStrategy : SimpleFieldMapParsingStrategy<PositionType> {

    override fun fieldFromChar(c: Char) = PositionType.fromChar(c)
}

object SeatLayoutParser : FieldMapParser<SeatLayout, PositionType>(SeatLayoutParsingStrategy)