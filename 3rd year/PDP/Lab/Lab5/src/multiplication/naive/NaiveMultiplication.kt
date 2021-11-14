package multiplication.naive

import Polynomial
import multiplication.BaseMultiplication

abstract class NaiveMultiplication(firstPolynomial: Polynomial, secondPolynomial: Polynomial) :
    BaseMultiplication(firstPolynomial, secondPolynomial) {

    protected fun multiplyRange(range: IntRange) = range.forEach {
        computeDegree(it)
    }

    private fun computeDegree(degree: Int) {
        /*
        Example
        first: 2X^3 + 3X^2 + X + 5
        second: 5X^3 + 2X^2 + 8X + 9

        Calling computeDegree(2) yields:
        5 * 2x^2 + x * 8X + 3X^2 * 9

        Always multiplying the index with its complementary (degree - index).
        degree 0: first[0] * second[2 - 0]
        degree 1: first[1] * second[2 - 1]
        degree 2: first[2] * second[2 - 2]
        */

        if (degree > result.degree)
            return

        (0..degree).map { it to degree - it }
            .filter { (index, complementary) ->
                index <= firstPolynomial.degree && complementary <= secondPolynomial.degree
            }.forEach { (index, complementary) ->
                result[degree] += firstPolynomial[index] * secondPolynomial[complementary]
            }
    }
}