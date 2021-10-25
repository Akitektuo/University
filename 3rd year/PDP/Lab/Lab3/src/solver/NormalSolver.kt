package solver

import FUNCTION
import domain.FunctionType
import thread.ColumnThread
import thread.KthThread
import thread.RowThread

fun solveNormal(configuration: Configuration) = with(configuration) {
    val range = 0 until numberOfThreads

    val threads = when (FUNCTION) {
        FunctionType.ROW -> range.map { RowThread(configuration, it).apply { start() } }
        FunctionType.COLUMN -> range.map { ColumnThread(configuration, it).apply { start() } }
        FunctionType.KTH -> range.map { KthThread(configuration, it).apply { start() } }
    }

    threads.forEach { it.join() }

    println("Result matrix:\n$result")
}