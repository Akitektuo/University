package multiplication.karatsuba

import container.Polynomial
import multiplication.naive.SequentialNaiveMultiplication

class SequentialKaratsubaMultiplication(
    firstPolynomial: Polynomial,
    secondPolynomial: Polynomial,
    withoutLogs: Boolean = false
) : KaratsubaMultiplication(firstPolynomial, secondPolynomial, withoutLogs) {

    override fun getMultiplicationMethod() = "Sequential Karatsuba Multiplication"

    override fun multiply(firstPolynomial: Polynomial, secondPolynomial: Polynomial): Polynomial {
        if (firstPolynomial.degree < 2 || secondPolynomial.degree < 2)
            return SequentialNaiveMultiplication(firstPolynomial, secondPolynomial, true).result

        val length = getHalfOfLength(firstPolynomial, secondPolynomial)

        with(split(firstPolynomial, secondPolynomial, length)) {
            val intermediaryLow = multiply(firstLow, secondLow)
            val intermediaryMiddle = multiply(firstLow + firstHigh, secondLow + secondHigh)
            val intermediaryHigh = multiply(firstHigh, secondHigh)

            return computeResult(intermediaryLow, intermediaryMiddle, intermediaryHigh, length)
        }
    }
}