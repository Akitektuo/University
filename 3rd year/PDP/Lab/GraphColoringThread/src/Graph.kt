class Graph(val numberOfVertices: Int) {
    private val edges = HashMap(0.until(numberOfVertices).associateWith { hashSetOf<Int>() })

    companion object {
        fun fromFile(fileName: String): Graph? {
            var graph: Graph? = null

            readFileIndexed(fileName) { index, line ->
                when (index) {
                    0 -> graph = Graph(line.toInt())
                    else -> {
                        val (nodeA, nodeB) = line.split(" ")
                        graph?.addEdge(nodeA.toInt(), nodeB.toInt())
                    }
                }
            }

            return graph
        }
    }

    operator fun get(vertex: Int) = edges[vertex] ?: error("Vertex $vertex out of graph vertices ($numberOfVertices)")

    private fun addEdge(vertexA: Int, vertexB: Int) = with(edges) {
        edges[vertexA]?.add(vertexB)
        edges[vertexB]?.add(vertexA)
    }
}