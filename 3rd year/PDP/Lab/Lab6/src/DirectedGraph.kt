import kotlin.random.Random

class DirectedGraph(val numberOfNodes: Int) {
    private val nodes = ArrayList<Int>()
    private val edges = HashMap<Int, HashSet<Int>>()

    companion object {
        fun generateHamiltonian(numberOfNodes: Int) = DirectedGraph(numberOfNodes).apply {
            if (numberOfNodes < 2)
                return@apply

            nodes.shuffle()

            1.until(nodes.size).forEach {
                addEdge(it - 1, it)
            }

            addEdge(nodes.last(), nodes.first())

            0.until(numberOfNodes / 2).forEach { _ ->
                addEdge(Random.nextInt(numberOfNodes), Random.nextInt(numberOfNodes))
            }
        }
    }

    init {
        0.until(numberOfNodes).forEach {
            nodes.add(it)
            edges[it] = hashSetOf()
        }
    }

    fun addEdge(fromNode: Int, toNode: Int) = edges[fromNode]?.add(toNode)

    operator fun get(node: Int) = edges[node] ?: hashSetOf()
}