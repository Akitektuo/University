package multiplication.naive

import container.Polynomial

class SequentialNaiveMultiplication(
    firstPolynomial: Polynomial,
    secondPolynomial: Polynomial,
    withoutLogs: Boolean = false
) : NaiveMultiplication(firstPolynomial, secondPolynomial, withoutLogs) {

    override fun getMultiplicationMethod() = "Sequential Naive Multiplication"

    override fun multiply() = multiplyRange(0..result.degree)
}