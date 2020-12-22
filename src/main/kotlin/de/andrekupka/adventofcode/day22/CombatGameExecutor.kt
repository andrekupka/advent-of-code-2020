package de.andrekupka.adventofcode.day22

class CombatGameExecutor(
    firstDeck: PlayerDeck,
    secondDeck: PlayerDeck
) {

    private val firstDeck = firstDeck.toPlayablePlayerDeck()
    private val secondDeck = secondDeck.toPlayablePlayerDeck()

    fun execute(): GameResult {
        while (!hasFinished()) {
            playRound()
        }

        return if (firstDeck.isLooser) {
            GameResult(secondDeck, firstDeck)
        } else {
            GameResult(firstDeck, secondDeck)
        }
    }

    private fun playRound() {
        val firstCard = firstDeck.takeTopCard()
        val secondCard = secondDeck.takeTopCard()

        if (firstCard > secondCard) {
            firstDeck.appendCards(firstCard, secondCard)
        } else {
            secondDeck.appendCards(secondCard, firstCard)
        }
    }

    private fun hasFinished() = firstDeck.cards.isEmpty() || secondDeck.cards.isEmpty()
}