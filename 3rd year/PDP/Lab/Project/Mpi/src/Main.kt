import mpi.MPI

fun main(args: Array<String>) {
    MPI.Init(args)
    val rank = MPI.COMM_WORLD.Rank()
    if (rank == 0) {
        val matrix = Matrix.fromFile("assets/matrix.txt")
        matrix.searchMaster()
    } else {
        searchWorker()
    }
    MPI.Finalize()
}

private fun Matrix.searchMaster() {
    val processes = MPI.COMM_WORLD.Size()
    val workers = processes - 1
    val startTime = System.currentTimeMillis()

    val queue = ArrayDeque<Matrix>()
    queue.addLast(this)
    while (queue.size < workers) {
        queue.addAll(queue.removeFirst().generateMoves())
    }

    var bound = manhattan
    while (true) {
        queue.forEachIndexed { index, matrix ->
            send(matrix, index + 1)
            send(bound, index + 1)
        }

        val (minimumBound, solution) = (1..queue.size).map { receiveResult(it) }
            .minByOrNull { (estimate, _) -> estimate }!!

        if (minimumBound == -1) {
            println("Solution:\n$solution\n" +
                    "Found in ${solution.numberOfSteps} steps in ${System.currentTimeMillis() - startTime}ms")
            break
        }

        println("Depth $minimumBound reached in ${System.currentTimeMillis() - startTime}ms")
        bound = minimumBound
    }

    1.until(processes).forEach { send(null, it) }
}

fun searchWorker() {
    while (true) {
        val matrix = receiveMatrix(0) ?: return
        val bound = receiveInteger(0)

        send(search(matrix, matrix.numberOfSteps, bound), 0)
    }
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