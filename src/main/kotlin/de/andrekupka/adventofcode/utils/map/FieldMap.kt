package de.andrekupka.adventofcode.utils.map

import java.util.*
import kotlin.math.max

interface FieldMap<F> {
    val width: Int
    val height: Int

    val hasEndlessWidth: Boolean

    fun getFieldType(x: Int, y: Int): F?
}

interface MutableFieldMap<F> : FieldMap<F> {
    fun setFieldType(x: Int, y: Int, fieldType: F)
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

    override fun setFieldType(x: Int, y: Int, fieldType: F) {
        val index = getFieldIndexIfValid(x, y) ?: throw IndexOutOfBoundsException("No field at x $x and y $y")
        fields[index] = fieldType
    }

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

inline fun <F> FieldMap<F>.forEach(operation: (x: Int, y: Int, type: F) -> Unit) {
    for (y in 0 until height) {
        for (x in 0 until width) {
            operation(x, y, getFieldType(x, y)!!)
        }
    }
}

inline fun <F> FieldMap<F>.count(predicate: (F) -> Boolean): Int {
    var count = 0
    forEach { _, _, type ->
        if (predicate(type)) {
            count++
        }
    }
    return count
}

fun <F> FieldMap<F>.toMutableFieldMap(): MutableFieldMap<F> = copy()

fun <F> MutableFieldMap<F>.toFieldMap(): FieldMap<F> = copy()

@ExperimentalStdlibApi
fun <F> FieldMap<F>.getAdjacentFieldTypes(x: Int, y: Int): List<F> = buildList {
    for (adjacentY in y - 1..y + 1) {
        for (adjacentX in x - 1..x + 1) {
            if (adjacentX != x || adjacentY != y) {
                getFieldType(adjacentX, adjacentY)?.let { add(it) }
            }
        }
    }
}

private val DIRECTION_LIST = listOf(
    -1 to 0, // left
    -1 to -1, // left up
    0 to -1, // up
    1 to -1, // right up
    1 to 0, // right
    1 to 1, // right down
    0 to 1, // down
    -1 to 1 // left down
)

fun <F> FieldMap<F>.countVisibleFieldTypes(x: Int, y: Int, blockType: F, searchType: F): Int {
    fun searchInDirection(deltaX: Int, deltaY: Int): Boolean {
        for (distance in 1..max(width, height)) {
            val effectiveX = x + deltaX * distance
            val effectiveY = y + deltaY * distance
            when (getFieldType(effectiveX, effectiveY)) {
                blockType, null -> return false
                searchType -> return true
            }
        }
        return false
    }

    return DIRECTION_LIST.map { (deltaX, deltaY) -> searchInDirection(deltaX, deltaY) }.count { it }
}

private fun <F> FieldMap<F>.copy(): DefaultFieldMap<F> = MutableList(width * height) { index ->
    val y = index / width
    val x = index % width
    getFieldType(x, y)!!
}.let { DefaultFieldMap(it, width) }
