import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

const val NUMBER_OF_THREADS = 5
const val NUMBER_OF_TASKS = 5

val executor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

fun main() {
    val initialMatrix = Matrix.fromFile("assets/matrix.txt")

    println(initialMatrix.solve())

    executor.shutdown()
    executor.awaitTermination(3, TimeUnit.MINUTES)
}

fun Matrix.solve(): Matrix {
    val startTime = System.currentTimeMillis()
    var minimumBound = manhattan
    while (true) {
        val (distance, solution) = searchParallel(this, 0, minimumBound, NUMBER_OF_TASKS)

        if (distance == -1) {
            println("Solution found in ${solution.numberOfSteps} steps in ${System.currentTimeMillis() - startTime}ms")
            return solution
        }

        println("Depth $distance reached in ${System.currentTimeMillis() - startTime}ms")
        minimumBound = distance
    }
}

fun searchParallel(currentMatrix: Matrix, numberOfSteps: Int, bound: Int, numberOfThreads: Int): Pair<Int, Matrix> {
    if (numberOfThreads <= 1)
        return search(currentMatrix, numberOfSteps, bound)

    val estimation = numberOfSteps + currentMatrix.manhattan
    if (estimation > bound || estimation > 80)
        return estimation to currentMatrix

    if (currentMatrix.manhattan == 0)
        return -1 to currentMatrix

    val generatedMoves = currentMatrix.generateMoves()
    val futures = generatedMoves.map {
        executor.submit<Pair<Int, Matrix>> {
            searchParallel(it, numberOfSteps + 1, bound, numberOfThreads / generatedMoves.size)
        }
    }

    return futures.map { it.get() }
        .minByOrNull { (estimate, _) -> estimate }!!
}

fun search(currentMatrix: Matrix, numberOfSteps: Int, bound: Int): Pair<Int, Matrix> {
    val estimation = numberOfSteps + currentMatrix.manhattan
    if (estimation > bound || estimation > 80)
        return estimation to currentMatrix

    if (currentMatrix.manhattan == 0)
        return -1 to currentMatrix

    return currentMatrix.generateMoves()
        .map { search(it, numberOfSteps + 1, bound) }
        .minByOrNull { (estimate, _) -> estimate }!!
}