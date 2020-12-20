package de.andrekupka.adventofcode.utils.map

import java.util.*
import kotlin.NoSuchElementException
import kotlin.math.max

data class FieldMapBorders<F>(
    val north: List<F>,
    val east: List<F>,
    val south: List<F>,
    val west: List<F>
)

data class BorderAdjacency(
    val ownDirection: Int,
    val otherDirection: Int,
    val flipped: Boolean
)

interface FieldMap<F> {
    val width: Int
    val height: Int

    val hasEndlessWidth: Boolean

    fun getFieldType(x: Int, y: Int): F?

    operator fun get(x: Int, y: Int): F
}

interface MutableFieldMap<F> : FieldMap<F> {
    fun setFieldType(x: Int, y: Int, fieldType: F)

    operator fun set(x: Int, y: Int, fieldType: F)
}

class DefaultFieldMap<F>(
    fields: List<F>,
    override val width: Int,
    private val endlessWidth: Boolean = false
) : MutableFieldMap<F> {

    private val fields = fields.toMutableList()
    override val height get() = fields.size / width

    override val hasEndlessWidth get() = endlessWidth

    override fun getFieldType(x: Int, y: Int) = getFieldIndexIfValid(x, y)?.let { fields[it] }

    override operator fun get(x: Int, y: Int): F = getFieldType(x, y) ?: throw NoSuchElementException("No field at x $x and y $y")

    override fun setFieldType(x: Int, y: Int, fieldType: F) {
        val index = getFieldIndexIfValid(x, y) ?: throw IndexOutOfBoundsException("No field at x $x and y $y")
        fields[index] = fieldType
    }

    override operator fun set(x: Int, y: Int, fieldType: F) = setFieldType(x, y, fieldType)

    private fun getFieldIndexIfValid(x: Int, y: Int): Int? {
        val xIndex = getXIndex(x)
        return if (isValidIndex(xIndex, y)) {
            width * y + xIndex
        } else null
    }

    private fun getXIndex(x: Int): Int = if (endlessWidth) {
        x % width
    } else {
        x
    }

    private fun isValidIndex(x: Int, y: Int) = (x in 0 until width) && (y in 0 until height)

    override fun hashCode() = Objects.hash(width, height, endlessWidth, fields)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is FieldMap<*>) {
            return false
        }
        if (width != other.width || height != other.height || hasEndlessWidth != other.hasEndlessWidth) {
            return false
        }
        forEach { x, y, type ->
            if (type != other.getFieldType(x, y)) {
                return false
            }
        }
        return true
    }

    override fun toString(): String = buildString {
        for (y in 0 until height) {
            for (x in 0 until width) {
                append(getFieldType(x, y)!!)
            }
            append("\n")
        }
    }
}

fun <F> FieldMap<F>.toMutableFieldMap(): MutableFieldMap<F> = copy()

fun <F> MutableFieldMap<F>.toFieldMap(): FieldMap<F> = copy()

private fun <F> FieldMap<F>.copy(): DefaultFieldMap<F> = MutableList(width * height) { index ->
    val y = index / width
    val x = index % width
    getFieldType(x, y)!!
}.let { DefaultFieldMap(it, width) }
