package de.andrekupka.adventofcode.day18

enum class TermOperation {
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
    val operation: TermOperation,
    val left: ArithmeticExpression,
    val right: ArithmeticExpression
) : ArithmeticExpression() {
    override fun evaluate() = when(operation) {
        TermOperation.PLUS -> left.evaluate() + right.evaluate()
        TermOperation.TIMES -> left.evaluate() * right.evaluate()
    }
}