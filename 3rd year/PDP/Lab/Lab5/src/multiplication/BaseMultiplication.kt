package multiplication

import container.Polynomial

abstract class BaseMultiplication(
    protected val firstPolynomial: Polynomial,
    protected val secondPolynomial: Polynomial,
    private val withoutLogs: Boolean = false
) {
    lateinit var result: Polynomial
        internal set

    var executionTime: Long = 0
        private set

    abstract fun getMultiplicationMethod(): String

    abstract fun multiply()

    init {
        run()
    }

    private fun run() {
        if (withoutLogs)
            return startMultiplication()

        val startTime = System.currentTimeMillis()
        startMultiplication()
        executionTime = System.currentTimeMillis() - startTime
        logTime()
    }

    private fun startMultiplication() {
        result = Polynomial(firstPolynomial.degree + secondPolynomial.degree, false)
        multiply()
    }

    private fun logTime() = println("Execution time of ${getMultiplicationMethod()}: $executionTime milliseconds")
}