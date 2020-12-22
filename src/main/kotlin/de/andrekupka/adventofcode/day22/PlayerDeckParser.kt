package de.andrekupka.adventofcode.day22

class PlayerDeckParsingException(message: String) : RuntimeException(message)

object PlayerDeckParser {
    private val playerNameRegex = "([A-Za-z0-9 ]+):".toRegex()

    fun parseLines(lines: List<String>): PlayerDeck {
        if (lines.size < 2) {
            throw PlayerDeckParsingException("Expected at least a player name and one card")
        }
        val playerLine = lines.first()
        val cardLines = lines.drop(1)

        val playerName = parsePlayerName(playerLine)
        val cards = parseCards(cardLines)
        return SimplePlayerDeck(playerName, cards.toMutableList())
    }

    private fun parsePlayerName(playerLine: String): String {
        val result = playerNameRegex.matchEntire(playerLine) ?: throw PlayerDeckParsingException("Player line did not have correct pattern")
        return result.groupValues[1]
    }

    private fun parseCards(cardLines: List<String>) =
        cardLines.map { it.toIntOrNull() ?: throw PlayerDeckParsingException("$it is not a valid card number") }
}