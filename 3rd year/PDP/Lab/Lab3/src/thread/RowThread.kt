package thread

import USE_LOGS
import solver.Configuration

class RowThread(configuration: Configuration, index: Int) : BaseThread(configuration, index) {
    override fun getStart() = with(configuration) {
        val rowStart = computationsPerThread * index / result.numberOfRows
        val columnStart = computationsPerThread * index % result.numberOfRows

        if (index + 1 == numberOfThreads)
            computationsPerThread += result.size % numberOfThreads

        rowStart to columnStart
    }

    override fun compute() = with(configuration.result) {
        val positions = ArrayList<Pair<Int, Int>>()

        var row = rowStart
        var column = columnStart
        var size = computationsPerThread

        while (size-- > 0) {
            positions.add(row to column++)
            if (column == numberOfColumns) {
                column = 0
                row++
            }
        }

        if (USE_LOGS)
            println("*** Positions of thread ${index}: $positions")

        positions
    }
}