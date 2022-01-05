import kotlin.math.min

const val NUMBER_OF_THREADS = 8

fun main() {
    val graph = Graph.fromFile("assets/matrix.txt") ?: error("Graph could not be initialized")

    val result = color(graph, 0)

    result.forEachIndexed { index, color -> println("$index: $color") }
}

fun color(graph: Graph, start: Int): IntArray {
    val result = IntArray(graph.numberOfVertices) {
        if (it == start) 0 else -1
    }

    val rangeWithoutStart = result.indices
        .step(if (result.size > NUMBER_OF_THREADS) NUMBER_OF_THREADS else 1)

    rangeWithoutStart.map {
        async {
            it.until(min(it + rangeWithoutStart.step + 1, result.size))
                .filter { it != start }
                .forEach { vertex ->
                    val usedColors = graph[vertex].map { result[it] != -1 }

                    result[vertex] = usedColors.indexOfFirst { !it }
                }
        }
    }.forEach { it.get() }

    return result
}