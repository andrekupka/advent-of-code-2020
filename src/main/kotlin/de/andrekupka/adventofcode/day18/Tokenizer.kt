package de.andrekupka.adventofcode.day18

internal data class TokenMatch(
    val text: String
)

internal interface TokenMatcher {
    fun matchAtPosition(input: String, position: Int): Int?
}

internal class LiteralMatcher(
    private val literal: String
) : TokenMatcher {

    override fun matchAtPosition(input: String, position: Int) =
        if (input.substring(position).startsWith(literal)) {
            position + literal.length
        } else null
}

internal class RegexMatcher(
    pattern: String
) : TokenMatcher {

    private val regex = "\\A$pattern".toRegex()

    override fun matchAtPosition(input: String, position: Int): Int? {
        val matcher = regex.toPattern().matcher(input).region(position, input.length)
        return if (matcher.find()) {
            matcher.end()
        } else null
    }
}

typealias TokenCreator<T> = (String) -> T

internal class TokenizerRule<T>(
    val matcher: TokenMatcher,
    val tokenCreator: TokenCreator<T>?
)

class TokenizerException(message: String) : RuntimeException(message)

interface TokenizerBuilder<T> {
    fun literalToken(literal: String, tokenCreator: TokenCreator<T>? = null)
    fun regexToken(pattern: String, tokenCreator: TokenCreator<T>? = null)
}

@ExperimentalStdlibApi
fun <T> buildTokenizer(block: TokenizerBuilder<T>.() -> Unit): Tokenizer<T> {
    val rules = buildList<TokenizerRule<T>> {
        val builder = object : TokenizerBuilder<T> {
            override fun literalToken(literal: String, tokenCreator: TokenCreator<T>?) {
                add(TokenizerRule(LiteralMatcher(literal), tokenCreator))
            }

            override fun regexToken(pattern: String, tokenCreator: TokenCreator<T>?) {
                add(TokenizerRule(RegexMatcher(pattern), tokenCreator))
            }
        }
        block(builder)
    }
    return DefaultTokenizer(rules)
}

interface Tokenizer<T> {
    fun tokenize(input: String): List<T>
}

@ExperimentalStdlibApi
internal class DefaultTokenizer<T> internal constructor(
    internal val rules: List<TokenizerRule<T>>
) : Tokenizer<T> {

    override fun tokenize(input: String) = buildList<T> {
        var position = 0
        while (position < input.length) {
            val (token, nextPosition) = nextToken(input, position)
            position = nextPosition
            token?.let { add(it) }
        }
    }

    private fun nextToken(input: String, position: Int): Pair<T?, Int> {
        rules.forEach { rule ->
            with(rule) {
                val nextPosition = matcher.matchAtPosition(input, position)
                if (nextPosition != null) {
                    val text = input.substring(position, nextPosition)
                    return tokenCreator?.invoke(text) to nextPosition
                }
            }
        }
        throw TokenizerException("No matching token found at position $position")
    }
}