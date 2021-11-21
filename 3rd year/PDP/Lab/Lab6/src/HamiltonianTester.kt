import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.system.measureTimeMillis

class HamiltonianTester(private val numberOfTest: Int, private val graph: DirectedGraph) {
    private val path = Collections.synchronizedList(arrayListOf<Int>())
    private val isDone = AtomicBoolean()

    init {
        start()
    }

    private fun start() {
        val duration = measureTimeMillis { parallelize() }
        println("Test $numberOfTest with ${graph.numberOfNodes} vertices executed in $duration ms")
    }

    private fun parallelize() = concurrently { async ->
        0.until(graph.numberOfNodes).forEach {
            async { visitNode(it) }
        }
    }

    private fun visitNode(startNode: Int, node: Int = startNode) {
        if (isDone.get())
            return

        path += node

        if (path.size != graph.numberOfNodes)
            return graph[node].filter { it !in path }.forEach { visitNode(it) }

        if (startNode !in graph[node])
            return

        isDone.set(true)
    }
}