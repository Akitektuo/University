import container.Algorithm
import container.Polynomial
import container.PolynomialSplit
import mpi.MPI
import kotlin.math.max
import kotlin.system.measureTimeMillis

val algorithm = Algorithm.NAIVE

fun main(args: Array<String>) {
    MPI.Init(args)

    val rank = MPI.COMM_WORLD.Rank()
    val numberOfProcesses = MPI.COMM_WORLD.Size()

    if (rank == 0)
        runMainProcess(numberOfProcesses)
    else
        runOtherProcesses()

    MPI.Finalize()
}

fun runMainProcess(numberOfProcesses: Int) {
    val firstPolynomial = Polynomial(1, 2, 3, 4, 5)
    val secondPolynomial = Polynomial(4)

    println("First: $firstPolynomial")
    println("Second: $secondPolynomial")

    var result = Polynomial(firstPolynomial.degree + secondPolynomial.degree, false)
    val time = measureTimeMillis {
        val length = max(result.size / (numberOfProcesses - 1), 1)
        var end = 0

        1.until(numberOfProcesses).forEach {
            val start = end
            end += length

            if (it == numberOfProcesses - 1)
                end = result.size

            sendPolynomial(firstPolynomial, it)
            sendPolynomial(secondPolynomial, it)
            send(arrayOf(start, end), it)
        }

        1.until(numberOfProcesses).forEach {
            result += receivePolynomial(it)
        }
    }
    println("Result: $result in $time ms")
}

fun runOtherProcesses() {
    val firstPolynomial = receivePolynomial(0)
    val secondPolynomial = receivePolynomial(0)

    val range = arrayOf(0, 0)
    receive(range, 0)

    val result = when (algorithm) {
        Algorithm.NAIVE -> runNaive(firstPolynomial, secondPolynomial, range[0].until(range[1]))
        Algorithm.KARATSUBA -> runKaratsuba(firstPolynomial, secondPolynomial, range[0].until(range[1]))
    }

    sendPolynomial(result, 0)
}

fun runNaive(firstPolynomial: Polynomial, secondPolynomial: Polynomial, range: IntRange): Polynomial {
    val result = Polynomial(firstPolynomial.degree + secondPolynomial.degree, false)

    fun computeDegree(degree: Int) = (0..degree).map { it to degree - it }
        .filter { (index, complementary) ->
            index <= firstPolynomial.degree && complementary <= secondPolynomial.degree
        }.forEach { (index, complementary) ->
            result[degree] += firstPolynomial[index] * secondPolynomial[complementary]
        }

    range.forEach {
        computeDegree(it)
    }

    return result
}

fun runKaratsuba(firstPolynomial: Polynomial, secondPolynomial: Polynomial, range: IntRange): Polynomial {
    firstPolynomial.keep(range)
    secondPolynomial.keep(range)

    return karatsuba(firstPolynomial, secondPolynomial)
}

fun karatsuba(firstPolynomial: Polynomial, secondPolynomial: Polynomial): Polynomial {
    if (firstPolynomial.degree < 2 || secondPolynomial.degree < 2)
        return runNaive(
            firstPolynomial,
            secondPolynomial,
            0.rangeTo(firstPolynomial.degree + secondPolynomial.degree)
        )

    val length = getHalfOfLength(firstPolynomial, secondPolynomial)

    with(split(firstPolynomial, secondPolynomial, length)) {
        val intermediaryLow = karatsuba(firstLow, secondLow)
        val intermediaryMiddle = karatsuba(firstLow + firstHigh, secondLow + secondHigh)
        val intermediaryHigh = karatsuba(firstHigh, secondHigh)

        return computeResult(intermediaryLow, intermediaryMiddle, intermediaryHigh, length)
    }
}

fun getHalfOfLength(firstPolynomial: Polynomial, secondPolynomial: Polynomial) =
    max(firstPolynomial.degree, secondPolynomial.degree) / 2

fun split(firstPolynomial: Polynomial, secondPolynomial: Polynomial, splitIndex: Int) =
    PolynomialSplit(
        firstPolynomial.getSubPolynomial(to = splitIndex),
        firstPolynomial.getSubPolynomial(splitIndex),
        secondPolynomial.getSubPolynomial(to = splitIndex),
        secondPolynomial.getSubPolynomial(splitIndex),
    )

fun computeResult(low: Polynomial, middle: Polynomial, high: Polynomial, length: Int): Polynomial {
    val resultHigh = high.fill(0, length * 2)
    val resultSubtraction = (middle - high - low).fill(0, length)
    return resultHigh + resultSubtraction + low
}
