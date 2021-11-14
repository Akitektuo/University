import multiplication.naive.ConcurrentNaiveMultiplication
import multiplication.naive.SequentialNaiveMultiplication

fun main() {
    val firstPolynomial = Polynomial(100)
    val secondPolynomial = Polynomial(100)

    println("First: $firstPolynomial")
    println("Second: $secondPolynomial")

    println("Result: ${SequentialNaiveMultiplication(firstPolynomial, secondPolynomial).result}")

    val multiplication = ConcurrentNaiveMultiplication(firstPolynomial, secondPolynomial)
    println("Result: ${multiplication.result}")
}