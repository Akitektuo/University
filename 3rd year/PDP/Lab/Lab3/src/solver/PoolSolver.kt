package solver

import FUNCTION
import domain.FunctionType
import thread.ColumnThread
import thread.KthThread
import thread.RowThread
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun solvePool(configuration: Configuration) = with(configuration) {
    val executor = Executors.newFixedThreadPool(numberOfThreads)
    val range = 0 until numberOfThreads

    when (FUNCTION) {
        FunctionType.ROW -> range.forEach { executor.submit(RowThread(configuration, it)) }
        FunctionType.COLUMN -> range.forEach { executor.submit(ColumnThread(configuration, it)) }
        FunctionType.KTH -> range.forEach { executor.submit(KthThread(configuration, it)) }
    }

    executor.shutdown()
    if (!executor.awaitTermination(5, TimeUnit.MINUTES))
        executor.shutdownNow()

    println("Result matrix:\n$result")
}