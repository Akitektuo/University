package multiplication.karatsuba

import container.Polynomial
import multiplication.BaseMultiplication
import kotlin.math.max

abstract class KaratsubaMultiplication(
    firstPolynomial: Polynomial,
    secondPolynomial: Polynomial,
    withoutLogs: Boolean = false
) : BaseMultiplication(firstPolynomial, secondPolynomial, withoutLogs) {

    data class PolynomialSplit(
        val firstLow: Polynomial,
        val firstHigh: Polynomial,
        val secondLow: Polynomial,
        val secondHigh: Polynomial
    )

    final override fun multiply() {
        result = multiply(firstPolynomial, secondPolynomial)
    }

    protected fun getHalfOfLength(firstPolynomial: Polynomial, secondPolynomial: Polynomial) =
        max(firstPolynomial.degree, secondPolynomial.degree) / 2

    protected fun split(firstPolynomial: Polynomial, secondPolynomial: Polynomial, splitIndex: Int) = PolynomialSplit(
        firstPolynomial.getSubPolynomial(to = splitIndex),
        firstPolynomial.getSubPolynomial(splitIndex),
        secondPolynomial.getSubPolynomial(to = splitIndex),
        secondPolynomial.getSubPolynomial(splitIndex),
    )

    protected fun computeResult(low: Polynomial, middle: Polynomial, high: Polynomial, length: Int): Polynomial {
        val resultHigh = high.fill(0, length * 2)
        val resultSubtraction = (middle - high - low).fill(0, length)
        return resultHigh + resultSubtraction + low
    }

    abstract fun multiply(firstPolynomial: Polynomial, secondPolynomial: Polynomial): Polynomial
}