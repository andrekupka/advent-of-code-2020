package de.andrekupka.adventofcode.utils.map

open class FieldMap<F>(
    private val fields: List<F>,
    val width: Int,
) {
    val height = fields.size / width


    fun getFieldType(x: Int, y: Int): F {
        val xIndex = x % width

        val effectiveIndex = width * y + xIndex
        return fields[effectiveIndex]
    }
}