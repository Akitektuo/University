import mpi.MPI
import mpi.Status

fun getProcessRank() = MPI.COMM_WORLD.Rank()

fun <T> send(buffer: Array<T>, processRank: Int) =
    MPI.COMM_WORLD.Send(buffer, 0, buffer.size, MPI.OBJECT, processRank, 0)

fun <T> receive(buffer: Array<T>, processRank: Int = MPI.ANY_SOURCE): Status =
    MPI.COMM_WORLD.Recv(buffer, 0, buffer.size, MPI.OBJECT, processRank, 0)

fun DistributedSharedMemory.threadedListener(block: DistributedSharedMemory.() -> Unit) {
    val thread = Thread(Listener(this))
    thread.start()

    block(this)

    thread.join()
}