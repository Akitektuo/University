package domain

import kotlin.random.Random
import kotlin.random.nextInt

class Matrix(val numberOfRows: Int, val numberOfColumns: Int) {
    private val table = Array(numberOfRows) {
        Array(numberOfColumns) { Random.nextInt(1..9) }
    }

    val size = numberOfRows * numberOfColumns

    operator fun set(row: Int, column: Int, value: Int) {
        table[row][column] = value
    }

    operator fun get(row: Int, column: Int) = table[row][column]

    override fun toString() = table.joinToString("\n") { it.joinToString(" ") }

    override fun equals(other: Any?) = other is Matrix &&
            numberOfRows == other.numberOfRows &&
            numberOfColumns == other.numberOfColumns &&
            haveEqualValues(other)

    private fun haveEqualValues(other: Matrix): Boolean {
        0.until(numberOfRows).forEach { row ->
            0.until(numberOfColumns).forEach { column ->
                if (get(row, column) != other[row, column])
                    return false
            }
        }
        return true
    }
}