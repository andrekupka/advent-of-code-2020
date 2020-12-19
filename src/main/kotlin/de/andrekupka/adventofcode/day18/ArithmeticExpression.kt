package de.andrekupka.adventofcode.day18

enum class TermOperator {
    PLUS,
    TIMES
}

sealed class ArithmeticExpression {
    abstract fun evaluate(): Long
}

data class NumberLiteralExpression(
    val value: Long
) : ArithmeticExpression() {
    override fun evaluate() = value
}

data class TermExpression(
    val operator: TermOperator,
    val left: ArithmeticExpression,
    val right: ArithmeticExpression
) : ArithmeticExpression() {
    override fun evaluate() = when(operator) {
        TermOperator.PLUS -> left.evaluate() + right.evaluate()
        TermOperator.TIMES -> left.evaluate() * right.evaluate()
    }
}