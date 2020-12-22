package de.andrekupka.adventofcode.day22

interface PlayerDeck {
    val name: String
    val cards: List<Int>

    val hasCards get() = cards.isNotEmpty()

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

    override fun appendCards(winnerCard: Int, looserCard: Int) {
        cards.apply {
            add(winnerCard)
            add(looserCard)
        }
    }

    override fun toString(): String = "Deck $name: ${cards.joinToString(",")}"
}

fun PlayerDeck.toPlayablePlayerDeck(): PlayablePlayerDeck = SimplePlayerDeck(
    name = name,
    cards = cards.toMutableList()
)

fun PlayablePlayerDeck.toPlayerDeck(): PlayerDeck = SimplePlayerDeck(
    name = name,
    cards = cards.toMutableList()
)


val PlayerDeck.score get() = cards.asReversed().mapIndexed { index, value -> (index + 1) * value }.sum()