package thread

import USE_LOGS
import solver.Configuration

class ColumnThread(configuration: Configuration, index: Int) : BaseThread(configuration, index) {
    override fun getStart() = with(configuration) {
        val rowStart = computationsPerThread * index % result.numberOfColumns
        val columnStart = computationsPerThread * index / result.numberOfColumns

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
            positions.add(row++ to column)
            if (row == numberOfRows) {
                row = 0
                column++
            }
        }

        if (USE_LOGS)
            println("*** Positions of thread ${index}: $positions")

        positions
    }
}