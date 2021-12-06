package container

import kotlin.math.min
import kotlin.random.Random

class Polynomial(private val coefficients: MutableList<Int>) {

    val size = coefficients.size
    val degree = size - 1

    constructor(degree: Int, fill: Boolean = true) : this((0..degree)
        .map { if (fill) Random.nextInt(10) else 0 }
        .toMutableList())

    constructor(vararg initialCoefficients: Int) : this(initialCoefficients.toMutableList())

    constructor(coefficients: Array<IntArray?>) : this(coefficients[0]?.toMutableList() ?: mutableListOf())

    fun getSubCoefficients(from: Int = 0, to: Int = size) = coefficients.subList(from, to)

    fun getSubPolynomial(from: Int = 0, to: Int = size) = Polynomial(getSubCoefficients(from, to))

    fun getCoefficientsAsArray() = arrayOf(coefficients.toIntArray())

    fun fill(start: Int, count: Int, withMapper: (Int) -> Int = { 0 }): Polynomial {
        val coefficientsResult = coefficients.toMutableList()

        coefficientsResult.addAll(start, start.until(start + count).map { withMapper(it) })

        return Polynomial(coefficientsResult)
    }

    fun keep(range: IntRange) = 0.until(size).filter { it !in range }.forEach {
        coefficients[it] = 0
    }

    operator fun get(degree: Int) = coefficients[degree]

    operator fun set(degree: Int, value: Int) {
        coefficients[degree] = value
    }

    operator fun plus(other: Polynomial): Polynomial {
        val resultCoefficients = coefficients.zip(other.coefficients)
            .map { (first, second) -> first + second }
            .toMutableList()

        if (size == other.size)
            return Polynomial(resultCoefficients)

        val minimumLength = min(size, other.size)
        resultCoefficients.addAll(
            if (size == minimumLength) other.getSubCoefficients(minimumLength) else getSubCoefficients(minimumLength)
        )

        return Polynomial(resultCoefficients)
    }

    operator fun minus(other: Polynomial): Polynomial {
        val resultCoefficients = coefficients.zip(other.coefficients)
            .map { (first, second) -> first - second }
            .toMutableList()

        if (size == other.size)
            return Polynomial(resultCoefficients)

        val minimumLength = min(size, other.size)
        resultCoefficients.addAll(
            if (size == minimumLength)
                other.getSubCoefficients(minimumLength).map { -it } else
                getSubCoefficients(minimumLength)
        )

        return Polynomial(resultCoefficients)
    }

    override fun toString() =
        coefficients.mapIndexedNotNull { index, coefficient ->
            if (coefficient == 0)
                null
            else "$coefficient${
                when (index) {
                    0 -> ""
                    1 -> "X"
                    else -> "X^${index}"
                }
            }"
        }.reversed()
            .joinToString(" + ")
}