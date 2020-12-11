package de.andrekupka.adventofcode.utils.map

class MapParsingException(message: String) : RuntimeException(message)

interface FieldMapParsingStrategy<M : FieldMap<F>, F> {

    fun fieldFromChar(c: Char): F?

    fun createMap(fields: List<F>, width: Int): M
}

interface SimpleFieldMapParsingStrategy<F> : FieldMapParsingStrategy<FieldMap<F>, F> {
    override fun createMap(fields: List<F>, width: Int) = FieldMap(fields, width)
}

open class FieldMapParser<M : FieldMap<F>, F>(
    private val strategy: FieldMapParsingStrategy<M, F>
) {

    fun parseMap(rows: List<String>): M {
        val width = rows.map { it.length }.singleOrNull() ?: throw MapParsingException("There must not be rows with different lengths")
        val fields = rows.flatMap { parseRow(it) }
        return strategy.createMap(fields, width)
    }

    private fun parseRow(row: String) = row.map {
        strategy.fieldFromChar(it) ?: throw MapParsingException("$it is no valid field type")
    }
}