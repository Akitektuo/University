package thread

import USE_LOGS
import solver.Configuration

class KthThread(configuration: Configuration, index: Int) : BaseThread(configuration, index) {
    override fun getStart() = with(configuration) {
        if (index < result.size % numberOfThreads)
            computationsPerThread++

        val rowStart = index / result.numberOfRows
        val columnStart = index % result.numberOfRows

        rowStart to columnStart
    }

    override fun compute() = with(configuration) {
        val positions = ArrayList<Pair<Int, Int>>()

        var row = rowStart
        var column = columnStart
        var size = computationsPerThread

        while (size-- > 0) {
            positions.add(row to column)
            row += (column + numberOfThreads) / result.numberOfRows
            column = (column + numberOfThreads) % result.numberOfRows
        }

        if (USE_LOGS)
            println("*** Positions of thread ${index}: $positions")

        positions
    }
}