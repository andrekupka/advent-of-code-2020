package de.andrekupka.adventofcode.day18

enum class TokenType {
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS,
    PLUS,
    TIMES,
    NUMBER
}

data class Token(
    val text: String,
    val type: TokenType
)

@ExperimentalStdlibApi
val arithmeticTokenizer = buildTokenizer<Token> {
    literalToken("(") { Token(it, TokenType.OPEN_PARENTHESIS) }
    literalToken(")") { Token(it, TokenType.CLOSE_PARENTHESIS) }
    literalToken("+") { Token(it, TokenType.PLUS) }
    literalToken("*") { Token(it, TokenType.TIMES) }
    regexToken("([1-9][0-9]*|0)") { Token(it, TokenType.NUMBER) }

    // ignore whitespaces
    regexToken("\\s+")
}