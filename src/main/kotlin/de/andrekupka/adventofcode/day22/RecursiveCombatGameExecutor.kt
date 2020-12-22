package de.andrekupka.adventofcode.day22

private fun PlayerDeck.hasEnoughCardsForSubGame(amount: Int) = amount <= cards.size

private fun PlayerDeck.toSubGameDeck(amount: Int): PlayablePlayerDeck = SimplePlayerDeck(
    name = name,
    cards = cards.subList(0, amount).toMutableList()
)

class RecursiveCombatGameExecutor(
    private val initialFirstDeck: PlayerDeck,
    private val initialSecondDeck: PlayerDeck
) {
    private var gameCounter = 0

    fun execute(): GameResult = playGame(++gameCounter, initialFirstDeck.toPlayablePlayerDeck(), initialSecondDeck.toPlayablePlayerDeck())

    private fun playGame(gameIndex: Int, firstDeck: PlayablePlayerDeck, secondDeck: PlayablePlayerDeck): GameResult {
        println("\nStarting game $gameIndex")

        var roundIndex = 0

        val alreadyPlayedRounds = mutableSetOf<Pair<List<Int>, List<Int>>>()

        while (firstDeck.hasCards && secondDeck.hasCards) {
            val round = firstDeck.cards.toList() to secondDeck.cards.toList()
            if (round in alreadyPlayedRounds) {
                println("${firstDeck.name} wins game $gameIndex due to already played round")
                return GameResult(firstDeck, secondDeck)
            }
            alreadyPlayedRounds.add(round)

            println("---Round ${++roundIndex} (Game $gameIndex)---")
            println("$firstDeck")
            println("$secondDeck")

            val firstCard = firstDeck.takeTopCard()
            val secondCard = secondDeck.takeTopCard()

            println("${firstDeck.name} plays $firstCard")
            println("${secondDeck.name} plays $secondCard")

            if (firstDeck.hasEnoughCardsForSubGame(firstCard) && secondDeck.hasEnoughCardsForSubGame(secondCard)) {
                val (winnerDeck, _) = playGame(++gameCounter, firstDeck.toSubGameDeck(firstCard), secondDeck.toSubGameDeck(secondCard))

                if (winnerDeck.name == firstDeck.name) {
                    firstDeck.appendCards(firstCard, secondCard)
                } else {
                    secondDeck.appendCards(secondCard, firstCard)
                }
            } else {
                if (firstCard > secondCard) {
                    println("${firstDeck.name} wins round $roundIndex of game $gameIndex\n")
                    firstDeck.appendCards(firstCard, secondCard)
                } else if (firstCard < secondCard) {
                    println("${secondDeck.name} wins round $roundIndex of game $gameIndex\n")
                    secondDeck.appendCards(secondCard, firstCard)
                } else {
                    error("Game $gameIndex and round $roundIndex, equal cards")
                }
            }
        }

        return if (secondDeck.isLooser) {
            println("${firstDeck.name} wins game $gameIndex\n")
            GameResult(firstDeck, secondDeck)
        } else {
            println("${secondDeck.name} wins game $gameIndex\n")
            GameResult(secondDeck, firstDeck)
        }
    }
}