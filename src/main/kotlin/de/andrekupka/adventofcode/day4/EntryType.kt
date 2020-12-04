package de.andrekupka.adventofcode.day4


enum class EntryType(
    val representation: String,
    val required: Boolean = true
) {

    BYR("byr"),
    IYR("iyr"),
    EYR("eyr"),
    HGT("hgt"),
    HCL("hcl"),
    ECL("ecl"),
    PID("pid"),
    CID("cid", required = false);

    companion object {
        private val representationMap = values().associateBy { it.representation }

        fun fromString(representation: String) = representationMap[representation]
    }
}
