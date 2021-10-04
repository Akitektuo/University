import kotlin.random.Random
import kotlin.random.nextInt

const val TOTAL_THREADS = 1000

fun main() {
    val startOfExecutionTime = System.currentTimeMillis()
    val threads = ArrayList<Thread>()
    val graph = ComputationalGraph()

    graph.loadGraph("graph.txt")
    graph.computeInitialValue()

    for (i in 0 until TOTAL_THREADS) {
        if (i % 5 == 0) {
            threads.forEach { it.join() }
            threads.clear()
            graph.checkConsistency()
        }

        val currentNode = graph.getRandomNode()
        val newValue = Random.nextInt(-10..10)
        println("Thread $i updates node ${currentNode.index} with $newValue")

        threads.add(Thread {
            run {
                graph.updateNode(currentNode, newValue)
            }
        }.apply { start() })
    }

    threads.forEach { it.join() }
    threads.clear()
    graph.checkConsistency()
    graph.printValues()

    val durationOfExecution = System.currentTimeMillis() - startOfExecutionTime
    println("Elapsed time: $durationOfExecution")
}

