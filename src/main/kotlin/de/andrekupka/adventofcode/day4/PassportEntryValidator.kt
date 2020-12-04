package de.andrekupka.adventofcode.day4

import de.andrekupka.adventofcode.day4.Validation.Invalid
import de.andrekupka.adventofcode.day4.Validation.Valid


sealed class Validation {

    object Valid : Validation()
    data class Invalid(val message: String) : Validation()
}

object PassportEntryValidator {

    private val validatorMap = mapOf(
        EntryType.BYR validateBy { validateYearInRange(it, 1920, 2002, "Birth year") },
        EntryType.IYR validateBy { validateYearInRange(it, 2010, 2020, "Issue year") },
        EntryType.EYR validateBy { validateYearInRange(it, 2020, 2030, "Expiration year") },
        EntryType.HGT validateBy { validateHeight(it, "Height") },
        EntryType.HCL validateBy { validateHexColor(it, "Hair color") },
        EntryType.ECL validateBy { validatePredefinedColor(it, "Eye color") },
        EntryType.PID validateBy { validatePid(it, "Passport ID") },
        EntryType.CID validateBy { Valid }
    )

    private fun validateYearInRange(value: String, startYear: Int, endYear: Int, name: String) =
        if (!value.matches("^\\d{4}$".toRegex())) {
            Invalid("$name must match \\d{4}")
        } else {
            val year = value.toInt()
            if (year !in startYear..endYear) {
                Invalid("$name must be in range 1920 to 2002")
            }
            Valid
        }

    private fun validateHeight(value: String, name: String): Validation {
        val matchResult = "^(\\d{2,3})(cm|in)$".toRegex().matchEntire(value)
            ?: return Invalid("$name must match (\\d{2,3})(cm|in)")

        val (height, unitValue) = matchResult.destructured
        val range = when (unitValue) {
            "cm" -> 150..193
            "in" -> 59..76
            else -> error("Already assured that unit is cm or in")
        }

        return if (height.toInt() !in range) {
            Invalid("$name must be in range ${range.first} to ${range.last}")
        } else Valid
    }

    private fun validateHexColor(value: String, name: String) =
        if (!value.matches("^#[a-f0-9]{6}$".toRegex())) {
            Invalid("$name must match #[a-z0-9]{6}")
        } else Valid

    private val predefinedColorSet = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

    private fun validatePredefinedColor(value: String, name: String) =
        if (predefinedColorSet.contains(value)) {
            Invalid("$name must be one of $predefinedColorSet")
        } else Valid

    private fun validatePid(value: String, name: String) =
        if (!value.matches("^\\d{9}$".toRegex())) {
            Invalid("$name must match \\d{9}")
        } else Valid

    fun validate(type: EntryType, value: String) =
        validatorMap[type]?.invoke(value) ?: Invalid("Unknown entry type $type")

    private infix fun EntryType.validateBy(validator: (String) -> Validation) = this to validator
}