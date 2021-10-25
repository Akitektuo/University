package thread

import solver.Configuration

abstract class BaseThread(protected val configuration: Configuration, protected val index: Int) : Thread() {
    protected var computationsPerThread: Int = 0
    protected var rowStart: Int = 0
    protected var columnStart: Int = 0

    init {
        with(configuration) {
            computationsPerThread = result.size / numberOfThreads

            getStart().apply {
                rowStart = first
                columnStart = second
            }
        }
    }

    abstract fun compute(): ArrayList<Pair<Int, Int>>

    abstract fun getStart(): Pair<Int, Int>

    override fun run() {
        compute().forEach { (row, column) ->
            configuration.result[row, column] = multiply(row, column)
        }
        // configuration.result[0, 0] = 0 // used to check the result matrix checker
    }

    private fun multiply(row: Int, column: Int) = with(configuration) {
        (0 until firstMatrix.numberOfColumns).sumOf { firstMatrix[row, it] * secondMatrix[it, column] }
    }
}