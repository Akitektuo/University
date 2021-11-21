fun main() {
    generateSequentially(1..NUMBER_OF_GRAPHS) {
        HamiltonianTester(it, DirectedGraph.generateHamiltonian(it * 10))
    }
}

fun generateSequentially(range: IntRange, action: (Int) -> Unit) = range.forEach(action)

// !!! If the CPU has less than 8 threads, to not suffocate the CPU, generateSequentially will be run
fun generateConcurrently(range: IntRange, action: (Int) -> Unit) = if (Runtime.getRuntime().availableProcessors() < 8)
    generateSequentially(range, action) else
    concurrently { async ->
        range.forEach {
            async { action(it) }
        }
    }
