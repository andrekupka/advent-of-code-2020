package de.andrekupka.adventofcode.day22

interface PlayerDeck {
    val name: String
    val cards: List<Int>

    val isLooser get() = cards.isEmpty()
}

interface PlayablePlayerDeck : PlayerDeck {
    fun takeTopCard(): Int

    fun appendCards(winnerCard: Int, looserCard: Int)
}

data class SimplePlayerDeck(
    override val name: String,
    override val cards: MutableList<Int>
) : PlayablePlayerDeck {

    override fun takeTopCard(): Int = cards.removeFirst()

    override fun appendCards(winnerCard: Int, loserCard: Int) {
        require(winnerCard > loserCard) { "Winner card must be greater than loser card" }
        cards.apply {
            add(winnerCard)
            add(loserCard)
        }
    }
}


fun PlayerDeck.toPlayablePlayerDeck() = SimplePlayerDeck(
    name = name,
    cards = cards.toMutableList()
)

val PlayerDeck.score get() = cards.asReversed().mapIndexed { index, value -> (index + 1) * value }.sum()