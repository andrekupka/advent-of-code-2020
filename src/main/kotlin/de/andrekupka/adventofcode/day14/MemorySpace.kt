package de.andrekupka.adventofcode.day14

class MemorySpace {

    private val memory = mutableMapOf<Long, Long>()

    val nonZeroValues: Map<Long, Long> get() = memory.toMap()

    operator fun set(address: Long, value: Long) {
        if (value == 0L) {
            memory.remove(address)
        } else {
            memory[address] = value
        }
    }

    operator fun get(address: Long) = memory.getOrDefault(address, 0L)
}