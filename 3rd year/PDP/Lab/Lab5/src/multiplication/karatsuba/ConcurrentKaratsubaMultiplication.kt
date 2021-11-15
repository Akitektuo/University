package multiplication.karatsuba

import container.Polynomial
import async

class ConcurrentKaratsubaMultiplication(firstPolynomial: Polynomial, secondPolynomial: Polynomial) :
    KaratsubaMultiplication(firstPolynomial, secondPolynomial) {

    override fun getMultiplicationMethod() = "Concurrent Karatsuba Multiplication"

    override fun multiply(firstPolynomial: Polynomial, secondPolynomial: Polynomial): Polynomial {
        if (firstPolynomial.degree < 6 || secondPolynomial.degree < 6)
            return SequentialKaratsubaMultiplication(firstPolynomial, secondPolynomial, true).result

        val length = getHalfOfLength(firstPolynomial, secondPolynomial)

        with(split(firstPolynomial, secondPolynomial, length)) {
            val intermediaryLow = async { multiply(firstLow, secondLow) }
            val intermediaryMiddle = async { multiply(firstLow + firstHigh, secondLow + secondHigh) }
            val intermediaryHigh = async { multiply(firstHigh, secondHigh) }

            return computeResult(intermediaryLow.get(), intermediaryMiddle.get(), intermediaryHigh.get(), length)
        }
    }
}