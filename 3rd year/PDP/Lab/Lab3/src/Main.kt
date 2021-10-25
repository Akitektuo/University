import domain.ApproachType
import domain.Matrix
import solver.Configuration
import solver.solveNormal
import solver.solvePool

fun main() {
    val matrix1 = Matrix(FIRST_MATRIX_ROWS, FIRST_MATRIX_COLUMNS)
    val matrix2 = Matrix(SECOND_MATRIX_ROWS, SECOND_MATRIX_COLUMNS)

    println("First matrix:\n$matrix1")
    println("Second matrix:\n$matrix2")

    if (matrix1.numberOfColumns != matrix2.numberOfRows)
        error(
            "The number of columns (${matrix1.numberOfColumns}) of the first matrix should mach the number of rows " +
                    "(${matrix2.numberOfRows}) of the second matrix!"
        )

    val result = Matrix(matrix1.numberOfRows, matrix2.numberOfColumns)

    val configuration = Configuration(matrix1, matrix2, result, getNumberOfThreads(result))

    val startTime = System.currentTimeMillis()
    when (APPROACH) {
        ApproachType.NORMAL -> solveNormal(configuration)
        ApproachType.POOL -> solvePool(configuration)
    }
    println("Execution time: ${System.currentTimeMillis() - startTime}")

    if (CHECK_RESULT_MATRIX)
        checkMatrix(matrix1, matrix2, result)
}

fun checkMatrix(matrix1: Matrix, matrix2: Matrix, givenResult: Matrix) {
    println("\nComputing expected matrix...\n")

    val expectedResult = Matrix(matrix1.numberOfRows, matrix2.numberOfRows)

    0.until(matrix1.numberOfRows).forEach { row ->
        0.until(matrix2.numberOfColumns).forEach { column ->
            expectedResult[row, column] = 0.until(matrix1.numberOfColumns).sumOf {
                matrix1[row, it] * matrix2[it, column]
            }
        }
    }

    val areEqual = givenResult == expectedResult
    if (areEqual) {
        println("The computed matrix is correct!")
        return
    }

    println("!!! The computed matrix is wrong!")
    println("\nExpected:\n$expectedResult")
    println("\nGiven:\n$givenResult")
}

fun getNumberOfThreads(result: Matrix): Int {
    var availableThreads = NUMBER_OF_THREADS

    if (NUMBER_OF_THREADS < 1) {
        availableThreads = Runtime.getRuntime().availableProcessors()
        println("\n'NUMBER_OF_THREADS' set to 0, found $availableThreads threads on the machine.\n")
    }

    if (OPTIMIZE_THREADS && result.size < availableThreads) {
        availableThreads = result.size
        println("\nToo many threads make the algorithm slower, thread size reduced to $availableThreads\n")
    }

    return availableThreads
}