import kotlin.math.abs

class Matrix(
    var freeRow: Int = -1,
    var freeColumn: Int = -1,
    var numberOfSteps: Int = 0,
    private val previousState: Matrix? = null
) {
    companion object {
        fun fromFile(fileName: String) = Matrix().apply {
            readFileIndexed(fileName) { row, line ->
                line.split(" ")
                    .map { it.toInt() }
                    .forEachIndexed { column, value ->
                        set(row, column, value)
                        if (value == 0) {
                            freeRow = row
                            freeColumn = column
                        }
                    }
            }
            computeData()
        }
    }

    private val data = hashMapOf<Pair<Int, Int>, Int>()
    var manhattan = 0
    private var minSteps = 0
    private var estimation = 0

    fun computeData() {
        manhattan = getManhattanDistance()
        minSteps = numberOfSteps + manhattan
        estimation = manhattan + numberOfSteps
    }

    private fun getManhattanDistance() = data.entries
        .filter { (_, value) -> value != 0 }
        .sumOf { (index, value) -> computeManhattanDistanceHelper(index.first, index.second, value) }

    fun generateMoves() = 0.until(MATRIX_SIZE)
        .filter {
            freeRow + DIRECTIONS_X[it] in 0 until MATRIX_SIZE &&
                    freeColumn + DIRECTIONS_Y[it] in 0 until MATRIX_SIZE
        }
        .mapNotNull { generateMatrix(it) }

    private fun generateMatrix(index: Int): Matrix? {
        val movedFreeRow = freeRow + DIRECTIONS_X[index]
        val movedFreeColumn = freeColumn + DIRECTIONS_Y[index]

        previousState?.let {
            if (movedFreeRow == it.freeRow && movedFreeColumn == it.freeColumn)
                return null
        }

        val matrix = Matrix(movedFreeRow, movedFreeColumn, numberOfSteps + 1, this)

        matrix.cloneData(data)
        matrix[freeRow, freeColumn] = matrix[movedFreeRow, movedFreeColumn]
        matrix[movedFreeRow, movedFreeColumn] = 0
        matrix.computeData()

        return matrix
    }

    private fun cloneData(data: HashMap<Pair<Int, Int>, Int>) = this.data.putAll(data)

    private fun computeManhattanDistanceHelper(row: Int, column: Int, value: Int): Int {
        val targetRow = (value - 1) / MATRIX_SIZE
        val targetColumn = (value - 1) % MATRIX_SIZE

        return abs(row - targetRow) + abs(column - targetColumn)
    }

    operator fun set(row: Int, column: Int, value: Int) {
        data[row to column] = value
    }

    operator fun get(row: Int, column: Int) = data[row to column] ?: error("No data found for ($row, $column)")

    override fun toString() = 0.until(MATRIX_SIZE).joinToString("\n") { row ->
        0.until(MATRIX_SIZE).joinToString(" ") { column -> get(row, column).toString()  }
    }
}