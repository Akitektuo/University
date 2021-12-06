import container.Polynomial
import mpi.MPI
import mpi.Status

fun <T> send(buffer: Array<T>, processRank: Int) =
    MPI.COMM_WORLD.Send(buffer, 0, buffer.size, MPI.OBJECT, processRank, 0)

fun <T> receive(buffer: Array<T>, processRank: Int): Status =
    MPI.COMM_WORLD.Recv(buffer, 0, buffer.size, MPI.OBJECT, processRank, 0)

fun sendPolynomial(polynomial: Polynomial, processRank: Int) = send(polynomial.getCoefficientsAsArray(), processRank)

fun receivePolynomial(processRank: Int): Polynomial {
    val coefficients = arrayOfNulls<IntArray>(1)

    receive(coefficients, processRank)

    return Polynomial(coefficients)
}