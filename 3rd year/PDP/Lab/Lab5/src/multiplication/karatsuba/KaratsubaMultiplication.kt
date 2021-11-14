package multiplication.karatsuba

import Polynomial
import multiplication.BaseMultiplication

abstract class KaratsubaMultiplication(firstPolynomial: Polynomial, secondPolynomial: Polynomial) :
    BaseMultiplication(firstPolynomial, secondPolynomial) {

    final override fun multiply() {
        multiply(firstPolynomial, secondPolynomial)
    }

    abstract fun multiply(firstPolynomial: Polynomial, secondPolynomial: Polynomial): Polynomial
}