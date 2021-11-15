package multiplication.naive

import container.Polynomial
import getNumberOfThreads
import runConcurrently
import kotlin.math.max

class ConcurrentNaiveMultiplication(firstPolynomial: Polynomial, secondPolynomial: Polynomial) :
    NaiveMultiplication(firstPolynomial, secondPolynomial) {

    override fun getMultiplicationMethod() = "Concurrent Naive Multiplication"

    override fun multiply() = with(result) {
        val step = degree / getNumberOfThreads(degree)
        val range = (0..degree).step(max(step, 1))

        runConcurrently(range.map { { multiplyRange(it.until(it + step)) } })
    }
}