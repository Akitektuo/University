import mpi.MPI
import java.io.File
import java.util.*

fun readFileIndexed(
    fileName: String,
    operationBlock: (index: Int, line: String) -> Unit,
) = with(Scanner(File(fileName))) {
    var index = 0
    while (hasNextLine())
        operationBlock(index++, nextLine())
    close()
}

fun send(matrix: Matrix?, targetProcessIndex: Int) =
    MPI.COMM_WORLD.Send(arrayOf(matrix), 0, 1, MPI.OBJECT, targetProcessIndex, 0)

fun send(integer: Int, targetProcessIndex: Int) =
    MPI.COMM_WORLD.Send(intArrayOf(integer), 0, 1, MPI.INT, targetProcessIndex, 0)

fun send(result: Pair<Int, Matrix>, targetProcessIndex: Int) =
    MPI.COMM_WORLD.Send(arrayOf(result), 0, 1, MPI.OBJECT, targetProcessIndex, 0)

fun receiveMatrix(fromProcessIndex: Int): Matrix? {
    val result = arrayOfNulls<Matrix>(1)

    MPI.COMM_WORLD.Recv(result, 0, 1, MPI.OBJECT, fromProcessIndex, 0)

    return result[0]
}

fun receiveInteger(fromProcessIndex: Int): Int {
    val result = IntArray(1)

    MPI.COMM_WORLD.Recv(result, 0, 1, MPI.INT, fromProcessIndex, 0)

    return result[0]
}

fun receiveResult(fromProcessIndex: Int): Pair<Int, Matrix> {
    val result = arrayOfNulls<Pair<Int, Matrix>>(1)

    MPI.COMM_WORLD.Recv(result, 0, 1, MPI.OBJECT, fromProcessIndex, 0)

    return result[0] ?: error("Received result from process $fromProcessIndex is null")
}