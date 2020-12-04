package de.andrekupka.adventofcode.day4

data class Passport(
    val entries: Map<EntryType, String>
)

private val requiredEntryTypes = EntryType.values().filter { it.required }

fun Passport.containsAllRequiredEntries() = entries.keys.containsAll(requiredEntryTypes)

fun Passport.validateEntries() = entries.mapValues { PassportEntryValidator.validate(it.key, it.value) }

fun Passport.isValid() = containsAllRequiredEntries() && validateEntries().values.all { it == Validation.Valid }