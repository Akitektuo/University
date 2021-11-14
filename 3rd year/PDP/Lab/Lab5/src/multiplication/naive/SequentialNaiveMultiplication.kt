package multiplication.naive

import Polynomial

class SequentialNaiveMultiplication(firstPolynomial: Polynomial, secondPolynomial: Polynomial) :
    NaiveMultiplication(firstPolynomial, secondPolynomial) {

    override fun getMultiplicationMethod() = "Sequential Naive Multiplication"

    override fun multiply() = multiplyRange(0..result.degree)
}