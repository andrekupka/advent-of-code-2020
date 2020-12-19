package de.andrekupka.adventofcode.day18

class ArithmeticExpressionParserException(message: String) : RuntimeException(message)

@ExperimentalStdlibApi
object HandwrittenArithmeticExpressionParser {

    fun parse(input: String): ArithmeticExpression =
        parse(arithmeticTokenizer.tokenize(input))

    fun parse(tokens: List<Token>): ArithmeticExpression =
        when (
            val result = parseExpression(tokens, 0)) {
            is ErrorResult -> throw ArithmeticExpressionParserException(result.message)
            is SuccessResult -> if (result.nextPosition != tokens.size) {
                throw ArithmeticExpressionParserException("Unparsed remainder at ${result.nextPosition}")
            } else result.value
        }

    private fun parseExpression(tokens: List<Token>, position: Int): ParseResult<ArithmeticExpression> =
        when (val leftTermResult = parseTerm(tokens, position)) {
            is ErrorResult -> leftTermResult
            is SuccessResult -> {
                when (val remainingOperatorsWithTermsResult = parseOperatorsWithTerms(tokens, leftTermResult.nextPosition)) {
                    is ErrorResult -> leftTermResult
                    is SuccessResult -> SuccessResult(
                        reduceTermExpressionsWithOperators(leftTermResult.value, remainingOperatorsWithTermsResult.value),
                        remainingOperatorsWithTermsResult.nextPosition
                    )
                }
            }
        }

    private fun reduceTermExpressionsWithOperators(
        leftTermExpression: ArithmeticExpression,
        remainingOperatorsWithTerms: List<Pair<TermOperator, ArithmeticExpression>>
    ): ArithmeticExpression {
        var result = leftTermExpression
        remainingOperatorsWithTerms.forEach { (operator, term) ->
            result = TermExpression(operator, result, term)
        }
        return result
    }

    private fun parseOperatorsWithTerms(tokens: List<Token>, position: Int): ParseResult<List<Pair<TermOperator, ArithmeticExpression>>> {
        val operatorsAndTerms = mutableListOf<Pair<TermOperator, ArithmeticExpression>>()
        var nextPosition = position
        while (true) {
            val result = parseOperatorWithTerm(tokens, nextPosition)
            nextPosition = when (result) {
                is ErrorResult -> break
                is SuccessResult -> {
                    operatorsAndTerms.add(result.value)
                    result.nextPosition
                }
            }
        }
        return SuccessResult(operatorsAndTerms, nextPosition)
    }

    private fun parseOperatorWithTerm(tokens: List<Token>, position: Int): ParseResult<Pair<TermOperator, ArithmeticExpression>> {
        val operatorResult = parseTermOperation(tokens, position)
        val operator = when (operatorResult) {
            is ErrorResult -> return operatorResult
            is SuccessResult -> operatorResult.value
        }

        return when (val termResult = parseTerm(tokens, operatorResult.nextPosition)) {
            is ErrorResult -> termResult
            is SuccessResult -> SuccessResult(operator to termResult.value, termResult.nextPosition)
        }
    }

    private fun parseTermOperation(tokens: List<Token>, position: Int): ParseResult<TermOperator> =
        tokens.expectAt(position, TokenType.PLUS, TokenType.TIMES).map {
            when (it.type) {
                TokenType.PLUS -> TermOperator.PLUS
                TokenType.TIMES -> TermOperator.TIMES
                else -> error("Unexpected operator ${it.type}")
            }
        }

    private fun parseTerm(tokens: List<Token>, position: Int): ParseResult<ArithmeticExpression> {
        val errors = mutableListOf<String>()

        when (val numberResult = parseNumber(tokens, position)) {
            is SuccessResult -> return numberResult
            is ErrorResult -> errors.add(numberResult.message)
        }

        when (val parenthesizedExpressionResult = parseParenthesizedExpression(tokens, position)) {
            is SuccessResult -> return parenthesizedExpressionResult
            is ErrorResult -> errors.add(parenthesizedExpressionResult.message)
        }

        return ErrorResult("Got multiple errors: ${errors.joinToString(",")}")
    }

    private fun parseParenthesizedExpression(tokens: List<Token>, position: Int): ParseResult<ArithmeticExpression> {
        val expressionPosition = when (val openResult = tokens.expectAt(position, TokenType.OPEN_PARENTHESIS)) {
            is SuccessResult -> openResult.nextPosition
            is ErrorResult -> return openResult
        }

        val expressionResult = parseExpression(tokens, expressionPosition)
        val closePosition = when (expressionResult) {
            is SuccessResult -> expressionResult.nextPosition
            is ErrorResult -> return expressionResult
        }

        return when (val closeResult = tokens.expectAt(closePosition, TokenType.CLOSE_PARENTHESIS)) {
            is ErrorResult -> closeResult
            is SuccessResult -> expressionResult.value.asSuccess(closeResult.nextPosition)
        }
    }

    private fun parseNumber(tokens: List<Token>, position: Int) =
        tokens.expectAt(position, TokenType.NUMBER).map { NumberLiteralExpression(it.text.toLong()) }

    private fun List<Token>.expectAt(position: Int, vararg types: TokenType) =
        at(position).flatMap { value, nextPosition ->
            if (value.type !in types) {
                ErrorResult("Expected token of any type $types but found ${value.type}")
            } else SuccessResult(value, nextPosition)
        }

    private fun List<Token>.at(position: Int) = if (position in indices) {
        get(position).asSuccess(position + 1)
    } else ErrorResult("Expected token but got eof")
}