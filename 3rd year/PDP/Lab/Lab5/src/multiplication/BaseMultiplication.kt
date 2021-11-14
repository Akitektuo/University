package multiplication

import Polynomial

abstract class BaseMultiplication(
    protected val firstPolynomial: Polynomial,
    protected val secondPolynomial: Polynomial
) {
    lateinit var result: Polynomial
        private set

    var executionTime: Long = 0
        private set

    abstract fun getMultiplicationMethod(): String

    abstract fun multiply()

    init {
        run()
    }

    private fun run() {
        val startTime = System.currentTimeMillis()
        result = Polynomial(firstPolynomial.degree + secondPolynomial.degree, false)
        multiply()
        executionTime = System.currentTimeMillis() - startTime
        logTime()
    }

    private fun logTime() = println("Execution time of ${getMultiplicationMethod()}: $executionTime milliseconds")
}