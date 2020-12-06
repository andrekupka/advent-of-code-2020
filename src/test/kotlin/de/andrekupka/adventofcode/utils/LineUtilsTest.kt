package de.andrekupka.adventofcode.utils

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactly
import assertk.assertions.containsOnly
import assertk.assertions.isEmpty
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class LineUtilsTest {

    @Nested
    @DisplayName("groupByNonBlankLines")
    inner class GroupByNonBlankLines {

        @Test
        internal fun `should return empty list for empty input`() {
            val result = groupByBlankLines(emptyList())
            assertThat(result).isEmpty()
        }

        @Test
        internal fun `should return empty list for input with only blank lines`() {
            val result = groupByBlankLines(listOf("", "  ", "\t"))
            assertThat(result).isEmpty()
        }


        @Test
        internal fun `should ignore leading blank lines`() {
            val result = groupByBlankLines(listOf("", "a"))
            assertThat(result).containsExactly(listOf("a"))
        }

        @Test
        internal fun `should ignore trailing blank lines`() {
            val result = groupByBlankLines(listOf("a", ""))
            assertThat(result).containsExactly(listOf("a"))
        }

        @Test
        internal fun `should return single lines grouped by blank lines`() {
            val result = groupByBlankLines(listOf("a", "", "b", "", "c"))
            assertThat(result).containsExactly(listOf("a"), listOf("b"), listOf("c"))
        }

        @Test
        internal fun `should return multiple lines grouped by blank lines`() {
            val result = groupByBlankLines(listOf("a", "b", "",  "", "c", "d"))
            assertThat(result).containsExactly(listOf("a", "b"), listOf("c", "d"))
        }
    }
}