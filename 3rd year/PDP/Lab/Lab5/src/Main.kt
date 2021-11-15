import container.Polynomial
import multiplication.karatsuba.ConcurrentKaratsubaMultiplication
import multiplication.karatsuba.SequentialKaratsubaMultiplication
import multiplication.naive.ConcurrentNaiveMultiplication
import multiplication.naive.SequentialNaiveMultiplication

fun main() {
    val firstPolynomial = Polynomial(1, 2, 3, 4, 5)
    val secondPolynomial = Polynomial(5)

    println("First: $firstPolynomial")
    println("Second: $secondPolynomial")

    println("Result: ${SequentialNaiveMultiplication(firstPolynomial, secondPolynomial).result}")
    println("Result: ${ConcurrentNaiveMultiplication(firstPolynomial, secondPolynomial).result}")
    println("Result: ${SequentialKaratsubaMultiplication(firstPolynomial, secondPolynomial).result}")
    println("Result: ${ConcurrentKaratsubaMultiplication(firstPolynomial, secondPolynomial).result}")
}