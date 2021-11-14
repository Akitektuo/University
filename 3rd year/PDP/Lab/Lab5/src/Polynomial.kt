import kotlin.random.Random

class Polynomial(private val coefficients: MutableList<Int>) {

    val degree = coefficients.size - 1

    constructor(degree: Int, fill: Boolean = true) : this((0..degree)
        .map { if (fill) Random.nextInt(10) else 0 }
        .toMutableList())

    constructor(vararg initialCoefficients: Int) : this(initialCoefficients.toMutableList())

    operator fun get(degree: Int) = coefficients[degree]

    operator fun set(degree: Int, value: Int) {
        coefficients[degree] = value
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