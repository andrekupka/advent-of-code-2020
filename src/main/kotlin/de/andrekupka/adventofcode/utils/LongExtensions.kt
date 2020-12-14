package de.andrekupka.adventofcode.utils

fun Long.setOneAtIndex(index: Int): Long = this or (1L shl index)

fun Long.setZeroAtIndex(index: Int): Long = this and (1L shl index).inv()

fun Long.floatAtIndex(index: Int) = listOf(setZeroAtIndex(index), setOneAtIndex(index))